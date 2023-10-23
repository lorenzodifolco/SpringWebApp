package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/users")
    public Iterable<User> getTickets() {
        return userService.getUsers();
    }

    @PostMapping(value="/users")
    public ResponseEntity<User> post(@RequestBody User user){
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<Optional<User>> getTicket(@PathVariable int id) {
        Optional<User> ticket = userService.getUser(id);
        return ticket.isPresent() ? new ResponseEntity<>(ticket, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value="/users/{id}")
    public ResponseEntity<User> put(@RequestBody User ticket,@PathVariable int id) {
        User u = userService.putUser(ticket, id);
        return u != null ? new ResponseEntity<>(u, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable int id) {
        return userService.delete(id) ? new ResponseEntity<>(Map.of("success", true),HttpStatus.OK) : new ResponseEntity<>(Map.of("success", false),HttpStatus.NOT_FOUND);
    }
}
