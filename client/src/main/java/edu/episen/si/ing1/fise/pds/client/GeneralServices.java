package edu.episen.si.ing1.fise.pds.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeneralServices {

    private final static Logger logger = LoggerFactory.getLogger(GeneralServices.class.getName());
    //attributs
    private int id_generalservices;
    private String company_name;

    //constructor
    public GeneralServices(int id_generalservices, String company_name) {
        super();
        this.id_generalservices = id_generalservices;
        this.company_name = company_name;
    }

    public GeneralServices() {

    }

    //getters and setters
    public int getId_generalservices() {
        return id_generalservices;
    }
    public void setId_generalservices(int id_generalservices) {
        this.id_generalservices = id_generalservices;
    }
    public String getCompany_name() {
        return company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public static ArrayList<Map> All_GeneralServices(ClientToServer connection)
    {
        ArrayList<Map> general_services=new ArrayList<Map>();
        try
        {
            //if(connection.client.isClosed())
               // connection = new ClientToServer();
            Request request=new Request();
            request.setNameRequest("all_generalServices");
            HashMap<String,Object>param=new HashMap<String,Object>();
            request.setData(param);
            System.out.println("ON est llllllllllllllaaaaaaaaaaaaaa");
            Request response=connection.SendRequest(request);
            general_services=(ArrayList<Map>)response.getData();
        }catch(Exception e)
        {
            logger.info("Server is maybe occupied");
        }
        return general_services;
    }
}
