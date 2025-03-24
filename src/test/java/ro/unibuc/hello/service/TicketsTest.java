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
    private TicketsService ticketsService = new TicketsService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    void testSaveTicket() {
        Ticket myTicket = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        when(ticketsService.saveTicket(myTicket)).thenReturn(myTicket);

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
    
}
