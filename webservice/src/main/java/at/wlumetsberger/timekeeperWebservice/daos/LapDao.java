package at.wlumetsberger.timekeeperWebservice.daos;

import at.wlumetsberger.timekeeperWebservice.models.Lap;
import at.wlumetsberger.timekeeperWebservice.models.LapResult;
import at.wlumetsberger.timekeeperWebservice.models.Person;
import at.wlumetsberger.timekeeperWebservice.models.Race;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Transactional
public interface LapDao extends CrudRepository<Lap,Long>{

    public Iterable<Lap> findByPerson(Person p);


}
