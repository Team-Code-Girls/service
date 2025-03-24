package ro.unibuc.hello.dto;

import ro.unibuc.hello.dto.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TicketTest {
    
    Ticket myTicket = new Ticket("1", "3", "12345", 22, 3, 2025, 250);

    @Test
    void test_id(){
        Assertions.assertEquals("1", myTicket.getId());
    } 

    @Test
    void test_eventId(){
        Assertions.assertEquals("12345", myTicket.getEventId());
    }

    @Test
    void test_userId(){
        Assertions.assertEquals("3", myTicket.getUserId());
    }

    @Test 
    void test_day(){
        Assertions.assertEquals(22, myTicket.getDay());
    }

    @Test 
    void test_month(){
        Assertions.assertEquals(3, myTicket.getMonth());
    }

    @Test 
    void test_year(){
        Assertions.assertEquals(2025, myTicket.getYear());
    }

    @Test
    void test_price(){
        Assertions.assertEquals(250, myTicket.getPrice());
    }
}
