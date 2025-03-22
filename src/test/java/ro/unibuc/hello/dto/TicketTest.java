package ro.unibuc.hello.dto;

import ro.unibuc.hello.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TicketTest {
    
    Ticket myTicket = new Ticket("1", "12345", "3", 22, 3, 2025, 250);

    @Test
    void test_id(){
        Assertions.assertSame("1", myTicket.getId());
    } 

    @Test
    void test_eventId(){
        Assertions.assertSame("12345", myTicket.getEventId());
    }

    @Test
    void test_userId(){
        Assertions.assertSame("3", myTicket.getUserId());
    }

    @Test 
    void test_day(){
        Assertions.assertSame(22, myTicket.getDay());
    }

    @Test 
    void test_month(){
        Assertions.assertSame(3, myTicket.getMonth());
    }

    @Test 
    void test_year(){
        Assertions.assertSame(2025, myTicket.getYear());
    }

    @Test
    void test_price(){
        Assertions.assertSame(250, myTicket.getPrice());
    }
}
