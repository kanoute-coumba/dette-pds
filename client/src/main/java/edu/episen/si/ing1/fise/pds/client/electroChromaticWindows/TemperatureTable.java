package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperatureTable {

    private final static Logger logger = LoggerFactory.getLogger(TemperatureTable.class.getName());

    private int id_temperature;
    private int degree;
    private int id_windows;

    public int getId_temperature() {
        return id_temperature;
    }
    public void setId_temperature(int id_temperature) {
        this.id_temperature = id_temperature;
    }
    public int getDegree() {
        return degree;
    }
    public void setDegree(int degree) {
        this.degree = degree;
    }
    public int getId_windows() {
        return id_windows;
    }
    public void setId_windows(int id_windows) {
        this.id_windows = id_windows;
    }

    public TemperatureTable(int id_temperature, int degree, int id_windows) {
        super();
        this.id_temperature = id_temperature;
        this.degree = degree;
        this.id_windows = id_windows;
    }

    public static Map degreeFromTemperature(ClientToServer connection, int choice)
    {
        ArrayList<Map>degree=new ArrayList<Map>();
        //choice = Windows.selection;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setNameRequest("get_temperature");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            request.setData(param);
            Request response=connection.SendRequest(request);
            degree=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server is maybe occupied");
        }
        if(degree.isEmpty())
            return null;
        else
            return degree.get(0);
    }

}
