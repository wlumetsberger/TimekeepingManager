package at.wlumetsberger.timekeeperWebservice.daos;

import at.wlumetsberger.timekeeperWebservice.models.Lap;
import at.wlumetsberger.timekeeperWebservice.models.Person;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Transactional
public interface LapDao extends CrudRepository<Lap,Long>{

    public List<Lap> findByPerson(Person p);


}
