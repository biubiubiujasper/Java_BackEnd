package main.model;

public class Post {

    private int pid;

    private int nid;

    private int uid;

    public Post(int pid,int nid,int uid){
        this.pid = pid;
        this.nid = nid;
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
