package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.service.UsersService;

import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

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

    public List<Map.Entry<String, Long>> getMostPopularEvents() {
        List<TicketEntity> tickets = ticketsRepository.findAll();
        
        Map<String, Long> eventCount = tickets.stream()
                .collect(Collectors.groupingBy(TicketEntity::getEventId, Collectors.counting()));

        return eventCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());
    }
    public Map<String, Long> getTicketCountByUserAgeRange() {
        List<TicketEntity> tickets = ticketsRepository.findAll();
        Map<String, Long> ageRangeTicketCount = new HashMap<>();
    
        for (TicketEntity ticket : tickets) {
            usersService.getUserById(ticket.getUserId()).ifPresent(user -> {
                int age = user.getAge();
                String ageRange = getAgeRange(age); 
                ageRangeTicketCount.put(ageRange, ageRangeTicketCount.getOrDefault(ageRange, 0L) + 1);
            });
        }
    
        return ageRangeTicketCount;
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
