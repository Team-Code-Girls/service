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
import java.util.ArrayList;

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
    @Autowired 
    private UsersService usersService;

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
            int priceTicket = event.getTicketPrice();
            int numberOfPoints = 0;

            if(priceTicket>0&&priceTicket<50){
                numberOfPoints = 2;
            }else{
                if(priceTicket>=50&&priceTicket<100){
                    numberOfPoints = 5;
                }else{
                    if(priceTicket>=100&&priceTicket<150){
                        numberOfPoints = 10;
                    }else{
                        if(priceTicket>=150&&priceTicket<200){
                            numberOfPoints = 15;
                        }else{
                            numberOfPoints = 20;
                        }
                    }
                }
            }
            user.setPoints(user.getPoints()+numberOfPoints);
            usersService.updateUser(userId, user);
            
        }else{
            throw new EntityNotFoundException("No tickets available for event: " + eventId);
        }
    }

    public void buyTicketWithDiscountedPrice(String eventId, String userId){
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

    public void buyTicketWithDiscount(String eventId, String userId, int discount){

        EventEntity event = eventRepository.findById(eventId).orElseThrow(
            () -> new EntityNotFoundException("No event with id: " + eventId));
        UserEntity user = userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("No user with id: " + userId));

        LocalDate currentDate = LocalDate.now();

        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        int numberOfPoints = user.getPoints();

        ArrayList<Integer> reduceriDisponibile = new ArrayList<>();

        if(event.getSoldTickets()<event.getTotalTickets()){

            if(numberOfPoints>=50){
                reduceriDisponibile.add(20);
            }
            if(numberOfPoints>=100){
                reduceriDisponibile.add(50);
            }
            if(numberOfPoints>=200){
                reduceriDisponibile.add(100);
            }

            if(reduceriDisponibile.contains(discount)){
                if(discount == 20){
                    user.setPoints(user.getPoints()-50);
                    usersService.updateUser(userId, user);
                    buyTicketWithDiscountedPrice(eventId, userId);
                }
                if(discount == 50){
                    user.setPoints(user.getPoints()-100);
                    usersService.updateUser(userId, user);
                    buyTicketWithDiscountedPrice(eventId, userId);
                }
                if(discount == 100){
                    user.setPoints(user.getPoints()-200);
                    usersService.updateUser(userId, user);
                    buyTicketWithDiscountedPrice(eventId, userId);
                }
            }else{
                if(discount==20||discount==50||discount==100){
                    throw new EntityNotFoundException("You don't have the required number of points in order to use this discount.");
                }else{
                    throw new EntityNotFoundException("This discount value is not available.");
                }
            }
        }
        
    }
    
}
