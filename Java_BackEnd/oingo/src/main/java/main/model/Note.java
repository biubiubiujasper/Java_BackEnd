package main.model;

public class Note {

    private int nid;

    private String content;

    private int uid;

    private int fid;

    private double longitude;

    private double latitude;

    private String range;

    private String time;

    public Note(int nid,String content,int uid,int fid,double longitude,double latitude,String range,String time){
        this.nid = nid;
        this.content = content;
        this.uid = uid;
        this.fid = fid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.range = range;
        this.time = time;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
