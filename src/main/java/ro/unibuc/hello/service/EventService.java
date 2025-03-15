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

    public int dynamicPrice(EventEntity event){
        // scumpire bilet 20%, dupa 80% bilete vandute
        if( event.getSoldTickets() == (int)( 0.8*event.getTotalTickets() )){
            return  (int)(event.getTicketPrice()*1.2);         
        }
        return event.getTicketPrice();
    }

    public EventEntity updateEvent(String id, EventEntity updatedEvent){
        if(!eventRepository.existsById(id)){
            throw new EntityNotFoundException("No event with id: "+ id);
        }
        updatedEvent.setId(id);
        updatedEvent.setTicketPrice(dynamicPrice(updatedEvent));
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
