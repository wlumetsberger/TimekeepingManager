package at.wlumetsberger.viewManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@XmlRootElement
public class Race implements Serializable {


    private long id;


    private String name;


    private String ort;


    private Date date;


    private List<Person> persons;

    private boolean running;

    public Race(){
        super();
    }

    public Race(String name, String ort, Date date){
        this.date = date;
        this.name = name;
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "at.wlumetsberger.viewManager.Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ort='" + ort + '\'' +
                ", date=" + date +
                ", persons=" + persons +
                ", running=" + running +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}

