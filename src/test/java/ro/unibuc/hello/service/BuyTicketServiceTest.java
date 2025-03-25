package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.service.BuyTicketService;

import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.service.EventService;

import ro.unibuc.hello.exception.EntityNotFoundException;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate; 

@ExtendWith(SpringExtension.class)
public class BuyTicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock 
    private EventRepository eventRepository;

    @Mock 
    private UserRepository userRepository;

    @Mock
    private TicketsService ticketsService;

    @Mock
    private EventService eventsService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private BuyTicketService buyTicketsService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    void testBuyTicketWhenEventIdIsWrong() {
        String eventId = "idGresit";
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicket(eventId, userId));
    }

    @Test 
    void testBuyTicketWhenUserIdIsWrong() {
        String eventId = "12345";
        String userId = "idGresit";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicket(eventId, userId));
    }

    @Test 
    void testBuyTicketWhenThereAreNoMoreTicketsAvailable() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 100, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicket(eventId, userId));
    }

    // @Test 
    // void testBuyTicketWithPriceInFirstRange() {
    //     String eventId = "12345";
    //     EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
    //                                             100, 0, 45, "100", "operatie");
    //     String userId = "3";
    //     UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
    //                                         "0725661234", 0);

    //     when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
    //     when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    //     List<Ticket> tickets = new ArrayList<>();
    //     when(ticketsService.getAllTickets()).thenReturn(tickets);

    //     doNothing().when(eventsService).checkSales(any(EventEntity.class));

    //     when(eventsService.updateEvent(anyString(), any(EventEntity.class)).thenReturn();
    //     when(usersService.updateUser(anyString(), any(UserEntity.class));
    //     doNothing().when(ticketRepository).save(any(TicketEntity.class));

    //     buyTicketsService.buyTicket(eventId, userId);
    //     assertEquals(1, event.getSoldTickets());
    //     assertEquals(2, user.getPoints());
    // }

    void testBuyTicketWithPriceInFirstRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 10, 45, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);

        List<Ticket> existingTickets = List.of(new Ticket("0", eventId, userId, 1, 1, 2024, 50));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ticketsService.getAllTickets()).thenReturn(existingTickets);
        when(ticketRepository.save(any(TicketEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        buyTicketsService.buyTicket(eventId, userId);

        assertEquals(11, event.getSoldTickets()); 
        assertEquals(2, user.getPoints());

        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        verify(eventsService, times(1)).updateEvent(eq(eventId), any(EventEntity.class));
        verify(usersService, times(1)).updateUser(eq(userId), any(UserEntity.class));
    }

    // @Test 
    // void testBuyTicketWithPriceInSecondRange() {

    // }

    // @Test 
    // void testBuyTicketWithPriceInThirdRange() {

    // }

    // @Test 
    // void testBuyTicketWithPriceInFourthRange() {

    // }

    // @Test 
    // void testBuyTicketWithPriceInFifthRange() {

    // }

    
}
