package at.wlumetsberger.viewManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@XmlRootElement
public class LapResult implements Serializable {


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
        return "at.wlumetsberger.viewManager.LapResult{" +
                "loopCount=" + loopCount +
                ", elapsedTime=" + elapsedTime +
                ", personId=" + personId +
                '}';
    }
}
