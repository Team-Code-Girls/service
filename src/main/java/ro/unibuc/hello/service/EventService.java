package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import main.java.ro.unibuc.hello.data.Event;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEventById(String id){
        return eventRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Event with id:" +id+" not found."));
    }

    public Event updateEvent(String id, Event updatedEvent){
        if(!eventRepository.existsById(id)){
            throw new EntityNotFoundException("No event with id: "+ id);
        }
        updatedEvent.setId(id);
        return eventRepository.save(updatedEvent);
    }

    public void deleteEvent(String id){
        if (!eventRepository.existsById(id)){
            throw new EntityNotFoundException("Event with id:" +id+" not found.");
            
        }
    }

    public Event getEventByEventName(String eventName){
      return eventRepository.findByEventName(eventName)
                .orElseThrow( () -> new EntityNotFoundException("No event with name: " + eventName)) ;
    }

    public List<Event> getEventsByOrganizerId(String organizerId){
         List<Event> events =  eventRepository.findByOrganizerId(organizerId);
         if (events.isEmpty()) {
            throw new EntityNotFoundException("No events found for organizer : " + organizerId);
        }
        
        return events;
    }


 
  
    
}
