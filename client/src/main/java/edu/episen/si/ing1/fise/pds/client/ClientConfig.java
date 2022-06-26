package edu.episen.si.ing1.fise.pds.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;


public class ClientConfig {
    private final static Logger logger = LoggerFactory.getLogger(ClientConfig.class.getName());
    private static final String episenClientConfigEnVar = "CLIENT_CONFIG";
    private final String episenClientConfigFileLocation;
    private ClientConfigCore config;


    public ClientConfigCore getConfig() {
        return config;
    }


    public ClientConfig() throws IOException {
        this.episenClientConfigFileLocation = System.getenv(episenClientConfigEnVar);
        logger.debug("Config file = {} ", episenClientConfigFileLocation);
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        config = mapper.readValue(new File(episenClientConfigFileLocation), ClientConfigCore.class);
        logger.debug("Config = {}", config.toString());

    }
}