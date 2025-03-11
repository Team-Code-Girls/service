package main.java.ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;


@Component
public class TicketsService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets(){
        List<TicketEntity> tickets = ticketRepository.findAll();
        return tickets.stream()
            .map(ticket -> new Ticket(ticket.getId(), ticket.getUserId(), ticket.getEventId()))
            .collect(Collectors.toList());
    }

    public Ticket getTicketById(String id) throws EntityNotFoundException {
        Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);
        TicketEntity ticket = ticketEntity.orElseThrow(()-> new EntityNotFoundException(id));
        return new Ticket(ticket.getId(), ticket.getUserId())
    }

    public Ticket updateTicket(String id, Ticket ticket) throws EntityNotFoundException {
        TicketEntity entity = ticketRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));

        entity.setUserId(ticket.getUserId);
        
    }

    public Greeting updateGreeting(String id, Greeting greeting) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        entity.setTitle(greeting.getContent());
        informationRepository.save(entity);
        return new Greeting(entity.getId(), entity.getTitle());
    }

    public void deleteGreeting(String id) throws EntityNotFoundException {
        InformationEntity entity = informationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        informationRepository.delete(entity);
    }
    
}
