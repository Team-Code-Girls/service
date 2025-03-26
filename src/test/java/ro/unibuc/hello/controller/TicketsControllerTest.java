package ro.unibuc.hello.controller;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.service.BuyTicketService;
import ro.unibuc.hello.controller.TicketsController;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GreetingsControllerTest {

    @Mock
    private TicketsService ticketsService;

    @Mock
    private BuyTicketService buyTicketsService;

    @Mock
    private TicketRepository ticketsRepository;

    @InjectMocks
    private TicketsController ticketsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketsController).build();
    }

    //buy ticket with discount
    @Test 
    void test_buyTicketWithDiscount() throws Exception {
        String eventId = "12345";
        String userId = "3";
        int discount = 20;
        doNothing().when(buyTicketsService).buyTicketWithDiscount(eventId, userId, discount);
        mockMvc.perform(post("/tickets/buy/discount/{eventId}/{userId}/{discount}", eventId, userId, discount))
                .andExpect(status().isOk());
    }
    
    //buy ticket
    @Test 
    void test_buyTicket() throws Exception {
        String eventId = "12345";
        String userId = "3";
        doNothing().when(buyTicketsService).buyTicket(eventId, userId);
        mockMvc.perform(post("/tickets/buy/{eventId}/{userId}", eventId, userId))
                .andExpect(status().isOk());
    }


    //get one ticket
    @Test 
    void test_getOneTicket() throws Exception {
        String ticketId = "1";
        Ticket ticket = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        when(ticketsService.getTicketById(ticketId)).thenReturn(ticket);
        mockMvc.perform(get("/tickets/{id}", ticketId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.userId").value("3"))
            .andExpect(jsonPath("$.eventId").value("12345"))
            .andExpect(jsonPath("$.day").value(22))
            .andExpect(jsonPath("$.month").value(3))
            .andExpect(jsonPath("$.year").value(2025))
            .andExpect(jsonPath("$.price").value(250));
    }
    
    //get all tickets
    @Test 
    void test_getAllTickets() throws Exception {
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);
        when(ticketsService.getAllTickets()).thenReturn(tickets);
        mockMvc.perform(get("/tickets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[2].id").value("3"));
    }
    
    //post a ticket
    @Test 
    void test_createTicket() throws Exception {
        String postTicketId = "1";
        String postUserId = "3";
        String postEventId = "12345";
        int postDay = 20;
        int postMonth = 3;
        int postYear = 2025;
        int postPrice = 200;
        TicketEntity ticketEntity = new TicketEntity(postTicketId, postEventId, postUserId, postDay,
                                            postMonth, postYear, postPrice);
        Ticket ticket = new Ticket(postTicketId, postUserId, postEventId,  postDay,
                                    postMonth, postYear, postPrice);
        when(ticketsService.saveTicket(any(Ticket.class))).thenReturn(ticket);
        mockMvc.perform(post("/tickets")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":\"" + postTicketId + "\","
                        + "\"userId\":\"" + postUserId + "\","
                        + "\"eventId\":\"" + postEventId + "\","
                        + "\"day\":" + postDay + ","
                        + "\"month\":" + postMonth + ","
                        + "\"year\":" + postYear + ","
                        + "\"price\":" + postPrice + "}"))
               .andExpect(status().isOk());
    }

    //delete a ticket
    @Test 
    void test_deleteTicket() throws Exception {
        String ticketId = "1";
        doNothing().when(ticketsService).deleteTicket(ticketId);
        mockMvc.perform(delete("/tickets/{id}", ticketId))
                .andExpect(status().isOk());
    }
    @Test
    void testGetMostPopularEvents() throws Exception {
        List<Map<String, Object>> mockResponse = new ArrayList<>();
        Map<String, Object> event = new HashMap<>();
        event.put("eventName", "Concert A");
        event.put("percentage", 75);
        mockResponse.add(event);

        when(ticketsService.getMostPopularEventsWithPercentage()).thenReturn(mockResponse);

        mockMvc.perform(get("/tickets/popular-events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("Concert A"))
                .andExpect(jsonPath("$[0].percentage").value(75));
    }

    @Test
    void testGetTicketCountByAgeRange() throws Exception {
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("18-25", "50");
        mockResponse.put("26-35", "30");

        when(ticketsService.getMostPopularEventByAgeRange()).thenReturn(mockResponse);

        mockMvc.perform(get("/tickets/age-stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.18-25").value("50"))
                .andExpect(jsonPath("$.26-35").value("30"));
    }
}