package main.model;

public class Festival {

    private int fesid;

    private String name;

    private String date;

    public Festival(int fesid,String name,String date){
        this.fesid = fesid;
        this.name = name;
        this.date = date;
    }

    public int getFesid() {
        return fesid;
    }

    public void setFesid(int fesid) {
        this.fesid = fesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
