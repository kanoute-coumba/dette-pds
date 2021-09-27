package edu.episen.si.ing1.fise.pds.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Client extends Thread {


    public void run()
    {
        try
        {
            String[] operation=new String[] {"add","update","delete","read"};
            ClientConfig config=new ClientConfig();
            Socket client_test=new Socket(config.getConfig().getIp(),config.getConfig().getListenPort());
            String msg="";
            PrintWriter out;
            BufferedReader in;
            int i=0;
            while(msg!="Server is occupied!" && msg!=null) {
                out = new PrintWriter(client_test.getOutputStream(), true);
                String operation_name = operation[new Random().nextInt(4)];
                out.println(operation_name);
                in = new BufferedReader(new InputStreamReader(client_test.getInputStream()));
                msg = in.readLine();
                if(msg==null)
                {
                    out.close();
                    in.close();
                    client_test.close();
                    break;
                }
                else if (msg != "Server is occupied!") {
                    System.out.println("client  number " + (++i) + " wants an /a " + operation_name + "'s operation\n");
                    System.out.println("Server's response\n\n" + msg + "\n");
                    System.out.println("*************************************************\n\n");
                }
            }
            System.out.println("Server is now occupied!");
        }catch (Exception ex)
        {
            logger.error("error from client's side ");
            ex.printStackTrace();
        }
    }
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());
    public static void main(String[] args) throws Exception {

        new Client().start();


    }

}
