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

    @Test 
    void testBuyTicketWithDiscountedPriceWhenEventIdIsWrong() {
        String eventId = "idGresit";
        String userId = "3";
        int discount = 20;
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscountedPrice(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountedPriceWhenUserIdIsWrong() {
        String eventId = "12345";
        String userId = "idGresit";
        int discount = 20;
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscountedPrice(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountedPriceWhenOutOfTickets() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 100, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        int discount = 20;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscountedPrice(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountedPrice() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        int discount = 20;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicketWithDiscountedPrice(eventId, userId, discount);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test 
    void testBuyTicketWithPriceInFirstRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 45, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicket(eventId, userId);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        assertEquals(2, user.getPoints());
    }

    @Test 
    void testBuyTicketWithPriceInSecondRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 60, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicket(eventId, userId);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        assertEquals(5, user.getPoints());
    }

    @Test 
    void testBuyTicketWithPriceInThirdRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 120, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicket(eventId, userId);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        assertEquals(10, user.getPoints());
    }

    @Test 
    void testBuyTicketWithPriceInFourthRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 160, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicket(eventId, userId);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        assertEquals(15, user.getPoints());
    }

    @Test 
    void testBuyTicketWithPriceInFifthRange() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        buyTicketsService.buyTicket(eventId, userId);
        assertEquals(1, event.getSoldTickets());
        verify(eventRepository, times(1)).findById(eventId); 
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
        assertEquals(20, user.getPoints());
    }

    @Test 
    void testBuyTicketWithDiscountWhenDiscountIsNotAvailableForUser() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        int discount = 20;

        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscount(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountWhenDiscountIsNotAvailable() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        int discount = 70;
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscount(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscount20() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 60);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        int discount = 20;
        buyTicketsService.buyTicketWithDiscount(eventId, userId, discount);
        assertEquals(1, event.getSoldTickets());
        verify(usersService, times(1)).updateUser(userId, user);
        assertEquals(10, user.getPoints());
    }

    @Test 
    void testBuyTicketWithDiscount50() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 110);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        int discount = 50;
        buyTicketsService.buyTicketWithDiscount(eventId, userId, discount);
        assertEquals(1, event.getSoldTickets());
        verify(usersService, times(1)).updateUser(userId, user);
        assertEquals(10, user.getPoints());
    }

    @Test 
    void testBuyTicketWithDiscount100() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 210);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        int discount = 100;
        buyTicketsService.buyTicketWithDiscount(eventId, userId, discount);
        assertEquals(1, event.getSoldTickets());
        verify(usersService, times(1)).updateUser(userId, user);
        assertEquals(10, user.getPoints());
    }

    @Test 
    void testBuyTicketWithDiscountWhenEventIdIsWrong() {
        String eventId = "idGresit";
        String userId = "3";
        int discount = 20;
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscount(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountWhenUserIdIsWrong() {
        String eventId = "12345";
        String userId = "idGresit";
        int discount = 20;
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 0, 250, "100", "operatie");
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscount(eventId, userId, discount));
    }

    @Test 
    void testBuyTicketWithDiscountWhenOutOfTickets() {
        String eventId = "12345";
        EventEntity event = new EventEntity("12345", "eveniment", "descriere", "locatie", LocalDate.parse("2025-03-30"), "14:00",
                                                100, 100, 250, "100", "operatie");
        String userId = "3";
        UserEntity user = new UserEntity("3", "Alexandra", 26, "Alexandra@email.com", "parola", 
                                            "0725661234", 0);
        int discount = 20;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(EntityNotFoundException.class, () -> buyTicketsService.buyTicketWithDiscount(eventId, userId, discount));
    }
}
