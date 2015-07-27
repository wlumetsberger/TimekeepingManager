package at.wlumetsberger.timekeeperWebservice.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Entity
@Table(name="person")
public class Person implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Race race;

    @OneToOne
    @JoinColumn(name="tag_id")
    private Tag tag;

    @OneToMany
    private List<Lap> laps;


    private String name;
    private String club;
    private int number;
    private int starterGroup;

    public Person(){
    }

    public Person(Race race, Tag tag, String name, String club, int number, int starterGroup) {
        this.race = race;
        this.tag = tag;
        this.name = name;
        this.club = club;
        this.number = number;
        this.starterGroup = starterGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStarterGroup() {
        return starterGroup;
    }

    public void setStarterGroup(int starterGroup) {
        this.starterGroup = starterGroup;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public void setLaps(List<Lap> laps) {
        this.laps = laps;
    }
}
