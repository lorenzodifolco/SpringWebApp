package ch.supsi.webapp.web.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;

    private String firstname;

    private String lastname;

    private String password;

    @OneToMany()
    private List<Ticket> watchedTickets;

    @Enumerated(EnumType.STRING)
    private Role role;
}
