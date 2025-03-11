package main.java.ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/tickets")


    @GetMapping("/tickets/{id}")

    @PostMapping("/tickets")
    @ResponseBody
    public List<Ticket> getAllTickets() {
        return ticketsService.getAllTickets();
    }

    @DeleteMapping("/tickets/{id}")
    @ResponseBody
    public void deleteTicket(@PathVariable String id) throws EntityNotFoundException {
        ticketsService.deleteTicket(id);
    }
}










@RestController
@RequestMapping("/api/tickets")
public class TicketsController {
    
    @Autowired
    private TicketsService ticketsService;

    @GetMapping
    @ResponseBody
    public List<Ticket> getAllTickets() {
        return ticketsService.getAllTickets();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Ticket getTicketById(@PathVariable String id){
        Optional<Ticket> ticket = ticketsService.getTicketById(id);
        return ticket.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseBody
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.saveTicket(ticket);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Ticket updateTicket(@PathVariable String id, @RequestBody Ticket ticket) throws EntityNotFoundException {
        return ticketsService.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTicket(@PathVariable String id) throws EntityNotFoundException {
        ticketsService.deleteTicket(id);
    }

}
