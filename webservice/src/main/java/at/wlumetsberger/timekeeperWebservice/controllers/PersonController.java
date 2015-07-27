package at.wlumetsberger.timekeeperWebservice.controllers;

import at.wlumetsberger.timekeeperWebservice.daos.PersonDao;
import at.wlumetsberger.timekeeperWebservice.daos.RaceDao;
import at.wlumetsberger.timekeeperWebservice.daos.TagDao;
import at.wlumetsberger.timekeeperWebservice.models.Person;
import at.wlumetsberger.timekeeperWebservice.models.Race;
import at.wlumetsberger.timekeeperWebservice.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Controller
public class PersonController {

    @Autowired
    PersonDao personDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    RaceDao raceDao;

    @RequestMapping("/person/insert")
    @ResponseBody
    public Person create(String name, String club, String tagId, Long raceId, int number, int starterGroup) {

        Race r = raceDao.findOne(raceId);
        if (r == null) {
            System.out.println("cannot find race for id: " + raceId);
            return null;
        }
        Tag t = tagDao.findOne(tagId);
        if (t == null) {
            System.out.println("cannot find tag for id: " + raceId);
            return null;
        }
        Person p = null;
        try {
            p = new Person(r, t, name, club, number,starterGroup);
            personDao.save(p);
        } catch (Exception e) {
            System.out.println("cannot create Person");
            e.printStackTrace();
            return null;
        }
        return personDao.findOne(p.getId());
    }
    @RequestMapping("/person/findAll")
    @ResponseBody
    public Iterable<Person> findAll(){
        return personDao.findAll();
    }

}
