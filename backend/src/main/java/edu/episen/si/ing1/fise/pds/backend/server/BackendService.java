package edu.episen.si.ing1.fise.pds.backend.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import edu.episen.si.ing1.fise.pds.backend.connectionPool.ConnectionDB;
import edu.episen.si.ing1.fise.pds.backend.connectionPool.DataSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendService extends Thread {


    private final static Logger logger = LoggerFactory.getLogger(BackendService.class.getName());

    public static DataSource ds=new DataSource(5,5);
    ServerSocket server;
    Socket client;
    public static ServerConfig serverConfig;
    static boolean inTestMode  = false;
    static  int maxConnectionValue = 0;
    static  int connectionTimeOutValue = 0;


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
        while(ds.getUsedConnection() < maxConnectionValue*2)
        {
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String operation_name=in.readLine();
                if(operation_name!=null) {
                    //CrudOperation(operation_name);
                    out = new PrintWriter(client.getOutputStream(), true);
                    System.out.print("************************************************\n ");
                    System.out.print(" A client is asking for a/an ");
                    out.println(CrudOperation(operation_name));
                    CrudOperation(operation_name);

                    sleep(connectionTimeOutValue * 1000);
                    if (ds.getUsedConnection() > maxConnectionValue) {

                        out.println("Server is occupied!");
                    }
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                logger.info("No service available!!");
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
        System.out.println(operation_name + " operation : " );
        System.out.println();
        String result="";
        switch (operation_name) {
            case "create":
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
        ds.closure();
        return result ;
    }

    public void serve() {
        try {
            client= server.accept();
            logger.debug("a client has been detected !!");

        } catch (IOException ex) {
            logger.info("No service available!!");
        }
    }

    public static void main(String[] args) throws Exception {
        //connection pool configuration

        serverConfig = new ServerConfig();
        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        final Option maxConnection = Option.builder().longOpt("maxConnection").
                hasArg().argName("maxConnection").build();
        final Option connectionTimeOut = Option.builder().longOpt("connectionTimeOut").
                hasArg().argName("connectionTimeOut").build();
        Option id = new Option("i", "id", true, "id of the person");
        Option name = new Option("n", "name", true, "name of the person");
        Option age = new Option("a", "age", true, "age of the person");

        options.addOption(testMode);
        options.addOption(maxConnection);
        options.addOption(connectionTimeOut);
        options.addOption(id);
        options.addOption(name);
        options.addOption(age);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options,  args);

        if(commandLine.hasOption("testMode")) {
            inTestMode = true;
        }
        if(commandLine.hasOption("maxConnection")) {
            maxConnectionValue = Integer.parseInt(commandLine.getOptionValue("maxConnection"));
        }
        if(commandLine.hasOption("connectionTimeOut")) {
            connectionTimeOutValue = Integer.parseInt(commandLine.getOptionValue("connectionTimeOut"));
        }

        logger.info("Backend Service is Running...(testMode={}, maxConnection={}, connectionTimeOut={})...", inTestMode, maxConnectionValue, connectionTimeOutValue);

        //connection pool created
        ds = new DataSource(maxConnectionValue, connectionTimeOutValue);

        BackendService service=new BackendService(serverConfig);
        logger.info(" SERVER HERE ! ");
        service.start();

    }

}
