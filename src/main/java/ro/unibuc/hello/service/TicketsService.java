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

import java.util.Optional;


@Component
public class TicketsService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket){
        TicketEntity entity = new TicketEntity();
        
    }

    public Ticket getTicketById(String id) throws EntityNotFoundException {
        Optional<TicketEntity> optionalEntity = ticketRepository.findById(id);
        TicketEntity entity = optionalEntity.orElseThrow(() -> new EntityNotFoundException(id));
        return new Ticket(entity.getId(), entity.getTitle());
    }

    public List<Ticket> getAllTickets(){
        List<TicketEntity> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticket -> new Ticket(ticket.getId(), ticket.getTitle()))
                .collect(Collectors.toList());
    }

    public void deleteTicket(String id) throws EntityNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));
        ticketRepository.delete(ticket);
    }
}
