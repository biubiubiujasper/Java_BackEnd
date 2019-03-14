package main.controller;

import main.config.Const;
import main.model.*;
import main.service.FilterService;
import main.service.NoteService;
import main.service.UserService;
import main.util.CommonUtil;
import main.util.TimeConvert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("note")
public class NoteController {

    static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private FilterService filterService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "listFestival")
    public List<Festival> listFestival(HttpServletRequest request){
        return filterService.listFestival();
    }

    @GetMapping(value = "addNote")
    public Map<String,Object> addNote(HttpServletRequest request){
        Map<String,Object> result = CommonUtil.getResult();
        // filter
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String specialDate = request.getParameter("specialDate");
        String repeatDate = request.getParameter("repeatDate");
        String festivalId = request.getParameter("fesId");
        int fesId = festivalId==null?-1:Integer.parseInt(request.getParameter("fesId"));
        int ifSpecial = specialDate==null?1:0;
        int ifRepeat = repeatDate==null?1:0;
        int ifFestival = fesId==-1?1:0;
        int radius = Integer.parseInt(request.getParameter("radius"));
        Filter filter = new Filter(0,beginTime,endTime,ifSpecial,specialDate,ifRepeat,repeatDate,ifFestival,fesId,radius);
        int fid = filterService.addFilter(filter);
        // note
        String content = request.getParameter("content");
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        String range = request.getParameter("range");
        int uid = CommonUtil.loginTable.get(request.getSession().getId());
        String time = TimeConvert.getTimeNow();
        Note note = new Note(0,content,uid,fid,longitude,latitude,range,time);
        int nid = noteService.addNote(note);
        // tag
        String tagName = request.getParameter("tagName");
        Tag tag = new Tag(0,nid,tagName);
        noteService.addTag(tag);

        // post
        if(range.equals(Const.RANGE_NONE)){
            //nothing
        }else if(range.equals(Const.RANGE_FRIENDS)){
            // friends can be posted
            User user = userService.getUserByUid(uid);
            if(StringUtils.isNotBlank(user.getFriends())){
                if(user.getFriends().contains(",")){
                    String[] friends = user.getFriends().split(",");
                    noteService.addPost(nid,friends);
                }else{
                    String[] friends = {user.getFriends()};
                    noteService.addPost(nid,friends);
                }
            }
        }else if(range.equals(Const.RANGE_EVERYONE)){
            // everyone can be posted
            List<User> userList = userService.getUserList();
            for(User user:userList){
                int oneUid = user.getId();
                noteService.addPost(nid,oneUid);
            }
        }

        return result;
    }

    @GetMapping(value = "getPostNote")
    public List<Note> getPostNote(HttpServletRequest request) throws Exception{
        List<Note> notes = new ArrayList<>();

        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));

        int uid = CommonUtil.loginTable.get(request.getSession().getId());
        List<Post> posts = noteService.listPost(uid);
        for(Post post:posts){
            int nid = post.getNid();
            Note note = noteService.getNote(nid);
            if(noteService.checkFilter(note,latitude,longitude,TimeConvert.getTimeNow(),uid)){
                // pass filter
                notes.add(note);
            }
        }

        return notes;
    }
}
