package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.State;
import ch.supsi.webapp.web.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByTitleContainingOrDescriptionContainingOrAuthorUsernameContaining(String title, String description, String username);

    List<Ticket> findByState(State state);

    List<Ticket> findByAssigneeUsername(String username);
}
