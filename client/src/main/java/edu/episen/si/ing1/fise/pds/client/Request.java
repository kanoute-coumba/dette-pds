package edu.episen.si.ing1.fise.pds.client;

public class Request {
    private String nameRequest;
    private Object data;
    public String nameRequest() {
        return nameRequest;
    }
    public void setNameRequest(String nameRequest) {
        this.nameRequest = nameRequest;
    }
    public Object getData() {
        return data;
    }
    public Request() {

    }

    public void setData(Object data) {
        this.data = data;
    }
}
