package main.java.ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.ro.unibuc.hello.data.TicketEntity;
import main.java.ro.unibuc.hello.dto.Ticket;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class TicketsService {

    @Autowired
    private TicketRepository ticketsRepository;

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
}
