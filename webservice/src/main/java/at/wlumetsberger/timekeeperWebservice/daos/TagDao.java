package at.wlumetsberger.timekeeperWebservice.daos;

import at.wlumetsberger.timekeeperWebservice.models.Tag;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Transactional
public interface TagDao extends CrudRepository<Tag,String> {
}
