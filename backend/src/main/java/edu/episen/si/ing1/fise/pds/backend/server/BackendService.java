package edu.episen.si.ing1.fise.pds.backend.server;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendService {

    private final static Logger logger = LoggerFactory.getLogger(BackendService.class.getName());
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
