package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.UserEntity;

import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.dto.User;

import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.service.EventService;

import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.data.EventRepository;
import ro.unibuc.hello.data.UserRepository;

import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BuyTicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketsService ticketService;
    @Autowired
    private EventService eventService;

    public void buyTicket(String eventId, String userId){
        EventEntity event = eventRepository.findById(eventId).orElseThrow(
            () -> new EntityNotFoundException("No event with id: " + eventId));
        UserEntity user = userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("No user with id: " + userId));

        LocalDate currentDate = LocalDate.now();

        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        if(event.getSoldTickets()<event.getTotalTickets()){
            event.setSoldTickets(event.getSoldTickets()+1);
            eventService.updateEvent(eventId, event);
            List<Ticket> tickets = ticketService.getAllTickets();
            int idTicket = tickets.size();
            TicketEntity ticket = new TicketEntity(String.valueOf(idTicket), eventId, userId, day, month, year);
            ticketRepository.save(ticket);
        }else{
            throw new EntityNotFoundException("No tickets available for event: " + eventId);
        }
    }
    
}
