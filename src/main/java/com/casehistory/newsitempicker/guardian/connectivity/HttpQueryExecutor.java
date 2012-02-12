package com.casehistory.newsitempicker.guardian.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpQueryExecutor {
	
    private static final Logger logger = LoggerFactory.getLogger(HttpQueryExecutor.class);

    String execute(URL url) throws IOException {
    	System.out.println("Calling :" + url);
        logger.info("Calling guardian API: " + UrlEncoder.decode(url.toExternalForm()));
		//setProxySettings();
        String response = convertStreamToString((InputStream) url.getContent());

        if (logger.isTraceEnabled())
            logger.trace("Response from API: " + response);

        return response;
    }

	private void setProxySettings() {
		System.setProperty("http.proxyHost", "proxy.ml.com");
		System.setProperty("http.proxyPort", "8083");
	}

    private String convertStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;

        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);

        return stringBuilder.toString();
    }

}
