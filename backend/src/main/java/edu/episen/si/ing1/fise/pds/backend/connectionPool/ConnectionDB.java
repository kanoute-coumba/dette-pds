package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.sql.*;

public class ConnectionDB {

        //attribute
        private static final String dataSmartCityEnVar = "SERVER_CONFIG";
        private Config config=null;
        public Connection connection;

        //the builder
        public ConnectionDB()
        {
                try {
                        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                        config=mapper.readValue(new File(System.getenv(dataSmartCityEnVar)), Config.class);
                        mapper = new ObjectMapper();
                        Class.forName(config.getDriver());
                        this.connection = DriverManager.getConnection(config.getURL(),
                                config.getUsername(),config.getPassword());
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}