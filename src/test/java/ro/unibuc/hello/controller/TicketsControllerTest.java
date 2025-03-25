// package ro.unibuc.hello.controller;
 
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import ro.unibuc.hello.dto.Ticket;
// import ro.unibuc.hello.data.TicketEntity;
// import ro.unibuc.hello.data.TicketRepository;
// import ro.unibuc.hello.exception.EntityNotFoundException;
// import ro.unibuc.hello.service.TicketsService;
// import ro.unibuc.hello.service.BuyTicketService;
// import ro.unibuc.hello.controller.TicketsController;

// import java.beans.Transient;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// class GreetingsControllerTest {

//     @Mock
//     private TicketsService ticketsService;

//     @Mock
//     private TicketRepository ticketsRepository;

//     @InjectMocks
//     private TicketsController ticketsController;

//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//         mockMvc = MockMvcBuilders.standaloneSetup(ticketsController).build();
//     }

//     //buy ticket with discount
    
//     //buy ticket
    
//     //get one ticket
    
//     //get all tickets
    
//     //post a ticket

//     //delete a ticket

//     // @Test
//     // void test_deleteTicket() throws Exception {
//     //     String id = "1";
//     //     Ticket ticket = new Ticket("1", "3", "12345", 25, 3, 2025, 100);
//     //     when(ticketsService.saveTicket(any(Ticket.class))).thenReturn(ticket);
//     //     mockMvc.perform(post("/tickets")
//     //         .contentType(MediaType.APPLICATION_JSON)
//     //         .content("{\"id\":\"1\",\"userId\":\"3\",\"eventId\":\"12345\",\"day\":25,\"month\":3,\"year\":2025,\"price\":100}"))
//     //         .andExpect(status().isOk())  
//     //         .andExpect(jsonPath("$.id").value("1"))  
//     //         .andExpect(jsonPath("$.eventId").value("12345"))
//     //         .andExpect(jsonPath("$.userId").value("3"))
//     //         .andExpect(jsonPath("$.price").value(100));
//     //     mockMvc.perform(delete("/tickets/{id}", "1"))
//     //            .andExpect(status().isOk());
//     //     verify(ticketsService, times(1)).deleteTicket(id);
//     //     mockMvc.perform(get("/greetings"))
//     //             .andExpect(status().isNoContent())
//     //             .andExpect(jsonPath("$").isEmpty());
//     // }


//     @Test 
//     void test_deleteTicket() throws Exception {
//         String ticketId = "1";
//         TicketEntity ticket = new TicketEntity(ticketId, "Event1", "User1", 25, 3, 2025, 100);
//         when(ticketsRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
//         doNothing().when(ticketsService.deleteTicket(ticketId));
//         mockMvc.perform(delete("/tickets/{id}", ticketId))
//                 .andExpect(status().isOk());
//         verify(ticketsService, times(1)).deleteTicket(ticketId);
//     }

//     // @Test
//     // void test_deleteGreeting() throws Exception {
//     //     String id = "1";
//     //     Greeting greeting = new Greeting(id, "Hello");
//     //     when(greetingsService.saveGreeting(any(Greeting.class))).thenReturn(greeting);
    
//     //     // add greeting
//     //     mockMvc.perform(post("/greetings")
//     //            .content("{\"content\":\"Hello\"}")
//     //            .contentType(MediaType.APPLICATION_JSON))
//     //            .andExpect(status().isOk());
    
//     //     // delete greeting
//     //     mockMvc.perform(delete("/greetings/{id}", id))
//     //            .andExpect(status().isOk());
    
//     //     verify(greetingsService, times(1)).deleteGreeting(id);
    
//     //     // check if greeting is deleted
//     //     mockMvc.perform(get("/greetings"))
//     //            .andExpect(status().isOk())
//     //            .andExpect(jsonPath("$").isEmpty());
//     // }






//     // @Test
//     // void test_sayHello() throws Exception {
//     //     // Arrange
//     //     Greeting greeting = new Greeting("1", "Hello, there!");
//     //     when(greetingsService.hello("there")).thenReturn(greeting);
    
//     //     // Act & Assert
//     //     mockMvc.perform(get("/hello-world?name=there"))
//     //            .andExpect(status().isOk())
//     //            .andExpect(jsonPath("$.id").value("1"))
//     //            .andExpect(jsonPath("$.content").value("Hello, there!"));
//     // }
    

//     // @Test
//     // void test_info() throws Exception {
//     //     // Arrange
//     //     Greeting greeting = new Greeting("1", "there : some description");
//     //     when(greetingsService.buildGreetingFromInfo("there")).thenReturn(greeting);
    
//     //     // Act & Assert
//     //     mockMvc.perform(get("/info?title=there"))
//     //            .andExpect(status().isOk())
//     //            .andExpect(jsonPath("$.id").value("1"))
//     //            .andExpect(jsonPath("$.content").value("there : some description"));
//     // }    

//     // @Test
//     // void test_info_cascadesException() {
//     //     // Arrange
//     //     String title = "there";
//     //     when(greetingsService.buildGreetingFromInfo(title)).thenThrow(new EntityNotFoundException(title));

//     //     // Act
//     //     EntityNotFoundException exception = assertThrows(
//     //             EntityNotFoundException.class,
//     //             () -> greetingsController.info(title),
//     //             "Expected info() to throw EntityNotFoundException, but it didn't");

//     //     // Assert
//     //     assertTrue(exception.getMessage().contains(title));
//     // }

//     // @Test
//     // void test_getAllGreetings() throws Exception {
//     //     // Arrange
//     //     List<Greeting> greetings = Arrays.asList(new Greeting("1", "Hello"), new Greeting("2", "Hi"));
//     //     when(greetingsService.getAllGreetings()).thenReturn(greetings);

//     //     // Act & Assert
//     //     mockMvc.perform(get("/greetings"))
//     //         .andExpect(status().isOk())
//     //         .andExpect(jsonPath("$[0].id").value("1"))
//     //         .andExpect(jsonPath("$[0].content").value("Hello"))
//     //         .andExpect(jsonPath("$[1].id").value("2"))
//     //         .andExpect(jsonPath("$[1].content").value("Hi"));
//     // }

//     // @Test
//     // void test_createGreeting() throws Exception {
//     //     // Arrange
//     //     Greeting newGreeting = new Greeting("1", "Hello, World!");
//     //     when(greetingsService.saveGreeting(any(Greeting.class))).thenReturn(newGreeting);
    
//     //     // Act & Assert
//     //     mockMvc.perform(post("/greetings")
//     //            .content("{\"content\":\"Hello, World!\"}")
//     //            .contentType(MediaType.APPLICATION_JSON))
//     //            .andExpect(status().isOk())
//     //            .andExpect(jsonPath("$.id").value("1"))
//     //            .andExpect(jsonPath("$.content").value("Hello, World!"));
//     // }
    
//     // @Test
//     // void test_updateGreeting() throws Exception {
//     //     // Arrange
//     //     String id = "1";
//     //     Greeting updatedGreeting = new Greeting(id, "Updated Greeting");
//     //     when(greetingsService.updateGreeting(eq(id), any(Greeting.class))).thenReturn(updatedGreeting);
    
//     //     // Act & Assert
//     //     mockMvc.perform(put("/greetings/{id}", id)
//     //            .content("{\"content\":\"Updated Greeting\"}")
//     //            .contentType(MediaType.APPLICATION_JSON))
//     //            .andExpect(status().isOk())
//     //            .andExpect(jsonPath("$.id").value("1"))
//     //            .andExpect(jsonPath("$.content").value("Updated Greeting"));
//     // }
    
// }