package edu.episen.si.ing1.fise.pds.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConfigCore {

    private int listenPort;
    private int soTimeout;


    private String ip;
    public ClientConfigCore() {

    }
    public String getIp() {
        return ip;
    }

    public int getListenPort() {
        return listenPort;
    }

    public int getSoTimeout() {
        return soTimeout;
    }


    @Override
    public String toString() {
        return "clientCoreConfig [listenPort=" + listenPort + ", soTimeout=" + soTimeout + "]";
    }


}