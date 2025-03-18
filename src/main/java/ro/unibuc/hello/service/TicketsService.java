package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.exception.NoTicketsFoundException;  
import ro.unibuc.hello.exception.NoEventsFoundException;
import ro.unibuc.hello.exception.NoUsersFoundException;

import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class TicketsService {

    @Autowired
    private TicketRepository ticketsRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UsersService usersService;

    public Ticket saveTicket(Ticket ticket){
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setUserId(ticket.getUserId());
        entity.setEventId(ticket.getEventId());
        entity.setDay(ticket.getDay());
        entity.setMonth(ticket.getMonth());
        entity.setYear(ticket.getYear());
        ticketsRepository.save(entity);
        return new Ticket(entity.getId(), entity.getUserId(), entity.getEventId(), 
                        entity.getDay(), entity.getMonth(), entity.getYear());
    }

    public Ticket getTicketById(String id) throws EntityNotFoundException {
        Optional<TicketEntity> optionalEntity = ticketsRepository.findById(id);
        TicketEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        return new Ticket(entity.getId(), entity.getUserId(), entity.getEventId(), 
                        entity.getDay(), entity.getMonth(), entity.getYear());
    }

    public List<Ticket> getAllTickets(){
        List<TicketEntity> tickets = ticketsRepository.findAll();
        return tickets.stream()
                .map(ticket -> new Ticket(ticket.getId(), ticket.getUserId(), ticket.getEventId(), ticket.getDay(),
                            ticket.getMonth(), ticket.getYear()))
                .collect(Collectors.toList());
    }

    public void deleteTicket(String id) throws EntityNotFoundException {
        TicketEntity ticket = ticketsRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));
        ticketsRepository.delete(ticket);
    }

    public List<Map<String, Object>> getMostPopularEventsWithPercentage() {
        long totalTickets = ticketsRepository.count();
        if (totalTickets == 0) {
            throw new NoTicketsFoundException("No tickets found in the database.");
        }
        List<TicketEntity> tickets = ticketsRepository.findAll();
        Map<String, Long> eventCount = tickets.stream()
                .collect(Collectors.groupingBy(TicketEntity::getEventId, Collectors.counting()));
    
        return eventCount.entrySet().stream()
                .map(entry -> {
                    String eventId = entry.getKey();
                    long ticketCount = entry.getValue();
                    double percentage = (ticketCount * 100.0) / totalTickets;
    
                    String eventName = eventRepository.findById(eventId)
                        .map(event -> event.geteventName())
                        .orElseThrow(() -> new NoEventsFoundException("Event with ID " + eventId + " not found."));
    
                    Map<String, Object> eventData = new HashMap<>();
                    eventData.put("percentage", String.format("%.2f%%", percentage));
                    eventData.put("eventName", eventName);
    
                    return eventData;
                })
                .sorted((e1, e2) -> Long.compare((long) e2.get("count"), (long) e1.get("count")))
                .collect(Collectors.toList());
    }
    

    public Map<String, String> getMostPopularEventByAgeRange() {
        List<TicketEntity> tickets = ticketsRepository.findAll();
        if (tickets.isEmpty()) {
            throw new NoTicketsFoundException("No tickets found in the database.");
        }
        Map<String, Map<String, Long>> ageRangeEventCount = new HashMap<>();
        for (TicketEntity ticket : tickets) {
            Optional<User> userOpt = usersService.getUserById(ticket.getUserId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                int age = user.getAge();
                String ageRange = getAgeRange(age);
                ageRangeEventCount.putIfAbsent(ageRange, new HashMap<>());
                Map<String, Long> eventCount = ageRangeEventCount.get(ageRange);
                eventCount.put(ticket.getEventId(), eventCount.getOrDefault(ticket.getEventId(), 0L) + 1);
            } else {
                throw new NoUsersFoundException("No user found with ID:" + ticket.getUserId());
            }
        }
        Map<String, String> result = new HashMap<>();
        for (String ageRange : ageRangeEventCount.keySet()) {
            Map<String, Long> eventCount = ageRangeEventCount.get(ageRange);
            String mostPopularEventId = eventCount.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new NoEventsFoundException("No events found in this age range."));
    
            String eventName = eventRepository.findById(mostPopularEventId)
                    .map(EventEntity::geteventName)
                    .orElseThrow(() -> new NoEventsFoundException("No event found with ID: "+ mostPopularEventId));
    
            result.put(ageRange, eventName);
        }
        return result;
    }
    
    private String getAgeRange(int age) {
        if (age < 18) {
            return "<18";
        } else if (age >= 18 && age <= 20) {
            return "18-20";
        } else if (age >= 21 && age <= 30) {
            return "21-30";
        } else if (age >= 31 && age <= 40) {
            return "31-40";
        } else if (age >= 41 && age <= 50) {
            return "41-50";
        } else if (age >= 51 && age <= 60) {
            return "51-60";
        } else {
            return "60+";
        }
    }
    
}
