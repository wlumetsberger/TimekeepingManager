package at.wlumetsberger.viewManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@XmlRootElement
public class Person implements Serializable{


    private long id;
    private Race race;
    private String tagId;
    private List<Lap> laps;
    private String name;
    private String club;
    private int number;
    private int starterGroup;

    public Person(){
    }

    public Person(Race race, String tag, String name, String club, int number, int starterGroup) {
        this.race = race;
        this.tagId = tag;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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
