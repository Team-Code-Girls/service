package main.java.ro.unibuc.hello.dto;


public class Ticket {
    private String id;
    private String userId;
    private String eventId;
    private int day;
    private int month;
    private int year;

    public Ticket() {}
    
    public Ticket(String id, String userId, String eventId, int day, int month, int year){
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
}
