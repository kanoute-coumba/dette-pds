package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;

public class ServerToClient {

    private final static Logger logger = LoggerFactory.getLogger(ServerToClient.class.getName());
    private DataSource data_source;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();


    public String SendResponse(Request request) throws Exception {
        ConnectionDB con = data_source.takeCon();
        Connection connection = con.connection;
        String request_name = request.getName_request();
        System.out.println(request_name);
        String response_string = "";
        logger.info("++++++++++++++Send++++++++Response+++++++++++++++++");

        data_source.returnCon(con);
        return response_string;
    }

    public ServerToClient(DataSource ds) {
        try {
            data_source = ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}