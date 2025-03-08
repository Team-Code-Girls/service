package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;

import ro.unibuc.hello.data.Event;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/events")
public class EventsController {
    
    @Autowired
    private EventService eventsService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventsService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventsService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Optional<Event> event = eventsService.getEventById(id);
        return event.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event event) {
        Event updatedEvent = eventsService.updateEvent(id, event);
        return updatedEvent != null
                ? new ResponseEntity<>(updatedEvent, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        boolean isDeleted = eventsService.deleteEvent(id);
        return isDeleted
                ? new ResponseEntity<>("Event deleted", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/eventName/{eventName}")
    public ResponseEntity<Event> getEventByEventName(@PathVariable String eventName) {
        Event event = eventsService.getEventByEventName(eventName);
        return event != null
                ? new ResponseEntity<>(event, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Event>> getEventsByOrganizerId(@PathVariable String organizerId) {
        List<Event> events = eventsService.getEventsByOrganizerId(organizerId);
        return events.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(events, HttpStatus.OK);
    }
   
}
