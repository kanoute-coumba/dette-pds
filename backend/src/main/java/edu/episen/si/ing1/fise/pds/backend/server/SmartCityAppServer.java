package edu.episen.si.ing1.fise.pds.backend.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.episen.si.ing1.fise.pds.backend.connectionPool.DataSource;
import edu.episen.si.ing1.fise.pds.backend.connectionPool.Request;
import edu.episen.si.ing1.fise.pds.backend.connectionPool.ServerToClient;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SmartCityAppServer extends Thread {

    private final static Logger logger = LoggerFactory.getLogger(SmartCityAppServer.class.getName());

    public static DataSource ds = new DataSource(5, 5);
    static boolean inTestMode = false;
    static int maxConnectionValue = 12;
    static int connectionTimeOutValue = 10;

    //ServerSocket server;
    Socket client;
    static ServerSocket myServerSocket;
    static boolean ServerOn = true;

    public SmartCityAppServer(Socket clientSocket) {

        client = clientSocket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        System.out.println(
                "Accepted Client Address - " + client.getInetAddress().getHostName());
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

            while (ds.getUsedConnection() < maxConnectionValue) {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String operation = in.readLine();
                ObjectMapper mapper = new ObjectMapper();
                logger.info(operation);
                Request request = mapper.readValue(operation, Request.class);
                ServerToClient connection = new ServerToClient(ds);
                String response = connection.SendResponse(request);
                out = new PrintWriter(client.getOutputStream(), true);
                out.println(response);
                System.out.print("*******\n ");
            }
        } catch (Exception e) {
            logger.info("Serveur est en attente de sa prochaine requete");
        } finally {
            try {
                in.close();
                out.close();
                client.close();
                System.out.println("...Stopped");
            } catch (IOException ioe) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        //connection pool configuration

        final Options options = new Options();
        final Option testMode = Option.builder().longOpt("testMode").build();
        final Option maxConnection = Option.builder().longOpt("maxConnection").
                hasArg().argName("maxConnection").build();
        final Option connectionTimeOut = Option.builder().longOpt("connectionTimeOut").
                hasArg().argName("connectionTimeOut").build();

        options.addOption(testMode);
        options.addOption(maxConnection);
        options.addOption(connectionTimeOut);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption("testMode")) {
            inTestMode = true;
        }
        if (commandLine.hasOption("maxConnection")) {
            maxConnectionValue = Integer.parseInt(commandLine.getOptionValue("maxConnection"));
        }
        if (commandLine.hasOption("connectionTimeOut")) {
            connectionTimeOutValue = Integer.parseInt(commandLine.getOptionValue("connectionTimeOut"));
        }

        logger.info("Backend Service is Running...(testMode={}, maxConnection={}, connectionTimeOut={})...", inTestMode, maxConnectionValue, connectionTimeOutValue);

        //connection pool created
        ds = new DataSource(maxConnectionValue, connectionTimeOutValue);

        try {
            myServerSocket = new ServerSocket(1111);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port 1111. Quitting.");
            System.exit(-1);
        }

        while (ServerOn) {
            try {
                Socket clientSocket = myServerSocket.accept();
                SmartCityAppServer cliThread = new SmartCityAppServer(clientSocket);
                cliThread.start();
                System.out.println("Serveur est en ecoute");
            } catch (IOException ioe) {
                System.out.println("Exception found on accept. Ignoring. Stack Trace :");

            }
        }
        try {
            myServerSocket.close();
            System.out.println("Server Stopped");
        } catch (Exception ioe) {
            System.out.println("Error Found stopping server socket");
            System.exit(-1);
        }
    }
}


