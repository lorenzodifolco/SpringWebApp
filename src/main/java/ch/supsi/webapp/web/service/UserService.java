package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository repo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        if (repo.findAll().isEmpty()) {
            User admin = new User();
            admin.setRole(Role.ROLE_ADMIN);
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            repo.save(admin);
        }
    }

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    public User add(User user) {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);
        return repo.save(user);
    }

    public Optional<User> getUser(int id) {
        return repo.findById(id);
    }

    public User putUser(User user, int id) {
        User u = repo.findById(id).orElse(null);
        if (u == null)
            return null;
        u.setUsername(user.getUsername());
        return repo.save(u);
    }

    public boolean delete(int id) {
        if (!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }

    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    public void addWatch(Ticket ticket, User userByUsername) {
        userByUsername.getWatchedTickets().add(ticket);
        repo.save(userByUsername);
    }

    public List<Ticket> getWatchedTickets(User user) {
        return user.getWatchedTickets();
    }
}
