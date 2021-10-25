package edu.episen.si.ing1.fise.pds.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class Client extends Thread {


    public void run()
    {
        try
        {
            String[] operation=new String[] {"create","delete","read","update"};
            ClientConfig config=new ClientConfig();
            Socket client_test=new Socket(config.getConfig().getIp(),config.getConfig().getListenPort());
            String msg="";
            PrintWriter out;
            BufferedReader in;

            while(msg!="Server is occupied!") {
                out = new PrintWriter(client_test.getOutputStream(), true);
                String operation_name = operation[new Random().nextInt(4)];
                out.println(operation_name);
                in = new BufferedReader(new InputStreamReader(client_test.getInputStream()));
                msg = in.readLine();
                if(msg==null) {
                try
                {
                    out.close();
                    in.close();
                    client_test.close();
                    break;
                }
                catch (SocketException err) { logger.info(" Socket closed : No connection available");
                    }
                }
                //else if (msg != "Server is occupied!") {
                    System.out.println("New client's connection  wants an/a " + operation_name + "'s operation\n");
                    System.out.println("Server's response\n\n" + msg + "\n");
                    System.out.println("*************************************************\n\n");
                //}
            }

        }catch (Exception ex)
        {
            System.out.println(" No connection available ");
            //logger.error("error from client's side ");
        }
    }
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());
    public static void main(String[] args) throws Exception {

        new Client().start();


    }

}
