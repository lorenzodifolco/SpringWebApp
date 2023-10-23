package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(value="/tickets")
    public Iterable<Ticket> getTickets() {
        return ticketService.getTickets();
    }

    @PostMapping(value="/tickets")
    public ResponseEntity<Ticket> post(@RequestBody Ticket ticket){
        return new ResponseEntity<>(ticketService.add(ticket), HttpStatus.CREATED);
    }

    @GetMapping(value="/tickets/{id}")
    public ResponseEntity<Optional<Ticket>> getTicket(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.getTicket(id);
        return ticket.isPresent() ? new ResponseEntity<>(ticket, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value="/tickets/{id}")
    public ResponseEntity<Ticket> put(@RequestBody Ticket ticket,@PathVariable int id) {
        Ticket t = ticketService.putTicket(ticket, id);
        return t != null ? new ResponseEntity<>(t, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value="/tickets/{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable int id) {
        return ticketService.delete(id) ? new ResponseEntity<>(Map.of("success", true),HttpStatus.OK) : new ResponseEntity<>(Map.of("success", false),HttpStatus.NOT_FOUND);
    }
}
