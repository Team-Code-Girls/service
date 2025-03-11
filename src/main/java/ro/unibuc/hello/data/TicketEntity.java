package main.java.ro.unibuc.hello.data;

import java.lang.annotation.Inherited;

import org.springframework.data.annotation.Id;


public class TicketEntity {
    
    @Id 
    private String id;

    private String eventId;
    private String userId;
    private int day;
    private int month;
    private int year;


    public TicketEntity() {}

    public TicketEntity(String eventId, String userId, int day, int month, int year) {
        this.eventId = eventId;
        this.userId = userId;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public TicketEntity(String id, String eventId, String userId, int day, int month, int year){
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }


    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }


    public String getEventId(){
        return eventId;
    }
    public void setEventId(string eventId){
        this.eventId = eventId;
    }

    
    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public int getMonth(){
        return month;
    }
    public void setMonth(int month){
        this.month = month;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }


    @Override
    public String toString(){
        return String.format(
            "Bilet[id=%s, userId=%s, eventId=%s, day=%d, month=%d, year=%d]",
             id, userId, eventId, day, month, year
        )
    }
}
