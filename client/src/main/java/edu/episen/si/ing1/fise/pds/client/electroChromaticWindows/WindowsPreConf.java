package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WindowsPreConf {
    private int idConf;
    private int openValue;
    private int reducedValue;
    private int closedValue;
    private int noIntensity;
    private int lowIntensity;
    private int mediumIntensity;
    private int highIntensity;
    private final static Logger logger = LoggerFactory.getLogger(WindowsTable.class.getName());

    public WindowsPreConf(int idConf, int openValue, int reducedValue, int closedValue, int noIntensity, int lowIntensity, int mediumIntensity, int highIntensity) {
        this.idConf = idConf;
        this.openValue = openValue;
        this.reducedValue = reducedValue;
        this.closedValue = closedValue;
        this.noIntensity = noIntensity;
        this.lowIntensity = lowIntensity;
        this.mediumIntensity = mediumIntensity;
        this.highIntensity = highIntensity;
    }

    public int getIdConf() {
        return idConf;
    }

    public void setIdConf(int idConf) {
        this.idConf = idConf;
    }

    public int getOpenValue() {
        return openValue;
    }

    public void setOpenValue(int openValue) {
        this.openValue = openValue;
    }

    public int getReducedValue() {
        return reducedValue;
    }

    public void setReducedValue(int reducedValue) {
        this.reducedValue = reducedValue;
    }

    public int getClosedValue() {
        return closedValue;
    }

    public void setClosedValue(int closedValue) {
        this.closedValue = closedValue;
    }

    public int getNoIntensity() {
        return noIntensity;
    }

    public void setNoIntensity(int noIntensity) {
        this.noIntensity = noIntensity;
    }

    public int getLowIntensity() {
        return lowIntensity;
    }

    public void setLowIntensity(int lowIntensity) {
        this.lowIntensity = lowIntensity;
    }

    public int getMediumIntensity() {
        return mediumIntensity;
    }

    public void setMediumIntensity(int mediumIntensity) {
        this.mediumIntensity = mediumIntensity;
    }

    public int getHighIntensity() {
        return highIntensity;
    }

    public void setHighIntensity(int highIntensity) {
        this.highIntensity = highIntensity;
    }

    @Override
    public String toString() {
        return "WindowsPreConf{" +
                "idConf=" + idConf +
                ", openValue=" + openValue +
                ", reducedValue=" + reducedValue +
                ", closedValue=" + closedValue +
                ", noIntensity=" + noIntensity +
                ", lowIntensity=" + lowIntensity +
                ", mediumIntensity=" + mediumIntensity +
                ", highIntensity=" + highIntensity +
                '}';
    }

    public static Map getValues(ClientToServer connection, int idConf)
    {
        ArrayList<Map> value =new ArrayList<Map>();
      try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("get_values");
            HashMap<String,Object> param=new HashMap<String,Object>();
            param.put("idConf", idConf);
            request.setData(param);
            Request response=connection.SendRequest(request);
            value=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }if(value.isEmpty())
        return null;
    else
        return value.get(0);
    }
}
