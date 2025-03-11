package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import main.java.ro.unibuc.hello.data.Event;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.EventService;
import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
public class EventsController {
    
    @Autowired
    private EventService eventsService;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventsService.createEvent(event);
       
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventsService.getAllEvents();
        
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable String id) {
        return eventsService.getEventById(id);
        
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable String id, @RequestBody Event event) {
        return eventsService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable String id){
        eventsService.deleteEvent(id);
        
    }

    @GetMapping("/eventName/{eventName}")
    public Event getEventByEventName(@PathVariable String eventName) {
        return eventsService.getEventByEventName(eventName);
      
    }
    

    @GetMapping("/organizer/{organizerId}")
    public List<Event> getEventsByOrganizerId(@PathVariable String organizerId) {
        return eventsService.getEventsByOrganizerId(organizerId);

    }
   
}
