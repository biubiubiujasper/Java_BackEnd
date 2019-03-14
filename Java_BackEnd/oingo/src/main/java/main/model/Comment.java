package main.model;

public class Comment {

    private int cid;

    private int nid;

    private String time;

    private String content;

    public Comment(int cid,int nid,String time,String content){
        this.cid = cid;
        this.nid = nid;
        this.time = time;
        this.content = content;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
