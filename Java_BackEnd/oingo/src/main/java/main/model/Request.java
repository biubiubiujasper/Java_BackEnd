package main.model;

public class Request {

    private int askId;

    private int answerId;

    private String requestTime;

    private int status;

    public Request(int askId,int answerId,String requestTime,int status){
        this.askId = askId;
        this.answerId = answerId;
        this.requestTime = requestTime;
        this.status = status;
    }

    public int getAskId() {
        return askId;
    }

    public void setAskId(int askId) {
        this.askId = askId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
