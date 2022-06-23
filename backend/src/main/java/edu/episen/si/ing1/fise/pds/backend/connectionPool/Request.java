package edu.episen.si.ing1.fise.pds.backend.connectionPool;

public class Request {
    private String nameRequest;
    private Object data;
    public String getNameRequest() {
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
