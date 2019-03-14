package main.model;

public class Tag {

    private int tid;

    private int nid;

    private String name;

    public Tag(int tid,int nid,String name){
        this.tid = tid;
        this.nid = nid;
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
