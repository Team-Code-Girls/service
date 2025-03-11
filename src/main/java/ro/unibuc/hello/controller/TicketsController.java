package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TicketsController {
    
    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/tickets")
    @ResponseBody
    public List<Ticket> getAllTickets() {
        return ticketsService.getAllTickets();
    }

    @PostMapping("/tickets")
    @ResponseBody
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.saveTicket(ticket);
    }

    @PutMapping("/tickets/{id}")
    @ResponseBody
    public Ticket updateTicket(@PathVariable String id, @RequestBody Ticket ticket) throws EntityNotFoundException {
        return ticketsService.updateTicket(id, ticket);
    }

    @DeleteMapping("/tickets/{id}")
    @ResponseBody
    public void deleteTicket(@PathVariable String id) throws EntityNotFoundException {
        ticketsService.deleteTicket(id);
    }

}
