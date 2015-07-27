package at.wlumetsberger.timekeeperWebservice.controllers;

import at.wlumetsberger.timekeeperWebservice.daos.*;
import at.wlumetsberger.timekeeperWebservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Wolfgang on 27.07.2015.
 */
@Controller
public class LapController {

    @Autowired
    RaceDao raceDao;

    @Autowired
    LapDao lapDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    LapResultDao resultDao;

    @RequestMapping("/lap/insert")
    @ResponseBody
    public boolean insertLap(Long raceId, String tagId){
        if(raceId == null && raceId <= 0 || StringUtils.isEmpty(tagId)){
            System.out.println("wrong data: "+ raceId + "/" + tagId);
            return false;
        }
        Race r = raceDao.findOne(raceId);
        if(r == null ){
            System.out.println("race not found: "+ raceId);
            return false;
        }
        Tag t = tagDao.findOne(tagId);
        if(t == null){
            System.out.println("tag not found: "+ tagId);
            return false;
        }
        Person p = personDao.findByTagAndRace(t,r);
        if(p == null){
            System.out.println("Person not found: Tag: "+ t + " / Race: "+ r);
            return false;
        }
        Lap l = new Lap(p);
        lapDao.save(l);
        return true;
    }
    @RequestMapping("/lap/findByPerson")
    @ResponseBody
    public Iterable<Lap> findByPerson(long personId){
        Person p = personDao.findOne(personId);
        if(p == null){
            System.out.println("cannot find Person with id: "+ personId);
            return null;
        }
        return lapDao.findByPerson(p);
    }
    @RequestMapping("/lap/findAll")
    @ResponseBody
    public Iterable<Lap> findAll(){
       try {
           System.out.println("dao find all");
           Iterable<Lap> retVal = lapDao.findAll();
           System.out.println("retVal: "+ retVal);
           return retVal;
       }catch(Exception e){
           e.printStackTrace();
       }
        return null;
    }

    @RequestMapping("/lap/findResults")
    @ResponseBody
    public  List<LapResult> findResult(long raceId){
        try{
            return resultDao.getGeneralResult(raceId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/lap/findResultByGroup")
    @ResponseBody
    public List<LapResult> findWinner(long raceId, int starterGroup){
        try{
          return resultDao.getResultByGroup(raceId,starterGroup);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
