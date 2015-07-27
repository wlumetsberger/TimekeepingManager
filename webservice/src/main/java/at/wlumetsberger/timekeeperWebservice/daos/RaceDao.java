package at.wlumetsberger.timekeeperWebservice.daos;

/**
 * Created by Wolfgang on 27.07.2015.
 */

import at.wlumetsberger.timekeeperWebservice.models.Race;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface RaceDao extends CrudRepository<Race, Long> {
}
