package ro.unibuc.hello.dto;

import java.beans.Transient;

import ro.unibuc.hello.dto.Event;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class EventTest {

    private Event event;

    @BeforeEach
    void setUp(){
        event = new Event();
    }
   

    @Test 
    void test_SetAndGetId(){
        event.setId("123");
        assertEquals("123", event.getId());
    }

    @Test 
    void test_SetAndGetEventName(){
        event.setEventName("Event Unit-Test");
        assertSame("Event Unit-Test", event.geteventName());
    }

    @Test 
    void test_SetAndGetDescription(){
        event.setDescription("Description");
        assertSame("Description", event.getDescription());
    }

    @Test 
    void test_SetAndGetLocation(){
        event.setLocation("Bd-ul Unirii 12");
        assertSame("Bd-ul Unirii 12", event.getLocation());
    }

    @Test 
    void test_SetAndGetDate(){
        event.setDate(LocalDate.parse("2025-03-24"));
        assertEquals(LocalDate.parse("2025-03-24"), event.getDate());
    }

    @Test 
    void test_SetAndGetTime(){
        event.setTime("18:00");
        assertSame("18:00", event.getTime());
    }

    @Test 
    void test_SetAndGetTotalTickets(){
        event.setTotalTickets(100);
        assertEquals(100, event.getTotalTickets());
    }

    @Test 
    void test_SetAndGetSoldTickets(){
        event.setSoldTickets(0);
        assertEquals(0, event.getSoldTickets());
    }

    @Test 
    void test_SetAndGetTicketPrice(){
        event.setTicketPrice(175);
        assertEquals(175, event.getTicketPrice());
    }

    @Test 
    void test_SetAndGetOrganizerId(){
        event.setOrganizerId("321");
        assertEquals("321", event.getOrganizerId());
    }

    @Test 
    void test_SetAndGetPriceOperation(){
        event.setPriceOperation("increase");
        assertSame("increase", event.getPriceOperation());
    }

}
