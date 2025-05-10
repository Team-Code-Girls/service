package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import ro.unibuc.hello.service.EventService;
import ro.unibuc.hello.controller.EventsController;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.dto.Event;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventsControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventsController eventsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventsController).build();
    }

    @Test
    void test_createEvent() throws Exception {
        String postEventId = "1";
        String postEventName = "Event 1";
        String postEventDescription = "Descriere";
        String postEventLocation = "Bucuresti";
        LocalDate postEventDate = LocalDate.parse("2025-03-30");
        String postEventTime = "14:00";
        int postEventTotalTickets = 200;
        int postEventSoldTickets = 0;
        int postEventTicketPrice = 100;
        String postEventOrganizerId = "3";
        String postPriceOperation = "none";

        EventEntity newEventEntity = new EventEntity(postEventId, postEventName, postEventDescription, postEventLocation,
                postEventDate, postEventTime, postEventTotalTickets, postEventSoldTickets, postEventTicketPrice, postEventOrganizerId, postPriceOperation);
        Event createdEvent = new Event(postEventId, postEventName, postEventDescription, postEventLocation,
                postEventDate, postEventTime, postEventTotalTickets, postEventSoldTickets, postEventTicketPrice, postEventOrganizerId, postPriceOperation);

        when(eventService.createEvent(any(EventEntity.class))).thenReturn(createdEvent);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + postEventId + "\","
                        + "\"eventName\":\"" + postEventName + "\","
                        + "\"description\":\"" + postEventDescription + "\","
                        + "\"location\":\"" + postEventLocation + "\","
                        + "\"time\":\"" + postEventTime + "\","
                        + "\"totalTickets\":" + postEventTotalTickets + ","
                        + "\"soldTickets\":" + postEventSoldTickets + ","
                        + "\"ticketPrice\":" + postEventTicketPrice + ","
                        + "\"organizerId\":\"" + postEventOrganizerId + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postEventId))
                .andExpect(jsonPath("$.eventName").value(postEventName))
                .andExpect(jsonPath("$.description").value(postEventDescription))
                .andExpect(jsonPath("$.location").value(postEventLocation))
                .andExpect(jsonPath("$.time").value(postEventTime))
                .andExpect(jsonPath("$.totalTickets").value(postEventTotalTickets))
                .andExpect(jsonPath("$.soldTickets").value(postEventSoldTickets))
                .andExpect(jsonPath("$.ticketPrice").value(postEventTicketPrice))
                .andExpect(jsonPath("$.organizerId").value(postEventOrganizerId));
    }

    @Test
    void test_getAllEvents() throws Exception {
        List<Event> events = Arrays.asList(
                new Event("1", "Event 1", "Descriere 1", "Bucuresti", LocalDate.parse("2025-03-30"), "14:00", 200, 0, 100, "3", "none"),
                new Event("2", "Event 2", "Descriere 2", "Cluj", LocalDate.parse("2025-04-01"), "16:00", 300, 50, 120, "4", "none")
        );

        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].eventName").value("Event 1"))
                .andExpect(jsonPath("$[0].description").value("Descriere 1"))
                .andExpect(jsonPath("$[0].location").value("Bucuresti"))
                //.andExpect(jsonPath("$[0].date").value("2025-03-30"))
                .andExpect(jsonPath("$[0].time").value("14:00"))
                .andExpect(jsonPath("$[0].totalTickets").value(200))
                .andExpect(jsonPath("$[0].soldTickets").value(0))
                .andExpect(jsonPath("$[0].ticketPrice").value(100))
                .andExpect(jsonPath("$[0].organizerId").value("3"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].eventName").value("Event 2"))
                .andExpect(jsonPath("$[1].description").value("Descriere 2"))
                .andExpect(jsonPath("$[1].location").value("Cluj"))
                //.andExpect(jsonPath("$[1].date").value("2025-04-01"))
                .andExpect(jsonPath("$[1].time").value("16:00"))
                .andExpect(jsonPath("$[1].totalTickets").value(300))
                .andExpect(jsonPath("$[1].soldTickets").value(50))
                .andExpect(jsonPath("$[1].ticketPrice").value(120))
                .andExpect(jsonPath("$[1].organizerId").value("4"));
    }

    @Test
    void test_getEventById() throws Exception {
        String eventId = "1";
        Event event = new Event(eventId, "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-30"), "14:00", 200, 0, 100, "3", "none");

        when(eventService.getEventById(eventId)).thenReturn(event);

        mockMvc.perform(get("/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.eventName").value("Event 1"))
                .andExpect(jsonPath("$.description").value("Descriere"))
                .andExpect(jsonPath("$.location").value("Bucuresti"))
                //.andExpect(jsonPath("$.date").value("2025-03-30"))
                .andExpect(jsonPath("$.time").value("14:00"))
                .andExpect(jsonPath("$.totalTickets").value(200))
                .andExpect(jsonPath("$.soldTickets").value(0))
                .andExpect(jsonPath("$.ticketPrice").value(100))
                .andExpect(jsonPath("$.organizerId").value("3"));
    }

    @Test
    void test_getEventByEventName() throws Exception {
        String eventName = "Event 1";
        Event event = new Event("1", eventName, "Descriere", "Bucuresti", LocalDate.parse("2025-03-30"), "14:00", 200, 0, 100, "3", "none");

        when(eventService.getEventByEventName(eventName)).thenReturn(event);

        mockMvc.perform(get("/events/eventName/{eventName}", eventName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.eventName").value("Event 1"))
                .andExpect(jsonPath("$.description").value("Descriere"))
                .andExpect(jsonPath("$.location").value("Bucuresti"))
                //.andExpect(jsonPath("$.date").value("2025-03-30"))
                .andExpect(jsonPath("$.time").value("14:00"))
                .andExpect(jsonPath("$.totalTickets").value(200))
                .andExpect(jsonPath("$.soldTickets").value(0))
                .andExpect(jsonPath("$.ticketPrice").value(100))
                .andExpect(jsonPath("$.organizerId").value("3"));
    }

    @Test
    void test_getEventsByOrganizerId() throws Exception {
        String organizerId = "3";
        List<Event> event = Arrays.asList(new Event("1", "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-30"), "14:00", 200, 0, 100, organizerId, "none"));

        when(eventService.getEventsByOrganizerId(organizerId)).thenReturn(event);

        mockMvc.perform(get("/events/organizer/{organizerId}", organizerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].eventName").value("Event 1"))
                .andExpect(jsonPath("$[0].description").value("Descriere"))
                .andExpect(jsonPath("$[0].location").value("Bucuresti"))
                //.andExpect(jsonPath("$.date").value("2025-03-30"))
                .andExpect(jsonPath("$[0].time").value("14:00"))
                .andExpect(jsonPath("$[0].totalTickets").value(200))
                .andExpect(jsonPath("$[0].soldTickets").value(0))
                .andExpect(jsonPath("$[0].ticketPrice").value(100))
                .andExpect(jsonPath("$[0].organizerId").value("3"));
    }

    @Test
    void test_deleteEvent() throws Exception {
        String eventId = "1";

        doNothing().when(eventService).deleteEvent(eventId);

        mockMvc.perform(delete("/events/{id}", eventId))
              .andExpect(status().isOk()); 

    }
    @Test
    void test_deleteAllEvents() throws Exception {
        doNothing().when(eventService).deleteAllEvents(); 

        mockMvc.perform(delete("/events"))
                .andExpect(status().isOk()); 
    }

    

    @Test
    void test_addDiscount() throws Exception {
        Event event = new Event("1", "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-27"), "14:00", 200, 0, 100, "3", "none");

        Event discountedEvent = new Event("1", "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-27"), "14:00", 200, 0, 80, "3", "none");

        when(eventService.addDiscount("1")).thenReturn(discountedEvent);

        mockMvc.perform(put("/events/discount/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.eventName").value("Event 1"))
                .andExpect(jsonPath("$.description").value("Descriere"))
                .andExpect(jsonPath("$.location").value("Bucuresti"))
                //.andExpect(jsonPath("$.date").value("2025-03-30"))
                .andExpect(jsonPath("$.time").value("14:00"))
                .andExpect(jsonPath("$.totalTickets").value(200))
                .andExpect(jsonPath("$.soldTickets").value(0))
                .andExpect(jsonPath("$.ticketPrice").value(80))
                .andExpect(jsonPath("$.organizerId").value("3"));
    }
    
    @Test
    void test_increasePriceOnEventDay_notEventDay() throws Exception {
        EventEntity eventEntity = new EventEntity("1", "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-28"), "14:00", 200, 0, 100, "3", "none");

        Event event = new Event("1", "Event 1", "Descriere", "Bucuresti", LocalDate.parse("2025-03-28"), "14:00", 200, 0, 100, "3", "none");

        when(eventService.increasePriceOnEventDay("1")).thenReturn(event);

        mockMvc.perform(put("/events/eventDayPrice/{id}", "1"))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.ticketPrice").value(100));  
        }






}
