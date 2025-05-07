package ro.unibuc.hello.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.events.EventException;

import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.Event;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MeterRegistry metricsRegistry;

    private Event convertToDTO(EventEntity eventEntity) {
        Event eventDTO = new Event();
        eventDTO.setId(eventEntity.getId());
        eventDTO.setEventName(eventEntity.geteventName());
        eventDTO.setDescription(eventEntity.getDescription());
        eventDTO.setLocation(eventEntity.getLocation());
        eventDTO.setDate(eventEntity.getDate());
        eventDTO.setTime(eventEntity.getTime());
        eventDTO.setTotalTickets(eventEntity.getTotalTickets());
        eventDTO.setSoldTickets(eventEntity.getSoldTickets());
        eventDTO.setTicketPrice(eventEntity.getTicketPrice());
        eventDTO.setOrganizerId(eventEntity.getOrganizerId());
        eventDTO.setPriceOperation(eventEntity.getPriceOperation());
        return eventDTO;
    }

    public Event saveEvent(Event event){
        EventEntity entity = new EventEntity();
        entity.setId(event.getId());
        entity.setEventName(event.geteventName());
        entity.setDescription(event.getDescription());
        entity.setLocation(event.getLocation());
        entity.setDate(event.getDate());
        entity.setTime(event.getTime());
        entity.setTotalTickets(event.getTotalTickets());
        entity.setSoldTickets(event.getSoldTickets());
        entity.setTicketPrice(event.getTicketPrice());
        entity.setOrganizerId(event.getOrganizerId());
        entity.setPriceOperation(event.getPriceOperation());
        eventRepository.save(entity);
        return new Event(event.getId(),event.geteventName(),event.getDescription(),event.getLocation(),event.getDate(), event.getTime(), event.getTotalTickets(), event.getSoldTickets(), event.getTicketPrice(), event.getOrganizerId(),event.getPriceOperation());
    }

    public Event createEvent(EventEntity event) {
        EventEntity savedEvent =  eventRepository.save(event);
        return convertToDTO(savedEvent);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll()
                              .stream()
                              .map(this::convertToDTO)
                              .collect(Collectors.toList());
    }

    public Event getEventById(String id) {        
        return eventRepository.findById(id)
                              .map(this::convertToDTO)
                              .orElseThrow(() -> new EntityNotFoundException("No event with id: " + id));
    }


    public void checkSales(EventEntity event){
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = event.getDate();
        if( (event.getSoldTickets() == (int)( 0.8*event.getTotalTickets() )) && (!"discount".equals(event.getPriceOperation()))
             && (!eventDate.equals(currentDate))){

            int increasedPrice =  (int)(event.getTicketPrice()*1.2);   
            event.setTicketPrice(increasedPrice);
            event.setPriceOperation("increase");
            eventRepository.save(event);      
        }
        
    }

    public Event addDiscount(String id){
        EventEntity event = eventRepository.findById(id)
                            .orElseThrow( () -> new EntityNotFoundException("No event with id:"+ id));        
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = event.getDate();
        Long daysBetween = ChronoUnit.DAYS.between(currentDate, eventDate);
        if( (daysBetween >= 0) && (daysBetween <= 3) && (event.getSoldTickets() <= event.getTotalTickets()/2)
            && (!"discount".equals(event.getPriceOperation())) ){
            int discountPrice = (int) (event.getTicketPrice()*0.8);
            event.setTicketPrice(discountPrice);
            event.setPriceOperation("discount");
            eventRepository.save(event);
        }  
        return convertToDTO(event);      
    }

    public Event increasePriceOnEventDay(String id){
        EventEntity event = eventRepository.findById(id)
                .orElseThrow ( ()-> new EntityNotFoundException("No event with id"+ id));

        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = event.getDate();
        int ticketPrice = event.getTicketPrice();
        int newPrice = ticketPrice; //default

        if((currentDate.equals(eventDate)) && (!"discount".equals(event.getPriceOperation())) && (!"eventDayIncrease".equals(event.getPriceOperation())) ){
            if("increase".equals(event.getPriceOperation())){
                newPrice = (int) Math.round(ticketPrice * 1.1);         
            }
            else{
                newPrice = (int) Math.round(ticketPrice * 1.3);
            }
            event.setPriceOperation("eventDayIncrease");
            event.setTicketPrice(newPrice);
            eventRepository.save(event);
            return convertToDTO(event);      
        }
        return convertToDTO(event);      
    }
    
    public Event updateEvent(String id, EventEntity updatedEventDTO) {
        EventEntity existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No event with id: " + id));
    
        updatedEventDTO.setId(id);
        EventEntity savedEvent = eventRepository.save(updatedEventDTO);
        return convertToDTO(savedEvent);
    }

    @Transactional
    public void deleteEvent(String id) throws EntityNotFoundException {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
                eventRepository.delete(event);
    }

    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    public Event getEventByEventName(String eventName) {
        return eventRepository.findByEventName(eventName)
                              .map(this::convertToDTO)
                              .orElseThrow(() -> new EntityNotFoundException("No event with name: " + eventName));
    }

    public List<Event> getEventsByOrganizerId(String organizerId){
         return eventRepository.findByOrganizerId(organizerId)
                              .stream()
                              .map(this::convertToDTO)
                              .collect(Collectors.toList());
    }


 
  
    
}