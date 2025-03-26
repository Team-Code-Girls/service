package ro.unibuc.hello.service;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import static org.mockito.Mockito.*;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.NoTicketsFoundException;
import ro.unibuc.hello.exception.NoUsersFoundException;
import ro.unibuc.hello.exception.NoEventsFoundException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {

    @Mock
    private TicketRepository ticketsRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private TicketsService ticketsService;

    private List<TicketEntity> mockTickets;

    @BeforeEach
    void setUp() {
        mockTickets = Arrays.asList(
            new TicketEntity("1", "event1", "user1", 1, 1, 2024, 50),
            new TicketEntity("2", "event2", "user2", 2, 1, 2024, 60),
            new TicketEntity("3", "event1", "user3", 3, 1, 2024, 55)
        );
    }

    @Test
    void testGetMostPopularEventsWithPercentage() {
        when(ticketsRepository.count()).thenReturn((long) mockTickets.size());
        when(ticketsRepository.findAll()).thenReturn(mockTickets);

        when(eventRepository.findById("event1")).thenReturn(Optional.of(new EventEntity(
            "event1", "Concert", "Descriere concert", "Iasi", LocalDate.of(2025, 3, 19), "19:00", 100, 78, 50, "5678", "none")));
    
        when(eventRepository.findById("event2")).thenReturn(Optional.of(new EventEntity(
            "event2", "Festival", "Descriere festival", "Bucharest", LocalDate.of(2025, 3, 20), "20:00", 200, 150, 60, "1234", "none")));

        List<Map<String, Object>> result = ticketsService.getMostPopularEventsWithPercentage();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Concert", result.get(0).get("eventName"));
        assertEquals("Festival", result.get(1).get("eventName"));
    }


    @Test
    void testGetMostPopularEventsWithPercentage_NoTickets() {
        when(ticketsRepository.count()).thenReturn(0L);

        assertThrows(NoTicketsFoundException.class, () -> {
            ticketsService.getMostPopularEventsWithPercentage();
        });
    }
    @Test
    void testGetMostPopularEventsWithPercentage_EventNotFound() {
        when(ticketsRepository.count()).thenReturn((long) mockTickets.size());
        when(ticketsRepository.findAll()).thenReturn(mockTickets);
        when(eventRepository.findById("event1")).thenReturn(Optional.empty());
    
        NoEventsFoundException exception = assertThrows(NoEventsFoundException.class, 
            () -> ticketsService.getMostPopularEventsWithPercentage());
    
        assertEquals("Event with ID event1 not found.", exception.getMessage());
    }
    @Test
    void testGetMostPopularEventByAgeRange() {
        when(ticketsRepository.findAll()).thenReturn(mockTickets);

        when(usersService.getUserById("user1")).thenReturn(Optional.of(new User(
            "user1", "Daria Updated", 22, "updated@yahoo.com", "1111111111", 150
        )));
        when(usersService.getUserById("user2")).thenReturn(Optional.of(new User(
            "user2", "John Doe", 31, "johndoe@gmail.com", "2222222222", 200
        )));
        when(usersService.getUserById("user3")).thenReturn(Optional.of(new User(
            "user3", "Alice Smith", 27, "alice@gmail.com", "3333333333", 300
        )));

        when(eventRepository.findById("event1")).thenReturn(Optional.of(new EventEntity(
            "event1", "Concert", "Descriere eveniment 1",  "Iasi", LocalDate.of(2025, 3, 19), "19:00", 100, 78, 50, "1234", "none"
        )));
        when(eventRepository.findById("event2")).thenReturn(Optional.of(new EventEntity(
            "event2", "Festival", "Descriere eveniment 2","Bucharest", LocalDate.of(2025, 3, 20), "20:00", 200, 150, 60, "1234","none"
        )));

        Map<String, String> result = ticketsService.getMostPopularEventByAgeRange();

        assertNotNull(result);
        assertTrue(result.containsKey("21-30"));
        assertEquals("Concert", result.get("21-30"));
    }

    @Test
    void testGetMostPopularEventByAgeRange_NoTickets() {
        when(ticketsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoTicketsFoundException.class, () -> {
            ticketsService.getMostPopularEventByAgeRange();
        });
    }

    @Test
    void testGetMostPopularEventByAgeRange_NoUserFound() {
        when(ticketsRepository.findAll()).thenReturn(mockTickets);
        when(usersService.getUserById("user1")).thenReturn(Optional.empty());

        assertThrows(NoUsersFoundException.class, () -> {
            ticketsService.getMostPopularEventByAgeRange();
        });
    }
    @Test
    void testGetMostPopularEventByAgeRange_NoEventFound() {
        when(ticketsRepository.findAll()).thenReturn(mockTickets);

        when(usersService.getUserById("user1")).thenReturn(Optional.of(new User(
            "user1", "Daria Updated", 25, "daria@gmail.com", "1111111111", 150
        )));
        when(usersService.getUserById("user2")).thenReturn(Optional.of(new User(
            "user2", "John Doe", 30, "john@gmail.com", "2222222222", 200
        )));
        when(usersService.getUserById("user3")).thenReturn(Optional.of(new User(
            "user3", "Alice Smith", 22, "alice@gmail.com", "3333333333", 250
        )));
        when(eventRepository.findById("event1")).thenReturn(Optional.empty());
        assertThrows(NoEventsFoundException.class, () -> {
            ticketsService.getMostPopularEventByAgeRange();
        });
    }
}
