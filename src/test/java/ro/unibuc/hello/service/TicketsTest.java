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
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.service.TicketsService;

import ro.unibuc.hello.exception.EntityNotFoundException;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TicketsTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketsService ticketsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    void testSaveTicket() {

        Ticket myTicket = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        TicketEntity ticketEntity = new TicketEntity("1", "12345", "3", 22, 3, 2025, 250);

        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);

        Ticket savedTicket = ticketsService.saveTicket(myTicket);

        assertNotNull(savedTicket);
        assertEquals("1", savedTicket.getId());
        assertEquals("3", savedTicket.getUserId());
        assertEquals("12345", savedTicket.getEventId());
        assertEquals(22, savedTicket.getDay());
        assertEquals(3, savedTicket.getMonth());
        assertEquals(2025, savedTicket.getYear());
        assertEquals(250, savedTicket.getPrice());
    }

    @Test 
    void testGetTicketById() {
        TicketEntity ticketEntity = new TicketEntity("1", "12345", "3", 22, 3, 2025, 250);
        when(ticketRepository.findById("1")).thenReturn(Optional.of(ticketEntity));
        Ticket ticketWithId = ticketsService.getTicketById("1");
        assertNotNull(ticketWithId);
        assertEquals("1", ticketWithId.getId());
        assertEquals("3", ticketWithId.getUserId());
        assertEquals("12345", ticketWithId.getEventId());
        assertEquals(22, ticketWithId.getDay());
        assertEquals(3, ticketWithId.getMonth());
        assertEquals(2025, ticketWithId.getYear());
        assertEquals(250, ticketWithId.getPrice());
        String id = "NonExistingId";
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ticketsService.getTicketById(id));
    }

    @Test 
    void testDeleteTicketWhenNonExistingTicket() {
        String id = "NonExistingId";
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ticketsService.deleteTicket(id));
        verify(ticketRepository, times(0)).delete(any(TicketEntity.class));
    }

    @Test 
    void testDeleteTicketWhenExistingTicket() {
        TicketEntity ticket = new TicketEntity("1", "12345", "3", 22, 3, 2025, 250);
        when(ticketRepository.findById("1")).thenReturn(Optional.of(ticket));
        ticketsService.deleteTicket("1");
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    void testGetAllTickets() {
        TicketEntity ticket1 = new TicketEntity("1", "12345", "3", 22, 3, 2025, 250);
        TicketEntity ticket2 = new TicketEntity("2", "12345", "4", 22, 3, 2025, 250);
        TicketEntity ticket3 = new TicketEntity("3", "12345", "5", 22, 3, 2025, 250);
        List<TicketEntity> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketRepository.findAll()).thenReturn(tickets);
        List<Ticket> returnedTickets = ticketsService.getAllTickets();
        //verific numarul de bilete din lista
        assertEquals(3, returnedTickets.size());
        //verific ca biletele au id-urile corecte
        assertEquals("1", returnedTickets.get(0).getId());
        assertEquals("2", returnedTickets.get(1).getId());
        assertEquals("3", returnedTickets.get(2).getId());
        //verific ca biletele au eventId-urile corecte
        assertEquals("12345", returnedTickets.get(0).getEventId());
        assertEquals("12345", returnedTickets.get(1).getEventId());
        assertEquals("12345", returnedTickets.get(2).getEventId());
        //verific ca biletele au userId-urile corecte
        assertEquals("3", returnedTickets.get(0).getUserId());
        assertEquals("4", returnedTickets.get(1).getUserId());
        assertEquals("5", returnedTickets.get(2).getUserId());
        //verific ca biletele au ziua corecta
        assertEquals(22, returnedTickets.get(0).getDay());
        assertEquals(22, returnedTickets.get(1).getDay());
        assertEquals(22, returnedTickets.get(2).getDay());
        //verific ca biletele au luna corecta
        assertEquals(3, returnedTickets.get(0).getMonth());
        assertEquals(3, returnedTickets.get(1).getMonth());
        assertEquals(3, returnedTickets.get(2).getMonth());
        //verific ca biletele au anul corect
        assertEquals(2025, returnedTickets.get(0).getYear());
        assertEquals(2025, returnedTickets.get(1).getYear());
        assertEquals(2025, returnedTickets.get(2).getYear());
        //verific ca biletele au pretul corect
        assertEquals(250, returnedTickets.get(0).getPrice());
        assertEquals(250, returnedTickets.get(1).getPrice());
        assertEquals(250, returnedTickets.get(2).getPrice());
    }

    
    
}
