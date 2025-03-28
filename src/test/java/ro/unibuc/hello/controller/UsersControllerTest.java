package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.data.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void test_createUser() throws Exception {
        String postUserId = "1";
        String postUserName = "Horga Daria";
        String postUserEmail = "dariahorga@yahoo.com";
        String postUserPhone = "1234567890";
        String postUserPassword = "helloworld1234";
        int postUserAge = 21;

        UserEntity newUserEntity = new UserEntity(postUserName, postUserAge, postUserEmail, postUserPassword, postUserPhone, 0);
        User createdUser = new User(postUserId, postUserName, postUserAge, postUserEmail, postUserPhone, 0);

        when(usersService.createUser(any(UserEntity.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + postUserId + "\","
                        + "\"fullName\":\"" + postUserName + "\","
                        + "\"email\":\"" + postUserEmail + "\","
                        + "\"age\":" + postUserAge + ","
                        + "\"password\":\"" + postUserPassword + "\","
                        + "\"phoneNumber\":\"" + postUserPhone + "\","
                        + "\"points\": 0" + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postUserId))
                .andExpect(jsonPath("$.fullName").value(postUserName))
                .andExpect(jsonPath("$.email").value(postUserEmail))
                .andExpect(jsonPath("$.phoneNumber").value(postUserPhone))
                .andExpect(jsonPath("$.age").value(postUserAge))
                .andExpect(jsonPath("$.points").value(0));  
    }

    @Test
    void test_getAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("1", "Horga Daria", 21, "dariahorga@yahoo.com", "1234567890", 0),
                new User("2", "Ionescu Maria", 25, "mariaionescu@yahoo.com", "9876543210", 5)
        );
        when(usersService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].fullName").value("Horga Daria"))
                .andExpect(jsonPath("$[0].email").value("dariahorga@yahoo.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].age").value(21))
                .andExpect(jsonPath("$[0].points").value(0))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].fullName").value("Ionescu Maria"))
                .andExpect(jsonPath("$[1].email").value("mariaionescu@yahoo.com"))
                .andExpect(jsonPath("$[1].phoneNumber").value("9876543210"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].points").value(5));
    }

    @Test
    void test_getUserById() throws Exception {
        String userId = "1";
        User user = new User(userId, "Horga Daria", 21, "dariahorga@yahoo.com", "1234567890", 0);
        when(usersService.getUserById(userId)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.fullName").value("Horga Daria"))
                .andExpect(jsonPath("$.email").value("dariahorga@yahoo.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.points").value(0));
    }

    @Test
    void test_updateUser() throws Exception {
        String userId = "1";
        User updatedUser = new User(userId, "Horga Daria", 22, "dariahorga@yahoo.com", "1234567890", 10);
        UserEntity updatedUserEntity = new UserEntity("Horga Daria", 22, "dariahorga@yahoo.com", "helloworld1234", "1234567890", 10);

        when(usersService.updateUser(eq(userId), any(UserEntity.class))).thenReturn(Optional.of(updatedUser));
        mockMvc.perform(put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fullName\":\"Horga Daria\","
                        + "\"age\":22,"
                        + "\"email\":\"dariahorga@yahoo.com\","
                        + "\"phoneNumber\":\"1234567890\","
                        + "\"password\":\"" + "helloworld"+ "\","
                        + "\"points\": 10" + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.fullName").value("Horga Daria"))
                .andExpect(jsonPath("$.email").value("dariahorga@yahoo.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.points").value(10));
    }

    @Test
    void test_deleteUser() throws Exception {
        String userId = "1";
        when(usersService.deleteUser(userId)).thenReturn(true);
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        verify(usersService, times(1)).deleteUser(userId);
    }

    @Test
    void test_getUserByEmail() throws Exception {
        String email = "dariahorga@yahoo.com";
        User user = new User("1", "Horga Daria", 21, email, "1234567890", 0);
        when(usersService.getUserByEmail(email)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.fullName").value("Horga Daria"))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.points").value(0));
    }

    @Test
    void test_getUserByFullName() throws Exception {
        String fullName = "Horga Daria";
        User user = new User("1", fullName, 21, "dariahorga@yahoo.com", "1234567890", 0);
        when(usersService.getUserByFullName(fullName)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/fullName/{fullName}", fullName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.fullName").value(fullName))
                .andExpect(jsonPath("$.email").value("dariahorga@yahoo.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.points").value(0));
    }
}
