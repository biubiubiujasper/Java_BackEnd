package main.model;

public class Filter {

    private int fid;

    private String beginTime;

    private String endTime;

    private int ifSpecial;

    private String specialDate;

    private int ifRepeat;

    private String repeatDate;

    private int ifFestival;

    private int fesId;

    private int radius;

    public Filter(int fid,String beginTime,String endTime,int ifSpecial,String specialDate,int ifRepeat,String repeatDate,int ifFestival,int fesId,int radius){
        this.fid = fid;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.ifSpecial = ifSpecial;
        this.specialDate = specialDate;
        this.ifRepeat = ifRepeat;
        this.repeatDate = repeatDate;
        this.ifFestival = ifFestival;
        this.fesId = fesId;
        this.radius = radius;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIfSpecial() {
        return ifSpecial;
    }

    public void setIfSpecial(int ifSpecial) {
        this.ifSpecial = ifSpecial;
    }

    public String getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(String specialDate) {
        this.specialDate = specialDate;
    }

    public int getIfRepeat() {
        return ifRepeat;
    }

    public void setIfRepeat(int ifRepeat) {
        this.ifRepeat = ifRepeat;
    }

    public String getRepeatDate() {
        return repeatDate;
    }

    public void setRepeatDate(String repeatDate) {
        this.repeatDate = repeatDate;
    }

    public int getIfFestival() {
        return ifFestival;
    }

    public void setIfFestival(int ifFestival) {
        this.ifFestival = ifFestival;
    }

    public int getFesId() {
        return fesId;
    }

    public void setFesId(int fesId) {
        this.fesId = fesId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
