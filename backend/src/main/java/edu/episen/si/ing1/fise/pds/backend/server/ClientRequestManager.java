package edu.episen.si.ing1.fise.pds.backend.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientRequestManager implements Runnable {


    private final static Logger logger = LoggerFactory.getLogger(ClientRequestManager.class.getName());

    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private final static String name = "client-thread-manager";
    private Thread self;


    public ClientRequestManager(final Socket socket) throws IOException {

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        self = new Thread(this, name);
        self.start();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        byte[] inputData;
        try {
            inputData = new byte[inputStream.available()];
            inputStream.read(inputData);
            logger.debug("data length is {} and data content={}", inputData.length, new String(inputData));

            final ObjectMapper mapper = new ObjectMapper();
            final Map<String, String> result = new HashMap<>();
            result.put("Result", "OK");
            outputStream.write(mapper.writeValueAsBytes(result));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}





