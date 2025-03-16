package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.EventRepository;
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

    public EventEntity createEvent(EventEntity event) {
        return eventRepository.save(event);
    }

    public List<EventEntity> getAllEvents(){
        return eventRepository.findAll();
    }

    public EventEntity getEventById(String id){
        return eventRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Event with id:" +id+" not found."));
    }


    //TO DO: conditii - sa nu se aplice in ziua event-ului
    //       de adaugat apel in BuyTicketService
    public void checkSales(EventEntity event){
        // scumpire bilet 20%, dupa 80% bilete vandute
       
        if( (event.getSoldTickets() == (int)( 0.8*event.getTotalTickets() )) && (!"discount".equals(event.getPriceOperation()))){
            int increasedPrice =  (int)(event.getTicketPrice()*1.2);   
            event.setTicketPrice(increasedPrice);
            event.setPriceOperation("increase");
            eventRepository.save(event);      
        }
        
    }

    public EventEntity addDiscount(String id){
        EventEntity event = eventRepository.findById(id)
                            .orElseThrow( () -> new EntityNotFoundException("No event with id:"+ id));        
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = event.getDate();
        Long daysBetween = ChronoUnit.DAYS.between(currentDate, eventDate);
        if( (daysBetween >= 0) && (daysBetween <= 3) && (event.getSoldTickets() <= event.getTotalTickets()/2)){
            int discountPrice = (int) (event.getTicketPrice()*0.8);
            event.setTicketPrice(discountPrice);
            event.setPriceOperation("discount");
            eventRepository.save(event);
        }  
        return event;      
    }

    public EventEntity updateEvent(String id, EventEntity updatedEvent){
        if(!eventRepository.existsById(id)){
            throw new EntityNotFoundException("No event with id: "+ id);
        }
        updatedEvent.setId(id);
        eventRepository.save(updatedEvent);
        return updatedEvent;
    }

    public void deleteEvent(String id){
        if (!eventRepository.existsById(id)){
            throw new EntityNotFoundException("Event with id:" +id+" not found.");
            
        }
    }

    public EventEntity getEventByEventName(String eventName){
      return eventRepository.findByEventName(eventName)
                .orElseThrow( () -> new EntityNotFoundException("No event with name: " + eventName)) ;
    }

    public List<EventEntity> getEventsByOrganizerId(String organizerId){
         List<EventEntity> events =  eventRepository.findByOrganizerId(organizerId);
         if (events.isEmpty()) {
            throw new EntityNotFoundException("No events found for organizer : " + organizerId);
        }
        
        return events;
    }


 
  
    
}
