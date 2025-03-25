package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    User user = new User("1", "Horga Daria", 21, "dariahorga@yahoo.com", "1234567890", 100);

    @Test
    void test_getId() {
        Assertions.assertEquals("1", user.getId());
    }

    @Test
    void test_getFullName() {
        Assertions.assertEquals("Horga Daria", user.getFullName());
    }

    @Test
    void test_getAge() {
        Assertions.assertEquals(21, user.getAge());
    }

    @Test
    void test_getEmail() {
        Assertions.assertEquals("dariahorga@yahoo.com", user.getEmail());
    }

    @Test
    void test_getPhoneNumber() {
        Assertions.assertEquals("1234567890", user.getPhoneNumber());
    }
    @Test
    void test_getPoints() {
        Assertions.assertEquals(100, user.getPoints());
    }
}
