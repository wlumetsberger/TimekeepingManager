package at.wlumetsberger.timekeeperWebservice.controllers;

import at.wlumetsberger.timekeeperWebservice.daos.TagDao;
import at.wlumetsberger.timekeeperWebservice.models.Tag;
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
public class TagController {

    @Autowired
    TagDao tagDao;

    @RequestMapping("/tag/insert")
    @ResponseBody
    public Tag insertLap(String tagId){
        Tag t = null;
        try {
            t = new Tag(tagId);
            tagDao.save(t);
        } catch (Exception ex) {
            Logger.getLogger(TagController.class.getName()).log(Level.SEVERE, "Error creating the race: ", ex);
        }
        return tagDao.findOne(t.getTagId());
    }
}
