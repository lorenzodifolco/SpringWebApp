package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.TicketService;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tickets", ticketService.getTickets());
        return "index";
    }

    @GetMapping("/ticket/{id}")
    public String showTicket(@PathVariable("id") int id, Model model) {
        if (ticketService.getTicket(id).isEmpty())
            return "index";
        model.addAttribute("ticket", ticketService.getTicket(id).get());
        return "ticket";
    }

    @GetMapping("/ticket/new")
    public String newTicket(Model model) {
        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);
        model.addAttribute("users", userService.getUsers());
        return "newTicket";
    }

    @PostMapping("/ticket/new")
    public String saveNewTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session) {
        SecurityContextImpl sc = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) sc.getAuthentication().getPrincipal();
        ticket.setAuthor(userService.findUserByUsername(user.getUsername()));
        ticketService.add(ticket);
        return "redirect:/";
    }

    @GetMapping("/ticket/{id}/edit")
    public String editTicket(@PathVariable("id") int id, Model model) {
        if (ticketService.getTicket(id).isEmpty())
            return "newTicket";
        model.addAttribute("ticket", ticketService.getTicket(id).get());
        model.addAttribute("users", userService.getUsers());
        return "editTicket";
    }

    @PostMapping("/ticket/{id}/edit")
    public String saveEditTicket(@PathVariable("id") int id, @ModelAttribute("ticket") Ticket ticket) {
        ticketService.putTicket(ticket,id);
        return "redirect:/";
    }

    @GetMapping("/ticket/{id}/setTimeSpent")
    public String setTimeSpent(@PathVariable("id") int id, Model model) {
        if (ticketService.getTicket(id).isEmpty())
            return "newTicket";
        model.addAttribute("ticket", ticketService.getTicket(id).get());
        return "setTimespent";
    }

    @PostMapping("/ticket/{id}/setTimeSpent")
    public String saveTimeSpent(@PathVariable("id") int id, @ModelAttribute("ticket") Ticket ticket) {
        ticketService.setTimeSpent(ticket.getTime_spent(),id);
        return "redirect:/";
    }

    @GetMapping("/watch")
    public String watch(Model model, HttpSession session) {
        SecurityContextImpl sc = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) sc.getAuthentication().getPrincipal();
        model.addAttribute("tickets", userService.getWatchedTickets(userService.findUserByUsername(user.getUsername())));
        return "watchList";
    }

    @PostMapping("/ticket/{id}/watch")
    public String addWatchTicket(@PathVariable("id") int id, @ModelAttribute("ticket") Ticket ticket, HttpSession session) {
        SecurityContextImpl sc = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) sc.getAuthentication().getPrincipal();
        userService.addWatch(ticketService.getTicket(id).get(), userService.findUserByUsername(user.getUsername()));
        return "redirect:/";
    }

    @GetMapping("/ticket/{id}/delete")
    public String deleteThroughId(@PathVariable("id") int id) {
        ticketService.delete(id);
        return "redirect:/";
    }



    @GetMapping("/board")
    public String board(Model model) {
        model.addAttribute("tickets", ticketService.getTickets());
        model.addAttribute("done", ticketService.getDone());
        model.addAttribute("inProgress", ticketService.getInProgress());
        model.addAttribute("open", ticketService.getOpen());
        model.addAttribute("closed", ticketService.getClosed());

        return "board";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/ticket/search")
    public @ResponseBody List<Ticket> searchTicket(String q){
        return ticketService.search(q);
    }
}
