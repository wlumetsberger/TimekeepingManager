package at.wlumetsberger.timekeeperWebservice.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Wolfgang on 27.07.2015.
 */

@Entity
@Table(name="tag")
public class Tag implements Serializable{
    @Id
    private String tagId;

    private String description;

    @OneToOne
    private Person person;

    public Tag(){

    }
    public Tag(String tagId){
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId='" + tagId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}


