package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.UsersService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UsersServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserEntity userEntity = new UserEntity("1", "Daria Horga", 21, "dariahorga@yahoo.com","helloworld1234","1234567890", 100);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        User createdUser = usersService.createUser(userEntity);

        assertNotNull(createdUser);
        assertEquals("1", createdUser.getId());
        assertEquals("Daria Horga", createdUser.getFullName());
        assertEquals(21, createdUser.getAge());
        assertEquals("dariahorga@yahoo.com", createdUser.getEmail());
        assertEquals("1234567890", createdUser.getPhoneNumber());
        assertEquals(100, createdUser.getPoints());
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> users = Arrays.asList(
                new UserEntity("1", "Daria Horga", 21, "dariahorga@yahoo.com","helloworld1234", "1234567890", 100),
                new UserEntity("2", "John Doe", 25, "johndoe@gmail.com", "helloworld1234","0987654321", 200)
        );
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = usersService.getAllUsers();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Daria Horga", result.get(0).getFullName());
        assertEquals(100, result.get(0).getPoints());
        assertEquals("2", result.get(1).getId());
        assertEquals("John Doe", result.get(1).getFullName());
        assertEquals(200, result.get(1).getPoints());
    }

    @Test
    void testGetUserById_ExistingUser() {
        String userId = "1";
        UserEntity userEntity = new UserEntity(userId, "Daria Horga", 21, "dariahorga@yahoo.com", "helloworld1234","1234567890", 100);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        Optional<User> user = usersService.getUserById(userId);
        assertTrue(user.isPresent());
        assertEquals("Daria Horga", user.get().getFullName());
    }

    @Test
    void testGetUserById_NonExistingUser() {
        String userId = "NonExistingId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<User> user = usersService.getUserById(userId);
        assertFalse(user.isPresent());
    }

    @Test
    void testUpdateUser_ExistingUser() {
        String userId = "1";
        UserEntity updatedUserEntity = new UserEntity(userId, "Daria Updated", 22, "updated@yahoo.com","helloworld1234", "1111111111", 150);
        when(userRepository.findById(userId)).thenReturn(Optional.of(updatedUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);
        Optional<User> updatedUser = usersService.updateUser(userId, updatedUserEntity);
        assertTrue(updatedUser.isPresent());
        assertEquals("Daria Updated", updatedUser.get().getFullName());
        assertEquals(150, updatedUser.get().getPoints());
    }

    @Test
    void testUpdateUser_NonExistingUser() {
        String userId = "NonExistingId";
        UserEntity updatedUserEntity = new UserEntity(userId, "Daria Updated", 22, "updated@yahoo.com","helloworld1234", "1111111111", 150);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<User> updatedUser = usersService.updateUser(userId, updatedUserEntity);
        assertFalse(updatedUser.isPresent());
    }

    @Test
    void testDeleteUser_ExistingUser() {
        String userId = "1";
        when(userRepository.existsById(userId)).thenReturn(true);
        boolean result = usersService.deleteUser(userId);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NonExistingUser() {
        String userId = "NonExistingId";
        when(userRepository.existsById(userId)).thenReturn(false);
        boolean result = usersService.deleteUser(userId);
        assertFalse(result);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testGetUserByEmail_ExistingUser() {
        String email = "dariahorga@yahoo.com";
        UserEntity userEntity = new UserEntity("1", "Daria Horga", 21, email,"helloworld1234", "1234567890", 100);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        Optional<User> user = usersService.getUserByEmail(email);
        assertTrue(user.isPresent());
        assertEquals("Daria Horga", user.get().getFullName());
    }

    @Test
    void testGetUserByFullName_ExistingUser() {
        String fullName = "Daria Horga";
        UserEntity userEntity = new UserEntity("1", fullName, 21, "dariahorga@yahoo.com","helloworld1234", "1234567890", 100);
        when(userRepository.findByFullName(fullName)).thenReturn(Optional.of(userEntity));
        Optional<User> user = usersService.getUserByFullName(fullName);
        assertTrue(user.isPresent());
        assertEquals("Daria Horga", user.get().getFullName());
    }
}
