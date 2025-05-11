package ro.unibuc.hello.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.service.EventService;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.dto.Event;

@ExtendWith(SpringExtension.class)
public class EventsServiceTest {

    @Mock 
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService = new EventService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    void testCreateEvent(){
        EventEntity eventEntity = new EventEntity("1","Event Service", "Descriere","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3" ,"none");
        when(eventRepository.save(eventEntity)).thenReturn(eventEntity);

        Event event = eventService.createEvent(eventEntity);

        assertNotNull(event);
        assertSame("1", event.getId());
        assertSame("Event Service", event.geteventName());
        assertSame("Descriere", event.getDescription());
        assertSame("Bucuresti", event.getLocation());
        assertEquals(LocalDate.parse("2025-03-30"), event.getDate());
        assertSame("14:00", event.getTime());
        assertEquals(200, event.getTotalTickets());
        assertEquals(0, event.getSoldTickets());
        assertEquals(100, event.getTicketPrice());
        assertSame("3", event.getOrganizerId());

    }

    @Test
    void testSaveEvent() {
       
        Event inputEvent = new Event(
            "1", "Sample Event", "Sample Description", "Bucharest",
            LocalDate.parse("2025-06-01"), "18:00", 100, 10, 50,
            "org123", "none"
        );

        Event result = eventService.saveEvent(inputEvent);

        assertNotNull(result);
        assertEquals(inputEvent.getId(), result.getId());
        assertEquals(inputEvent.geteventName(), result.geteventName());
        assertEquals(inputEvent.getDescription(), result.getDescription());
        assertEquals(inputEvent.getLocation(), result.getLocation());
        assertEquals(inputEvent.getDate(), result.getDate());
        assertEquals(inputEvent.getTime(), result.getTime());
        assertEquals(inputEvent.getTotalTickets(), result.getTotalTickets());
        assertEquals(inputEvent.getSoldTickets(), result.getSoldTickets());
        assertEquals(inputEvent.getTicketPrice(), result.getTicketPrice());
        assertEquals(inputEvent.getOrganizerId(), result.getOrganizerId());
        assertEquals(inputEvent.getPriceOperation(), result.getPriceOperation());

        verify(eventRepository, times(1)).save(any(EventEntity.class));
    }


    @Test 
    void testGetAllEvents(){
        List<EventEntity> events = Arrays.asList(
            new EventEntity("1","Event 1", "Descriere 1","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3","none"),
            new EventEntity("2", "Event 2", "Descriere 2","Bucuresti", LocalDate.parse("2025-03-29"),"16:00", 100, 0, 50, "4","none")
        );
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> allEvents = eventService.getAllEvents();
        assertNotNull(allEvents);
        assertEquals("1", allEvents.get(0).getId());
        assertEquals("Event 1", allEvents.get(0).geteventName());
        assertEquals("Descriere 1", allEvents.get(0).getDescription());

        assertEquals("2", allEvents.get(1).getId());
        assertEquals("Event 2", allEvents.get(1).geteventName());
        assertEquals("Descriere 2", allEvents.get(1).getDescription());
        
    }

    @Test 
    void testGetEventById_ExistingEntity() {
        EventEntity eventEntity = new EventEntity("1","Event Service", "Descriere","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3","none");
        when(eventRepository.findById("1")).thenReturn(Optional.of(eventEntity));

        Event event = eventService.getEventById("1");
        assertNotNull(event);
        assertEquals("Event Service", event.geteventName()); 

    }

    @Test 
    void testGetEventById_NonExistingEntity() {
        String id = "0";
        when(eventRepository.findById("0")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.getEventById("0")); 

    }

    @Test 
    void testGetEventByEventName(){
        EventEntity eventEntity = new EventEntity("1","Event Service", "Descriere","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3","none");
        when(eventRepository.findByEventName("Event Service")).thenReturn(Optional.of(eventEntity));

        Event event = eventService.getEventByEventName("Event Service");
        assertNotNull(event);
        assertEquals("1", event.getId()); 

    }

    @Test 
    void testGetEventByEventName_NonExistingEntity(){
        String eventName = "Non existing";
        when(eventRepository.findByEventName(eventName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.getEventByEventName("Non existing")); 

    }

    @Test
    void testGetEventsByOrganizerId_ExistingEntity() {
        String organizerId = "3";
        List<EventEntity> eventEntities = Arrays.asList(
            new EventEntity("1", "Event 1", "Descriere 1", "Bucuresti", LocalDate.parse("2025-03-30"), "14:00", 200, 0, 100, organizerId,"none"),
            new EventEntity("2", "Event 2", "Descriere 2", "Bucuresti", LocalDate.parse("2025-04-01"), "16:00", 150, 0, 80, organizerId,"none")
        );
        when(eventRepository.findByOrganizerId(organizerId)).thenReturn(eventEntities);

        List<Event> result = eventService.getEventsByOrganizerId(organizerId);

        assertNotNull(result);
        assertEquals("Event 1", result.get(0).geteventName());
        assertEquals("Event 2", result.get(1).geteventName());
    }

    @Test
    void testGetEventsByOrganizerId_NonExistingEntity() {
        String organizerId = "non"; 
        when(eventRepository.findByOrganizerId(organizerId)).thenReturn(List.of());

        List<Event> result = eventService.getEventsByOrganizerId(organizerId);

        assertTrue(result.isEmpty());

    }

    @Test 
    void testUpdateEvent_ExistingEntity(){
        String eventId = "1"; 
        EventEntity updatedEventEntity = new EventEntity(eventId,"Event Service", "Descriere","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3","none");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(updatedEventEntity));
        when(eventRepository.save(any(EventEntity.class))).thenReturn(updatedEventEntity);

        Event updatedEvent = eventService.updateEvent(eventId, updatedEventEntity);
        assertNotNull(updatedEvent);
        assertEquals(eventId, updatedEvent.getId());
        assertEquals("Event Service", updatedEvent.geteventName());

    }

    @Test 
    void testUpdateEvent_NonExistingEntity(){
        String eventId = "non";
        EventEntity updatedEventEntity = new EventEntity(eventId,"Event Service", "Descriere","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3","none");
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.updateEvent(eventId, updatedEventEntity)); 

    }

    @Test 
    void testDeleteEvent(){
        String eventId = "1";
        EventEntity eventEntity = new EventEntity(eventId, "Event Service", "Descriere", 
                                                  "Bucuresti", LocalDate.parse("2025-03-30"), 
                                                  "14:00", 200, 0, 100, "3","none");    
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
  
        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));
        verify(eventRepository, times(1)).delete(eventEntity); 

    }

    @Test
    void testDeleteEvent_NonExistingEntity() {
        String eventId = "non";
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.deleteEvent(eventId));
        verify(eventRepository, never()).delete(any(EventEntity.class));
    }

    @Test 
    void testCheckSales_Increase(){
        EventEntity eventEntity = new EventEntity("1", "Event Service", "Descriere", 
                                                  "Bucuresti", LocalDate.parse("2025-06-30"), 
                                                  "14:00", 100, 80, 50, "3","none");    
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        eventService.checkSales(eventEntity);

        assertEquals(60, eventEntity.getTicketPrice()); 
        assertEquals("increase", eventEntity.getPriceOperation());

    }

    @Test 
    void testCheckSales_NoIncrease(){
        LocalDate date = LocalDate.now();
        EventEntity eventEntity = new EventEntity("1", "Event Service", "Descriere", 
                                                  "Bucuresti", date, 
                                                  "14:00", 100, 80, 50, "3","none");    
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        eventService.checkSales(eventEntity);

        assertEquals(50, eventEntity.getTicketPrice()); 
        assertEquals("none", eventEntity.getPriceOperation());

    }

    @Test 
    void testAddDiscount_Valid(){
        LocalDate date = LocalDate.now();
        EventEntity eventEntity = new EventEntity("1","Event Service", "Descriere","Bucuresti", date,"14:00", 200, 0, 100, "3","none");
        when(eventRepository.findById("1")).thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        eventService.addDiscount("1");

        assertEquals(80, eventEntity.getTicketPrice());
        assertEquals("discount", eventEntity.getPriceOperation());

    }

    @Test 
    void testAddDiscount_NonValid(){
        EventEntity eventEntity = new EventEntity("1", "Event Service", "Descriere", 
                                                  "Bucuresti", LocalDate.parse("2025-03-27"), 
                                                  "14:00", 100, 81, 50, "3","none");    
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        eventService.checkSales(eventEntity);

        assertEquals(50, eventEntity.getTicketPrice()); 
        assertEquals("none", eventEntity.getPriceOperation());

    }

    @Test
    void testAddDiscount_TooEarlyForDiscount() {
        LocalDate futureDate = LocalDate.now().plusDays(10);
        EventEntity eventEntity = new EventEntity("2", "Event", "Desc", 
                                                "Cluj", futureDate, "12:00", 
                                                100, 0, 100, "3", "none");

        when(eventRepository.findById("2")).thenReturn(Optional.of(eventEntity));

        eventService.addDiscount("2");

        assertEquals(100, eventEntity.getTicketPrice()); 
        assertEquals("none", eventEntity.getPriceOperation());
    }

   @Test
    void testAddDiscount_EventNotFound() {
        when(eventRepository.findById("1")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            eventService.addDiscount("1");
        });

        assertEquals("Entity: No event with id:1 was not found", exception.getMessage());
    }



    @Test
    void testAddDiscount_EventInPast() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        EventEntity eventEntity = new EventEntity("4", "Past Event", "Desc", 
                                                "Iasi", pastDate, "10:00", 
                                                100, 20, 100, "3", "none");

        when(eventRepository.findById("4")).thenReturn(Optional.of(eventEntity));

        eventService.addDiscount("4");

        assertEquals(100, eventEntity.getTicketPrice()); 
        assertEquals("none", eventEntity.getPriceOperation());
    }



    @Test 
    void testIncreasePriceOnEventDay_Valid(){
        LocalDate date = LocalDate.now();
        EventEntity eventEntity = new EventEntity("1", "Event Service", "Descriere", 
                                 "Bucuresti", date, 
                                 "14:00", 100, 81, 50, "3","none");   
                                 
        when(eventRepository.findById("1")).thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        eventService.increasePriceOnEventDay("1");

        assertEquals(65, eventEntity.getTicketPrice());
        assertEquals("eventDayIncrease", eventEntity.getPriceOperation());

        
    }

   @Test
    void testIncreasePriceOnEventDay_NotToday() {
        LocalDate date = LocalDate.now().plusDays(1); 
        EventEntity eventEntity = new EventEntity("5", "Event", "Desc", "Iasi", 
            date, "15:00", 100, 20, 100, "3", "none");

        when(eventRepository.findById("5")).thenReturn(Optional.of(eventEntity));

        Event result = eventService.increasePriceOnEventDay("5");

        assertEquals(100, result.getTicketPrice());
        assertEquals("none", result.getPriceOperation()); 
    }







}
