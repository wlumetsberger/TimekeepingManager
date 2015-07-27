package at.wlumetsberger.timekeeperWebservice.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Entity
@Table(name="lap")
public class Lap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Person person;

    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
    private Timestamp timestamp;

    public Lap(){

    }
    public Lap(Person person) {
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "id=" + id +
                ", person=" + person +
                ", timestamp=" + timestamp +
                '}';
    }
}
