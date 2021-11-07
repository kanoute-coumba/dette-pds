package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.GeneralServices;
import edu.episen.si.ing1.fise.pds.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsTable {

    private final static Logger logger = LoggerFactory.getLogger(WindowsTable.class.getName());

    private int id_windows;
    private String status;
    private int temperature;
    private String light;
    private String blind;
    private String opacity;
    private int id_equipment;
    GeneralServices company=null;


    public int getId_windows() {
        return id_windows;
    }
    public void setId_windows(int id_windows) {
        this.id_windows = id_windows;
    }
    public String getsStatus() {
        return status;
    }
    public void setsStatus(String status) {
        this.status = status;
    }
    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public String getLight() {
        return light;
    }
    public void setLight(String light) {
        this.light = light;
    }
    public String getBlind() {
        return blind;
    }
    public void setBlind(String blind) {
        this.blind = blind;
    }
    public String getOpacity() {
        return opacity;
    }
    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }
    public int getId_equipment() {
        return id_equipment;
    }
    public void setId_equipment(int id_equipment) {
        this.id_equipment = id_equipment;
    }

    public WindowsTable(int id_windows, String status, int temperature, String light, String blind, String opacity, int id_equipment) {
        super();
        this.id_windows = id_windows;
        this.status = status;
        this.temperature = temperature;
        this.light = light;
        this.blind = blind;
        this.opacity = opacity;
        this.id_equipment = id_equipment;

    }

    public static ArrayList<Map> ownEquipment(ClientToServer connection, int id_gs)
    {
        ArrayList<Map>equipment=new ArrayList<Map>();
        //id_gs = company.
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("own_equipment");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_gs",id_gs);
            //param.put("type_equipment",type_equipment);
            request.setData(param);
            Request response=connection.SendRequest(request);
            equipment=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        return equipment;
    }

    public static ArrayList<Map> ownWindows(ClientToServer connection, int id_gs)
    {
        ArrayList<Map>window=new ArrayList<Map>();
        //ArrayList<Map>windows=null;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("own_windows");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_gs",id_gs);
            //param.put("type_equipment", type_equipment); //fenetre electro-chromatique
            request.setData(param);
            Request response=connection.SendRequest(request);
            window=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        return window;
    }

    public static Map getWindow(ClientToServer connection, int id_eq)
    {
        ArrayList<Map> window=new ArrayList<Map>();
        //choice = Windows.selection;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("get_window");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_equipment", id_eq);
            request.setData(param);
            Request response=connection.SendRequest(request);
            window=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server is maybe occupied");
        }
        if(!window.isEmpty())
            return window.get(0);
        else
            return null;
    }

    public static ArrayList<Map> windowsDefaultStatus(ClientToServer connection, int choice)
    {
        ArrayList<Map>defaultStatus=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("default_status");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_equipment", choice);
            request.setData(param);
            Request response=connection.SendRequest(request);
            defaultStatus=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        return defaultStatus;
    }

    public static boolean windowsUpdateForLightLevelAucun(ClientToServer connection, int choice, String level)
    {
        ArrayList<Map>none1=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("light_aucun");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("light", level);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none1=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server IS maybe occupied");
        }
        if(!none1.isEmpty())
            return (boolean) none1.get(0).get("update_done");
        else
            return false ; //(boolean)none1.get(0).get("not_done");
    }

    public static boolean windowsUpdateForLightLevelFaible(ClientToServer connection, int choice, String level)
    {
        ArrayList<Map>none2=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("light_faible");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("light", level);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none2=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none2.isEmpty())
            return (boolean) none2.get(0).get("update_done");
        else
            return false ; //(boolean)none2.get(0).get("not_done");

    }

    public static boolean windowsUpdateForLightLevelMoyen(ClientToServer connection, int choice, String level)
    {
        ArrayList<Map>none3=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("light_moyen");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("light", level);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none3=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none3.isEmpty())
            return (boolean) none3.get(0).get("update_done");
        else
            return false ; //(boolean)none3.get(0).get("not_done");

    }

    public static boolean windowsUpdateForLightLevelFort(ClientToServer connection, int choice, String level)
    {
        ArrayList<Map>none4=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("light_fort");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("light", level);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none4=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none4.isEmpty())
            return (boolean) none4.get(0).get("update_done");
        else
            return false ; //(int)none4.get(0).get("not_done");

    }

    public static boolean windowsUpdateForLightLevelAutre(ClientToServer connection, int choice, String level)
    {
        ArrayList<Map>none5=new ArrayList<Map>();
        //choice = Windows.selection;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("light_autre");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("light", level);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none5=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none5.isEmpty())
            return (boolean) none5.get(0).get("update_done");
        else
            return false ; //(int)none5.get(0).get("not_done");

    }

    public static boolean windowsUpdateForTemperatureDegreeLessThan18(ClientToServer connection, int choice, int degree)
    {
        ArrayList<Map>none6=new ArrayList<Map>();
        //Windows.selection = choice;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("less_than_18");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("temperature", degree);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none6=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none6.isEmpty())
            return (boolean) none6.get(0).get("update_done");
        else
            return false ; //(int)none6.get(0).get("not_done");

    }

    public static boolean windowsUpdateForTemperatureDegree18_22(ClientToServer connection, int choice, int degree)
    {
        ArrayList<Map>none7=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("between_18_22");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("temperature", degree);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none7=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none7.isEmpty())
            return (boolean) none7.get(0).get("update_done");
        else
            return false ; //(int)none7.get(0).get("not_done");

    }

    public static boolean windowsUpdateForTemperatureDegree22(ClientToServer connection, int choice, int degree)
    {
        ArrayList<Map>none8=new ArrayList<Map>();
        //Windows.selection = choice ;
        try
        {
            if(connection.client.isClosed())
                connection = new ClientToServer();
            Request request=new Request();
            request.setName_request("more_than_22");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_windows", choice);
            param.put("temperature", degree);
            request.setData(param);
            Request response=connection.SendRequest(request);
            none8=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        if(!none8.isEmpty())
            return (boolean) none8.get(0).get("update_done");
        else
            return false ; //(int)none8.get(0).get("not_done");

    }

    static public ArrayList<Map> allRentedWorkSpace(ClientToServer connection, int id_gs)
    {
        ArrayList<Map>myworkspaces=new ArrayList<Map>();
        try
        {
            Request request=new Request();
            request.setName_request("all_rented_workspaces");
            HashMap<String,Object>param=new HashMap<String,Object>();
            param.put("id_gs",id_gs);
            request.setData(param);
            Request response=connection.SendRequest(request);
            myworkspaces=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Server is maybe occupied");
        }
        return myworkspaces;
    }

}
