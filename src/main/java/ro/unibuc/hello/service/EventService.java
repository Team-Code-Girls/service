package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import main.java.ro.unibuc.hello.data.Event;
import ro.unibuc.hello.data.EventRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String id){
        return eventRepository.findById(id);
    }

    public Event updateEvent(String id, Event updatedEvent){
        if(eventRepository.existsById(id)){
            updatedEvent.setId(id);
            return eventRepository.save(updatedEvent);
        }
        return null;
    }

    public boolean deleteEvent(String id){
        if (eventRepository.existsById(id)){
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Event> getEventByEventName(String eventName){
        return eventRepository.findByEventName(eventName);
    }

    public List<Event> getEventsByOrganizerId(String organizerId){
         return eventRepository.findByOrganizerId(organizerId);
    }


 
  
    
}
