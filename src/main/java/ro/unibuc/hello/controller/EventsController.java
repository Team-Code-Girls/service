package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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


    @PostMapping("/events")
    public EventEntity createEvent(@RequestBody EventEntity event) {
        return eventsService.createEvent(event);
       
    }

    @GetMapping("/events")
    public List<EventEntity> getAllEvents() {
        return eventsService.getAllEvents();
        
    }

    @GetMapping("/events/{id}")
    public EventEntity getEventById(@PathVariable String id) {
        return eventsService.getEventById(id);
        
    }

    @PutMapping("/events/discount/{id}")
    public EventEntity addDiscount(@PathVariable String id){
        return eventsService.addDiscount(id);
    }

    @PutMapping("/events/eventDayPrice/{id}")
    public EventEntity increasePriceOnEventDay(@PathVariable String id){
        return eventsService.increasePriceOnEventDay(id);
    }

    @PutMapping("/events/{id}")
    public EventEntity updateEvent(@PathVariable String id, @RequestBody EventEntity event) {
        return eventsService.updateEvent(id, event);
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable String id){
        eventsService.deleteEvent(id);
        
    }

    @GetMapping("/events/eventName/{eventName}")
    public EventEntity getEventByEventName(@PathVariable String eventName) {
        return eventsService.getEventByEventName(eventName);
      
    }
    

    @GetMapping("/events/organizer/{organizerId}")
    public List<EventEntity> getEventsByOrganizerId(@PathVariable String organizerId) {

        return eventsService.getEventsByOrganizerId(organizerId);

    }
   
}