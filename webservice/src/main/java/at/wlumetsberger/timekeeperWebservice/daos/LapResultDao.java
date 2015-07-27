package at.wlumetsberger.timekeeperWebservice.daos;

import at.wlumetsberger.timekeeperWebservice.models.Lap;
import at.wlumetsberger.timekeeperWebservice.models.LapResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
public interface LapResultDao extends CrudRepository<LapResult,Long> {
    @Query(value = "select count(lap.timestamp) as loop_count, " +
            " time_to_sec(max(lap.timestamp)) - time_to_sec(min(lap.timestamp)) as elapsed_time," +
            " lap.person as person_id " +
            " from lap, person " +
            " where lap.person = person.id " +
            " and person.race =  :raceId " +
            " group by lap.person order by count(lap.timestamp) desc ,time_to_sec(max(lap.timestamp)) - time_to_sec(min(lap.timestamp)) asc", nativeQuery = true)
    List<LapResult> getGeneralResult(@Param("raceId") Long raceId);

    @Query(value = "select count(lap.timestamp) as loop_count, " +
            " time_to_sec(max(lap.timestamp)) - time_to_sec(min(lap.timestamp)) as elapsed_time," +
            " lap.person as person_id " +
            " from lap, person " +
            " where lap.person = person.id " +
            " and person.race =  :raceId " +
            " and person.starter_group = :groupId "+
            " group by lap.person order by count(lap.timestamp) desc ,time_to_sec(max(lap.timestamp)) - time_to_sec(min(lap.timestamp)) asc", nativeQuery = true)
    List<LapResult> getResultByGroup(@Param("raceId") Long raceId, @Param("groupId") int groupId);
}

