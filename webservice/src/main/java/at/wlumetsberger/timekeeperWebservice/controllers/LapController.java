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
    LapResultDao resultDao;

    @RequestMapping("/lap/insert")
    @ResponseBody
    public boolean insertLap(Long raceId, String tagId){
        if(raceId == null && raceId <= 0 || StringUtils.isEmpty(tagId)){
            System.out.println("wrong data: "+ raceId + "/" + tagId);
            return false;
        }
        Race r = raceDao.findOne(raceId);
        if(r == null ) {
            System.out.println("race not found: " + raceId);
            return false;
        }
        Person p = personDao.findByTagIdAndRace(tagId,r);
        if(p == null){
            System.out.println("Person not found: Tag: "+ tagId + " / Race: "+ r);
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
    @RequestMapping("/lap/getLapCount")
    @ResponseBody
    public int getLapCount(long personId){
        Person p = personDao.findOne(personId);
        if(p == null){
            System.out.println("cannot find Person with id: "+ personId);
            return 0;
        }
        return lapDao.findByPerson(p).size();
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
          return resultDao.getResultByGroup(raceId, starterGroup);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("lap/getPositionByGroup")
    @ResponseBody
    public int getPositionByGroup(long personId){
        Person p = personDao.findOne(personId);
        List<LapResult> res = resultDao.getResultByGroup(p.getRace().getId(), p.getStarterGroup());
        int i = 1;
        for(LapResult r : res){
            if(r.getPersonId()==(p.getId())){
                return i;
            }
            i++;
        }
        return 0;
    }

    @RequestMapping("lap/getPosition")
    @ResponseBody
    public int getPosition(long personId){
        Person p = personDao.findOne(personId);
        List<LapResult> res = resultDao.getGeneralResult(p.getRace().getId());
        int i = 1;
        for(LapResult r : res){
            if(r.getPersonId()==(p.getId())){
                return i;
            }
            i++;
        }
        return 0;
    }
}
