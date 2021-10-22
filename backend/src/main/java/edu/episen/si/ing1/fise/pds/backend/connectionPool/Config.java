package edu.episen.si.ing1.fise.pds.backend.connectionPool;


public class Config {
    private String driver;
    private String url;
    private String username;
    private String password;

    private String create;
    private String read;
    private String update;
    private String delete;
    private int listenPort;
    private int soTimeout;

    private String ip;
    public String getIp() {
        return ip;
    }
    public int getListenPort() {
        return listenPort;
    }
    public int getSoTimeout() {
        return soTimeout;
    }

    //CRUD
    public String getCreate() {
        return create;
    }
    public String getRead() { return read; }
    public String getUpdate() {
        return update;
    }
    public String getDelete() {
        return delete;
    }

    //who have acces read only
    public String getDriver() {
        return driver;
    }
    public String getURL() {
        return url;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
