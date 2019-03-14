package main.service;

import main.config.Const;
import main.dao.*;
import main.model.*;
import main.util.CommonUtil;
import main.util.MapUtil;
import main.util.TimeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("NoteService")
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserService userService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private FestivalDao festivalDao;

    public Note getNote(int nid){
        return noteDao.listNote(nid).get(0);
    }

    public int addNote(Note note) {
        noteDao.addNote(note);
        return noteDao.listNote(-1).get(0).getNid();
    }

    public int addTag(Tag tag) {
        return tagDao.addTag(tag);
    }

    public void addPost(int nid, String[] postToNames) {
        for (String name : postToNames) {
            User user = userService.getUser(name);
            Post post = new Post(0, nid, user.getId());
            postDao.addPost(post);
        }
    }

    public void addPost(int nid, int uid) {
        Post post = new Post(0, nid, uid);
        postDao.addPost(post);
    }

    public List<Post> listPost(int uid){
        return postDao.listPost(uid);
    }

    public boolean checkFilter(Note note,double myLat,double myLng,String nowTime,int myId) throws  Exception{
        int fid = note.getFid();
        Filter filter = filterService.getFilter(fid);

        boolean timeOk = checkTimeOk(filter.getBeginTime(),filter.getEndTime(),nowTime);
        boolean specialOk = checkSpecialOk(filter.getIfSpecial(),filter.getSpecialDate(),nowTime);
        boolean repeatOk = checkRepeatOk(filter.getIfRepeat(),filter.getRepeatDate(),nowTime);
        boolean festivalOk = checkFestivalOk(filter.getIfFestival(),filter.getFesId(),nowTime);
        boolean radiusOk = checkRadiusOk(note.getLatitude(),note.getLongitude(),myLat,myLng,filter.getRadius());

        if(timeOk||specialOk||repeatOk||festivalOk){
            if(radiusOk){
                // pass filter
                if(note.getRange().equals(Const.RANGE_EVERYONE)){
                    return true;
                }else if(note.getRange().equals(Const.RANGE_NONE)){
                    return false;
                }else if(note.getRange().equals(Const.RANGE_FRIENDS)){
                    int uid = note.getUid();
                    User user = userService.getUserByUid(uid);
                    User me = userService.getUserByUid(myId);
                    if(user.getFriends().contains(me.getName())){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkRadiusOk(double fLat, double fLng, double myLat, double myLng, int radius){
        int distance = MapUtil.GetDistance(fLat,fLng,myLat,myLng);
        return distance<=radius?true:false;
    }

    private boolean checkTimeOk(String beginTime,String endTime,String nowTime) throws Exception{
        Date bt = TimeConvert.convertToDate(beginTime);
        Date et = TimeConvert.convertToDate(endTime);
        Date nt = TimeConvert.convertToDate(nowTime);
        long b = bt.getTime();
        long e = et.getTime();
        long n = nt.getTime();
        if(n<=e&&n>=b){
            return true;
        }
        return false;
    }

    private boolean checkSpecialOk(int ifSpecial,String specialDate,String nowTime){
        if(ifSpecial==1){
            // no check
            return true;
        }
        if(nowTime.contains(specialDate)){
            return true;
        }
        return false;
    }

    private boolean checkRepeatOk(int ifRepeat,String repeatDate,String nowTime) throws Exception{
        if(ifRepeat==1){
            // no check
            return true;
        }
        // TODO 重复条件，周几
        Date today = TimeConvert.convertToDate(nowTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }

        int repeat = Integer.parseInt(repeatDate);
        if(repeat == week_index){
            return true;
        }
        return true;
    }

    private boolean checkFestivalOk(int ifFestival,int fesId,String nowTime) throws Exception{
        if(ifFestival==1){
            // no check
            return true;
        }
        Festival festival = festivalDao.listFestival(fesId).get(0);
        String fesDay = festival.getDate();
        String[] temp = nowTime.split(" ");
        String month = temp[0].split("-")[1];
        String day = temp[0].split("-")[2];
        String checkDay = month+"-"+day;
        if(checkDay.equals(fesDay)){
            return true;
        }
        return false;
    }

}
