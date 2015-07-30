package at.wlumetsberger.timekeeperWebservice.controllers;

import at.wlumetsberger.timekeeperWebservice.daos.RaceDao;
import at.wlumetsberger.timekeeperWebservice.models.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Controller
public class RaceController {

    @Autowired
    RaceDao raceDao;

    @RequestMapping("/race/insert")
    @ResponseBody
    public Race create(String name, String ort) {
        Race race = null;
        try {
            LocalDate date = LocalDate.now();
            race = new Race(name, ort, date);
            raceDao.save(race);
        } catch (Exception ex) {
            Logger.getLogger(RaceController.class.getName()).log(Level.SEVERE, "Error creating the race: " , ex);
        }
        return raceDao.findOne(race.getId());
    }

    @RequestMapping("race/getActive")
    @ResponseBody
    public Race getActiveRace(){
        return raceDao.findByRunning(true);
    }

    @RequestMapping("race/get")
    @ResponseBody
    public Race getRaceById(Long id){
        return raceDao.findOne(id);
    }

    @RequestMapping("race/findAll")
    @ResponseBody
    public Iterable<Race> findAll(){
        return raceDao.findAll();
    }

}