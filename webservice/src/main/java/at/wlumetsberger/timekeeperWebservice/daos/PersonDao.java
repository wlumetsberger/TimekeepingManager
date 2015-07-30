package at.wlumetsberger.timekeeperWebservice.daos;

import at.wlumetsberger.timekeeperWebservice.models.Person;
import at.wlumetsberger.timekeeperWebservice.models.Race;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Transactional
public interface PersonDao extends CrudRepository<Person,Long> {

    public Person findByTagIdAndRace(String tagId, Race r);

}

