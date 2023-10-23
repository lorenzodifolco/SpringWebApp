package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.State;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository repo;

    public List<Ticket> getTickets() {
        return repo.findAll();
    }


    public Ticket add(Ticket ticket) {
        ticket.setDate(new Timestamp(System.currentTimeMillis()));
        ticket.setState(State.Open);
        return repo.save(ticket);
    }

    public Optional<Ticket> getTicket(int id) {
        return repo.findById(id);
    }

    public Ticket putTicket(Ticket ticket, int id) {
        Ticket t = repo.findById(id).orElse(null);
        if (t == null)
            return null;
        t.setType(ticket.getType());
        t.setState(ticket.getState());
        t.setDescription(ticket.getDescription());
        t.setTitle(ticket.getTitle());
        t.setAttachment(ticket.getAttachment());
        t.setAssignee(ticket.getAssignee());
        t.setEstimate(ticket.getEstimate());
        t.setDue_date(ticket.getDue_date());
        t.setTime_spent(ticket.getTime_spent());
        return repo.save(t);
    }

    public boolean delete(int id) {
        if (!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }

    public List<Ticket> search(String q) {
        return repo.findByTitleContainingOrDescriptionContainingOrAuthorUsernameContaining(q, q, q);
    }

    public List<Ticket> getDone() {
        return repo.findByState(State.Done);
    }

    public List<Ticket> getOpen() {
        return repo.findByState(State.Open);
    }

    public List<Ticket> getInProgress() {
        return repo.findByState(State.In_Progress);
    }

    public List<Ticket> getClosed() {
        return repo.findByState(State.Closed);
    }


    public void setTimeSpent(double time_spent, int id) {
        Ticket t = repo.findById(id).orElse(null);
        if (t == null)
            return;
        t.setTime_spent(time_spent);
        repo.save(t);
    }
}
