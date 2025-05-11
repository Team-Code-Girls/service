package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ro.unibuc.hello.dto.Event;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.EventService;
import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class EventsController {
    
    @Autowired
    private EventService eventsService;


    @Counted(value = "events.create.count", description = "Events created")
    @PostMapping("/events")
    public Event createEvent(@RequestBody EventEntity event) {
        return eventsService.createEvent(event);
       
    }

    @Timed(value = "events.fetch.time", description = "Time taken to fetch all events.")
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventsService.getAllEvents();
        
    }

    @Counted(value = "eventsId.fetch.count", description = "Times events were returned")
    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable String id) {
        return eventsService.getEventById(id);
        
    }

    @PutMapping("/events/discount/{id}")
    public Event addDiscount(@PathVariable String id){
        return eventsService.addDiscount(id);
    }

    @PutMapping("/events/eventDayPrice/{id}")
    public Event increasePriceOnEventDay(@PathVariable String id){
        return eventsService.increasePriceOnEventDay(id);
    }

    @Counted(value = "events.update.count", description = "Times events were returned")
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable String id, @RequestBody EventEntity event) {
        return eventsService.updateEvent(id, event);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable String id){
        eventsService.deleteEvent(id);
        
    }
    @DeleteMapping("/events")
    public void deleteAllEvents(){
        eventsService.deleteAllEvents();
    }

    @Counted(value = "eventsName.fetch.count", description = "Times events were returned")
    @GetMapping("/events/eventName/{eventName}")
    public Event getEventByEventName(@PathVariable String eventName) {
        return eventsService.getEventByEventName(eventName);
      
    }
    
    @Counted(value = "eventsOrganizerId.fetch.count", description = "Times events were returned")
    @GetMapping("/events/organizer/{organizerId}")
    public List<Event> getEventsByOrganizerId(@PathVariable String organizerId) {

        return eventsService.getEventsByOrganizerId(organizerId);

    }
   
}