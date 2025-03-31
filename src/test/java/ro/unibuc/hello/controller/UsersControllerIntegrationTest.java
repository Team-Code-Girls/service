package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.UsersService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class UsersControllerIntegrationTest {

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
    private UsersService usersService;

    @BeforeEach
    public void cleanUpAndAddTestData() {

        UserEntity user1 = new UserEntity("1","Horga Daria", 21, "dariahorga@yahoo.com", "password123", "1234567890", 0);
        UserEntity user2 = new UserEntity("2","Ionescu Maria", 25, "mariaionescu@yahoo.com", "password456", "9876543210", 5);

        usersService.createUser(user1);
        usersService.createUser(user2);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[0].fullName").value("Horga Daria"))
                .andExpect(jsonPath("$[1].fullName").value("Ionescu Maria"))
                .andExpect(jsonPath("$[0].age").value(21))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[0].email").value("dariahorga@yahoo.com"))
                .andExpect(jsonPath("$[1].email").value("mariaionescu@yahoo.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$[1].phoneNumber").value("9876543210"))
                .andExpect(jsonPath("$[0].points").value(0))
                .andExpect(jsonPath("$[1].points").value(5));
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User("3", "Popescu Andrei", 30, "andrei@yahoo.com", "5555555555", 0);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.fullName").value("Popescu Andrei"))
                .andExpect(jsonPath("$.email").value("andrei@yahoo.com"));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User("1", "Horga Daria", 22, "dariahorga@yahoo.com", "1234567890", 10);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.fullName").value("Horga Daria"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.points").value(10));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("Horga Daria"))
                .andExpect(jsonPath("$[0].age").value(22));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted")); 
    }    

    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "dariahorga@yahoo.com";
        mockMvc.perform(get("/users/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.fullName").value("Horga Daria"))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testGetUserByFullName() throws Exception {
        String fullName = "Ionescu Maria";
        mockMvc.perform(get("/users/fullName/{fullName}", fullName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.fullName").value(fullName))
                .andExpect(jsonPath("$.email").value("mariaionescu@yahoo.com"));
    }
}
