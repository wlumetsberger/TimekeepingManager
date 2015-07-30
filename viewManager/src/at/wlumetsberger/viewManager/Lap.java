package at.wlumetsberger.viewManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wolfgang on 27.07.2015.
 */

@XmlRootElement
public class Lap implements Serializable {


    private long id;

    private Person person;

    private Date timestamp;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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
        return "at.wlumetsberger.viewManager.Lap{" +
                "id=" + id +
                ", person=" + person +
                ", timestamp=" + timestamp +
                '}';
    }
}
