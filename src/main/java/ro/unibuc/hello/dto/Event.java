package ro.unibuc.hello.dto;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;



public class Event {


    private String id;
    private String eventName;
    private String description;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String time;
    private int totalTickets;
    private int soldTickets;
    private int ticketPrice;
    private String organizerId;

    @JsonIgnore
    private String priceOperation = "none"; // putem avea "discount", "increase", "eventDayIncrease"

    public Event(){}

    public Event(String eventName, String description, String location, LocalDate date, String time, int totalTickets, int soldTickets, int ticketPrice, String organizerId){
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.time = time;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.ticketPrice = ticketPrice;
        this.organizerId = organizerId;
    }

    public Event(String id, String eventName, String description, String location, LocalDate date, String time, int totalTickets, int soldTickets, int ticketPrice, String organizerId, String priceOperation ) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.time = time;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.ticketPrice = ticketPrice;
        this.organizerId = organizerId;
        this.priceOperation = "none";
    }

    public String getId() { return id; }
    public void setId(String id){this.id = id;}

    public String geteventName() {return eventName;}
    public void setEventName(String eventName){ this.eventName = eventName; }

    public String getDescription(){ return description;}
    public void setDescription(String description){ this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getSoldTickets() { return soldTickets; }
    public void setSoldTickets(int soldTickets) { this.soldTickets = soldTickets; }

    public int getTicketPrice() { 
        return ticketPrice;   
    }
    public void setTicketPrice(int ticketPrice){this.ticketPrice = ticketPrice;}

    public String getOrganizerId() { return organizerId; }
    public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

    public String getPriceOperation() {return priceOperation;}
    public void setPriceOperation(String priceOperation){ this.priceOperation = priceOperation;}

    
}
