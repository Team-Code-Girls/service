package ro.unibuc.hello.data;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

public class EventEntity {

    @Id 
    private String id;

    private String eventName;
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private int totalTickets;
    private int soldTickets;
    private int ticketPrice;
    private String organizerId;

    public EventEntity(){}

    public EventEntity(String eventName, String description, String location, LocalDateTime dateTime, int totalTickets, int soldTickets, int ticketPrice, String organizerId){
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.ticketPrice = ticketPrice;
        this.organizerId = organizerId;
    }

    public EventEntity(String id, String eventName, String description, String location, LocalDateTime dateTime, int totalTickets, int soldTickets, int ticketPrice, String organizerId) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.ticketPrice = ticketPrice;
        this.organizerId = organizerId;
    }

    public String getId() { return id; }
    public void setId(String id){this.id = id;}

    public String geteventName() {return eventName;}
    public void setEventName(String eventName){ this.eventName = eventName; }

    public String getDescription(){ return description;}
    public void setDescription(String description){ this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getSoldTickets() { return soldTickets; }
    public void setSoldTickets(int soldTickets) { this.soldTickets = soldTickets; }

    public int getTicketPrice() { return ticketPrice;}
    public void setTicketPrice(int ticketPrice){this.ticketPrice = ticketPrice;}

    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

    @Override
    public String toString() {
        String result = String.format(
            "Event[id='%s', eventName='%s', location='%s', dateTime='%s', totalTickets=%d, soldTickets=%d, ticketPrice=%d, organizerId='%s']",
            id, eventName, location, dateTime, totalTickets,soldTickets, ticketPrice, organizerId);
    
        if (description != null && !description.isEmpty()) {
            result += ", description= " + description;
        }

        return result;
    }
    
}