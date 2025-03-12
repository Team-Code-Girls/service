package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.TicketEntity;
import ro.unibuc.hello.data.TicketRepository;
import ro.unibuc.hello.dto.Ticket;
import ro.unibuc.hello.service.TicketsService;
import ro.unibuc.hello.service.BuyTicketService;

import ro.unibuc.hello.exception.EntityNotFoundException;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private BuyTicketService buyTicketService;

    @PostMapping("/tickets/buy/discount/{eventId}/{userId}/{discount}")
    @ResponseBody
    public void buyTicketWithDiscountRoute(@PathVariable String eventId, @PathVariable String userId, @PathVariable int discount){
        buyTicketService.buyTicketWithDiscount(eventId, userId, discount);
    }

    @PostMapping("/tickets/buy/{eventId}/{userId}")
    @ResponseBody
    public void buyTicketRoute(@PathVariable String eventId, @PathVariable String userId){
        buyTicketService.buyTicket(eventId, userId);
    }

    @GetMapping("/tickets/{id}")
    @ResponseBody
    public Ticket getTicket(@PathVariable String id) throws EntityNotFoundException {
        return ticketsService.getTicketById(id);
    }

    @GetMapping("/tickets")
    @ResponseBody
    public List<Ticket> getAllTickets() {
        return ticketsService.getAllTickets();
    }

    @PostMapping("/tickets")
    @ResponseBody
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketsService.saveTicket(ticket);
    }


    @DeleteMapping("/tickets/{id}")
    @ResponseBody
    public void deleteTicket(@PathVariable String id) throws EntityNotFoundException {
        ticketsService.deleteTicket(id);
    }

    @GetMapping("/tickets/popular-events")
    @ResponseBody
    public List<Map.Entry<String, Long>> getMostPopularEvents() {
        return ticketsService.getMostPopularEvents();
    }

    @GetMapping("/tickets/age-stats")
    @ResponseBody
    public Map<String, Long> getTicketCountByAgeRange() {
        return ticketsService.getTicketCountByUserAgeRange();
    }
}