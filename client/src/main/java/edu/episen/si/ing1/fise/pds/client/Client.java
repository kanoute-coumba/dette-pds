package edu.episen.si.ing1.fise.pds.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(Client.class.getName());

    public void run()
    {
    }
     public static void main(String[] args) throws Exception {

        new Client().start();


    }

}
