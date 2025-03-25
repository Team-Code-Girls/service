package ro.unibuc.hello.dto;

import java.beans.Transient;

import ro.unibuc.hello.dto.Event;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class EventTest {

    private Event event;

    Event eventObj1 = new Event("1","Event 1", "Descriere 1","Bucuresti", LocalDate.parse("2025-03-30"),"14:00", 200, 0, 100, "3");
    Event eventObj2 = new Event("Event 2", "Descriere 2","Bucuresti", LocalDate.parse("2025-03-29"),"16:00", 100, 0, 50, "4");

    @BeforeEach
    void setUp(){
        event = new Event();
    }
   

    @Test 
    void test_SetAndGetId(){
        event.setId("123");
        assertEquals("123", event.getId());

        assertEquals("1", eventObj1.getId());
    }

    @Test 
    void test_SetAndGetEventName(){
        event.setEventName("Event Unit-Test");
        assertSame("Event Unit-Test", event.geteventName());

        assertSame("Event 1", eventObj1.geteventName());
        assertSame("Event 2", eventObj2.geteventName());
    }

    @Test 
    void test_SetAndGetDescription(){
        event.setDescription("Description");
        assertSame("Description", event.getDescription());

        assertSame("Descriere 1", eventObj1.getDescription());
        assertSame("Descriere 2", eventObj2.getDescription());
    }

    @Test 
    void test_SetAndGetLocation(){
        event.setLocation("Bd-ul Unirii 12");
        assertSame("Bd-ul Unirii 12", event.getLocation());

        assertSame("Bucuresti", eventObj1.getLocation());
        assertSame("Bucuresti", eventObj2.getLocation());
    }

    @Test 
    void test_SetAndGetDate(){
        event.setDate(LocalDate.parse("2025-03-24"));
        assertEquals(LocalDate.parse("2025-03-24"), event.getDate());

        assertEquals(LocalDate.parse("2025-03-30"), eventObj1.getDate());
        assertEquals(LocalDate.parse("2025-03-29"), eventObj2.getDate());
    }


    @Test 
    void test_SetAndGetTime(){
        event.setTime("18:00");
        assertSame("18:00", event.getTime());

        assertSame("14:00", eventObj1.getTime());
        assertSame("16:00", eventObj2.getTime());
    }

    @Test 
    void test_SetAndGetTotalTickets(){
        event.setTotalTickets(100);
        assertEquals(100, event.getTotalTickets());

        assertEquals(200, eventObj1.getTotalTickets());
        assertEquals(100, eventObj2.getTotalTickets());
    }

    @Test 
    void test_SetAndGetSoldTickets(){
        event.setSoldTickets(0);
        assertEquals(0, event.getSoldTickets());

        assertEquals(0, eventObj1.getSoldTickets());
        assertEquals(0, eventObj2.getSoldTickets());
    }

    @Test 
    void test_SetAndGetTicketPrice(){
        event.setTicketPrice(175);
        assertEquals(175, event.getTicketPrice());

        assertEquals(100, eventObj1.getTicketPrice());
        assertEquals(50, eventObj2.getTicketPrice());
    }

    @Test 
    void test_SetAndGetOrganizerId(){
        event.setOrganizerId("321");
        assertEquals("321", event.getOrganizerId());

        
        assertEquals("3", eventObj1.getOrganizerId());
        assertEquals("4", eventObj2.getOrganizerId());
    }

    @Test 
    void test_SetAndGetPriceOperation(){
        event.setPriceOperation("increase");
        assertSame("increase", event.getPriceOperation());
    }



}
