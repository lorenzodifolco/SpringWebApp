package ch.supsi.webapp.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table()
public class Ticket {
    @Id
    @GeneratedValue
    @Column()
    private int id;

    @Column()
    private String title;

    @Column()
    private String description;

    @ManyToOne()
    private User author;

    @ManyToOne()
    private User assignee;

    @Column()
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Lob
    private byte[] attachment;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp date;

    @Column
    @CreationTimestamp
    private Timestamp due_date;

    @Column
    private double estimate;

    @Column
    private double time_spent;

    public String getDate() {
        // 2022-12-06 15:45:26.929
        String d = date.toString();
        d = d.substring(0,d.length()-7);
        d = d.replace("-","/");
        return d;
    }
}


