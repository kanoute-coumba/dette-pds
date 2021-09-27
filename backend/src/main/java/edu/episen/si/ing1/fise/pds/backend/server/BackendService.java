package edu.episen.si.ing1.fise.pds.backend.server;

import edu.episen.si.ing1.fise.pds.backend.connectionPool.ConnectionDB;
import edu.episen.si.ing1.fise.pds.backend.connectionPool.DataSource;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BackendService extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(BackendService.class.getName());

    public static DataSource ds=new DataSource(5,5);
    ServerSocket server;
    Socket client;
    public static ServerConfig serverConfig;
    public static  int max_connection_i = 0, connection_duration_i = 0;

    public BackendService(final ServerConfig config) {
        try {
            server = new ServerSocket(config.getConfig().getListenPort());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void run()
    {
        PrintWriter out=null;
        BufferedReader in=null;
        this.serve();
        while(ds.getUsedConnection()<max_connection_i )
        {

            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String operation_name=in.readLine();
                if(operation_name!=null)
                {
                    CrudOperation(operation_name);
                    out = new PrintWriter(client.getOutputStream(),true);
                    out.println(CrudOperation(operation_name));
                    System.out.print("connexion number "+(ds.getUsedConnection()+1)+" asking for a/an ");
                    ds.setUsedConnection(ds.getUsedConnection()+1);
                    //interval between each connexion
                    sleep(connection_duration_i*1000);
                    if(ds.getUsedConnection()>=max_connection_i ) {

                        out.println("Server is occupied!");
                    }
                }
                System.out.print("***************************************************************\n ");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        try {
            in.close();
            out.close();
            server.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String CrudOperation(String operation_name) throws Exception
    {
        ConnectionDB c = ds.takeCon();
        System.out.println(operation_name + " operation :");
        String result="";
        switch (operation_name) {
            case "add":
                //Add operation
                result= c.createPerson();
                break;
            case "update":
                // Update operation
                result= c.updatePerson();
                break;
            case "delete":
                //Delete operation
                result= c.deletePerson();
                break;
            case "read":
                //Read operation
                result=c.listPerson().toString();
                break;
            default:
                result="operation non-existent!!";
                break;
        }
        c.connection.close();
        return result;
    }

    public void serve() {
        try {
            client= server.accept();
            logger.debug("a client has been detected !!");
            //    final ClientRequestManager clientRequestManager = new ClientRequestManager(client);

        } catch (Exception ex) {
            logger.info("no service available!!");
        }
    }

      public static void main (String[] args) throws ParseException {

        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        final Option maxConnection = Option.builder().longOpt("maxConnection").
                hasArg().argName("maxConnection").build();
        options.addOption(testMode);
        options.addOption(maxConnection);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options,  args);

        boolean inTestMode  = false;
        int maxConnectionValue = 10;

        if(commandLine.hasOption("testMode")) {
            inTestMode = true;
        }
        if(commandLine.hasOption("maxConnection")) {
            maxConnectionValue = Integer.parseInt(commandLine.getOptionValue("maxConnection"));
        }

        logger.info("Backend Service is Running...(testMode={}, maxConnection={})...", inTestMode, maxConnectionValue);

    }
}
