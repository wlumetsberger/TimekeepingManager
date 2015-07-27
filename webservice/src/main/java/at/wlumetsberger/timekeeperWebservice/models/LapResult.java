package at.wlumetsberger.timekeeperWebservice.models;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Entity
@Table(name="result")
public class LapResult implements Serializable {

    @Id
    private int loopCount;
    private long elapsedTime;
    private int personId;

    public LapResult(){

    }

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    @Override
    public String toString() {
        return "LapResult{" +
                "loopCount=" + loopCount +
                ", elapsedTime=" + elapsedTime +
                ", personId=" + personId +
                '}';
    }
}
