package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.dto.Event;
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

import ro.unibuc.hello.service.EventService;
import java.time.LocalDate;


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
public class EventsControllerIntegrationTest {

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
        final String MONGO_URL = "mongodb://host.docker.internal:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @BeforeEach
    public void cleanUpAndAddTestData() {

        eventService.deleteAllEvents();
        
        Event event1 = new Event("1", "Event 1", "Descriere 1", 
                                 "Bucuresti", LocalDate.parse("2025-06-30"), 
                                 "14:00", 200, 0, 100, "3","none");   
        Event event2 = new Event("2", "Event 2", "Descriere 2", 
                                "Bucuresti", LocalDate.parse("2025-07-30"), 
                                "14:00", 300, 0, 150, "3","none");  

        eventService.saveEvent(event1);
        eventService.saveEvent(event2);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        mockMvc.perform(get("/events"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].eventName").value("Event 1"))
            .andExpect(jsonPath("$[0].description").value("Descriere 1"))
            .andExpect(jsonPath("$[0].location").value("Bucuresti"))
            .andExpect(jsonPath("$[0].date").value("2025-06-30"))
            .andExpect(jsonPath("$[0].time").value("14:00"))
            .andExpect(jsonPath("$[0].totalTickets").value(200))
            .andExpect(jsonPath("$[0].soldTickets").value(0))
            .andExpect(jsonPath("$[0].ticketPrice").value(100))
            .andExpect(jsonPath("$[0].organizerId").value("3"))
            .andExpect(jsonPath("$[1].eventName").value("Event 2"))
            .andExpect(jsonPath("$[1].description").value("Descriere 2"))
            .andExpect(jsonPath("$[1].location").value("Bucuresti"))
            .andExpect(jsonPath("$[1].date").value("2025-07-30"))
            .andExpect(jsonPath("$[1].time").value("14:00"))
            .andExpect(jsonPath("$[1].totalTickets").value(300))
            .andExpect(jsonPath("$[1].soldTickets").value(0))
            .andExpect(jsonPath("$[1].ticketPrice").value(150))
            .andExpect(jsonPath("$[1].organizerId").value("3"));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event("3", "Event 3", "Descriere 3", 
        "Bucuresti", LocalDate.parse("2025-07-30"), 
        "15:00", 100, 0, 150, "3", "none"); 

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event))) 
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.eventName").value("Event 3"));
    }

    @Test
    public void testUpdateEvent() throws Exception {

        Event event = new Event("1", "Event Updated", "Descriere 1", 
                    "Bucuresti", LocalDate.parse("2025-06-30"), 
                    "14:00", 200, 0, 100, "3","none"); 

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  
            
        mockMvc.perform(put("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.eventName").value("Event Updated"));

        mockMvc.perform(get("/events"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eventName").value("Event Updated"))
                .andExpect(jsonPath("$[1].eventName").value("Event 2"));
    }

    @Test 
    public void deleteEvent() throws Exception{

        mockMvc.perform(delete("/events/1"))
               .andExpect(status().isOk());

        mockMvc.perform(get("/events"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].eventName").value("Event 2"));        
    }

    @Test 
    public void increasePriceOnEventDay() throws Exception{

        LocalDate date = LocalDate.now();

        Event event = new Event("1", "Event 1", "Descriere 1", 
                    "Bucuresti", date, 
                    "14:00", 200, 0, 100, "3","none"); 

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  

        mockMvc.perform(put("/events/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(event)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.date").value(date.toString()));
        
        mockMvc.perform(put("/events/eventDayPrice/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(event)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.ticketPrice").value(130));
    }

    @Test 
    public void addDiscount()throws Exception{
        LocalDate date = LocalDate.now();

        Event event = new Event("1", "Event 1", "Descriere 1", 
                    "Bucuresti", date, 
                    "14:00", 200, 0, 100, "3","none"); 

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  

        mockMvc.perform(put("/events/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(event)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.date").value(date.toString()));
        
        mockMvc.perform(put("/events/discount/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.ticketPrice").value(80));




    }


}
