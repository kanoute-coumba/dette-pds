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
    private int anyTinted;
    private int weakTinted;
    private int halfTinted;
    private int fullTinted;
    private final static Logger logger = LoggerFactory.getLogger(WindowsTable.class.getName());

    public WindowsPreConf(int idConf, int openValue, int reducedValue, int closedValue, int anyTinted, int weakTinted, int halfTinted, int fullTinted) {
        this.idConf = idConf;
        this.openValue = openValue;
        this.reducedValue = reducedValue;
        this.closedValue = closedValue;
        this.anyTinted = anyTinted;
        this.weakTinted = weakTinted;
        this.halfTinted = halfTinted;
        this.fullTinted = fullTinted;
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

    public int getAnyTinted() {
        return anyTinted;
    }

    public void setAnyTinted(int anyTinted) {
        this.anyTinted = anyTinted;
    }

    public int getWeakTinted() {
        return weakTinted;
    }

    public void setWeakTinted(int weakTinted) {
        this.weakTinted = weakTinted;
    }

    public int getHalfTinted() {
        return halfTinted;
    }

    public void setHalfTinted(int halfTinted) {
        this.halfTinted = halfTinted;
    }

    public int getFullTinted() {
        return fullTinted;
    }

    public void setFullTinted(int fullTinted) {
        this.fullTinted = fullTinted;
    }

    @Override
    public String toString() {
        return "WindowsPreConf{" +
                "idConf=" + idConf +
                ", openValue=" + openValue +
                ", reducedValue=" + reducedValue +
                ", closedValue=" + closedValue +
                ", anyTinted=" + anyTinted +
                ", weakTinted=" + weakTinted +
                ", halfTinted=" + halfTinted +
                ", fullTinted=" + fullTinted +
                '}';
    }

    public static ArrayList<Map> windowsPreconf(ClientToServer connection, int idConf)
    {
        ArrayList<Map> preConf =new ArrayList<Map>();
      try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("default_conf");
            HashMap<String,Object> param=new HashMap<String,Object>();
            param.put("idConf", idConf);
            request.setData(param);
            Request response=connection.SendRequest(request);
            preConf=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        return preConf;
    }
}
