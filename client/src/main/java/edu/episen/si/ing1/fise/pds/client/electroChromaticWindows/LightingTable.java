package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightingTable {

    private final static Logger logger = LoggerFactory.getLogger(LightingTable.class.getName());

    private int id_light;
    private String level;

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    private int intensity;
    private int id_windows;

    public int getId_light() {
        return id_light;
    }

    public void setId_light(int id_light) {
        this.id_light = id_light;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId_windows() {
        return id_windows;
    }

    public void setId_windows(int id_windows) {
        this.id_windows = id_windows;
    }

    public LightingTable(int id_light, int intensity, int id_windows) {
        super();
        this.id_light = id_light;
        this.intensity = intensity;
        this.id_windows = id_windows;
    }

    public static Map levelFromLighting(ClientToServer connection, int choice)
    {
        ArrayList<Map>level=new ArrayList<Map>();
        //choice = Windows.selection;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("get_light");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            request.setData(param);
            Request response=connection.SendRequest(request);
            level=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server is maybe occupied");
        }
        if(level.isEmpty())
            return null;
        else
            return level.get(0);
    }

    public static Map getIntensity(ClientToServer connection, int choice)
    {
        ArrayList<Map> intensity =new ArrayList<Map>();
        //choice = Windows.selection;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("get_intensity");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            request.setData(param);
            Request response=connection.SendRequest(request);
            intensity =(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server is maybe occupied");
        }
        if(intensity.isEmpty())
            return null;
        else
            return intensity.get(0);
    }

}
