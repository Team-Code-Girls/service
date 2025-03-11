package main.java.ro.unibuc.hello.data;

import java.lang.annotation.Inherited;

import org.springframework.data.annotation.Id;


public class TicketEntity {
    
    @Id 
    private String id;

    private String eventId;
    private String userId;

    public TicketEntity() {}

    public TicketEntity(String eventId, String userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public String getId(){
        return id;
    }

    public String getEventId(){
        return eventId;
    }

    public String setEventId(String eventId){
        this.eventId = eventId;
    }

    public String getUserId(){
        return userId;
    }

    public String setUserId(String userId){
        this.userId = userId;
    }

    @Override
    public String toString(){
        return String.format(
            "Bilet[id=%s, userId=%s, eventId=%s]", id, userId, eventId
        )
    }
}
