package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.dto.Event;
import ro.unibuc.hello.data.UserEntity;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.service.BuyTicketService;
import ro.unibuc.hello.service.EventService;
import ro.unibuc.hello.service.UsersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class TicketsControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private BuyTicketService buyTicketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UsersService usersService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        Ticket ticket1 = new Ticket("1", "3", "12345", 22, 3, 2025, 250);
        Ticket ticket2 = new Ticket("2", "4", "12345", 22, 3, 2025, 250);
        Ticket ticket3 = new Ticket("3", "5", "12345", 22, 3, 2025, 250);
        ticketsService.saveTicket(ticket1);
        ticketsService.saveTicket(ticket2);
        ticketsService.saveTicket(ticket3);
        Event event1 = new Event("12345", "Concert", "descriere", "București", LocalDate.of(2025, 8, 15), "20:00", 5000, 1200, 250, "7", "none");
        eventService.saveEvent(event1);
        UserEntity user1 = new UserEntity("3", "Andrei Popescu", 30, "andrei.popescu@example.com", "parola", "0723456789", 100);
        usersService.createUser(user1);
    }

    @Test
    public void testBuyTicketWithDiscount() throws Exception {
        mockMvc.perform(post("/tickets/buy/discount/12345/3/20"))
            .andExpect(status().isOk());
    }

    @Test
    public void testBuyTicket() throws Exception {
        mockMvc.perform(post("/tickets/buy/12345/3"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTickets() throws Exception {
        mockMvc.perform(get("/tickets"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(3))
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].userId").value("3"))
        .andExpect(jsonPath("$[0].eventId").value("12345"))
        .andExpect(jsonPath("$[0].day").value(22))
        .andExpect(jsonPath("$[0].month").value(3))
        .andExpect(jsonPath("$[0].year").value(2025))
        .andExpect(jsonPath("$[0].price").value(250))
        .andExpect(jsonPath("$[1].id").value("2"))
        .andExpect(jsonPath("$[1].userId").value("4"))
        .andExpect(jsonPath("$[1].eventId").value("12345"))
        .andExpect(jsonPath("$[1].day").value(22))
        .andExpect(jsonPath("$[1].month").value(3))
        .andExpect(jsonPath("$[1].year").value(2025))
        .andExpect(jsonPath("$[1].price").value(250))
        .andExpect(jsonPath("$[2].id").value("3"))
        .andExpect(jsonPath("$[2].userId").value("5"))
        .andExpect(jsonPath("$[2].eventId").value("12345"))
        .andExpect(jsonPath("$[2].day").value(22))
        .andExpect(jsonPath("$[2].month").value(3))
        .andExpect(jsonPath("$[2].year").value(2025))
        .andExpect(jsonPath("$[2].price").value(250));       
    }

    @Test
    public void testGetOneTicket() throws Exception {
        mockMvc.perform(get("/tickets/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.userId").value("3"))
        .andExpect(jsonPath("$.eventId").value("12345"))
        .andExpect(jsonPath("$.day").value(22))
        .andExpect(jsonPath("$.month").value(3))
        .andExpect(jsonPath("$.year").value(2025))
        .andExpect(jsonPath("$.price").value(250)); 
    }

    @Test
    public void testCreateTicket() throws Exception {

        String newTicket = """
        {
            "id": "4",
            "userId": "6",
            "eventId": "67890",
            "day": 15,
            "month": 5,
            "year": 2025,
            "price": 300
        }
        """;

        mockMvc.perform(post("/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newTicket))
            .andExpect(status().isOk())  
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("4"))
            .andExpect(jsonPath("$.userId").value("6"))
            .andExpect(jsonPath("$.eventId").value("67890"))
            .andExpect(jsonPath("$.day").value(15))
            .andExpect(jsonPath("$.month").value(5))
            .andExpect(jsonPath("$.year").value(2025))
            .andExpect(jsonPath("$.price").value(300));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        mockMvc.perform(delete("/tickets/4"))
            .andExpect(status().isOk());  
    }
    
}
