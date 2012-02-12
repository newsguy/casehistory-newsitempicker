package com.casehistory.newsitempicker.guardian.connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuardianApiHostProvider {
	
    private static final Logger logger = LoggerFactory.getLogger(GuardianApiHostProvider.class);

    private static final String defaultHost = "http://content.guardianapis.com";
    private static final String apiProperties = "/guardian-api.properties";
    private static final String hostPropertyKey = "apiHost";

    private static String cachedApiHost = defaultHost;

    static String getGuardianApiHost() {
        if (cachedApiHost == null)
            resolveHost();

        return cachedApiHost;
    }

    private static void resolveHost() {
        Properties properties = new Properties();

        try {
            InputStream stream = GuardianApiHostProvider.class.getResourceAsStream(apiProperties);

            if (stream != null)
                properties.load(stream);
        }
        catch (IOException e) {
            logger.info("No local config file found");
        }

        cachedApiHost = properties.getProperty(hostPropertyKey);

        if (cachedApiHost == null)
            cachedApiHost = defaultHost;

        logger.info("Using API host: " + cachedApiHost);
    }

}
