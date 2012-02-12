package com.casehistory.newsitempicker.guardian.connectivity;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
	
	private final ApiUrlFactory apiUrlFactory;
	private final HttpQueryExecutor httpQueryExecutor;

    public HttpClient(ApiUrlFactory apiUrlFactory, HttpQueryExecutor httpQueryExecutor) {
        this.apiUrlFactory = apiUrlFactory;
        this.httpQueryExecutor = httpQueryExecutor;
    }
    
    public String executeQuery(String path, ApiQuery<?> apiQuery) throws HttpException {
        String responseAsString;
        URL url = apiUrlFactory.constructApiUrl(path, apiQuery);

        try {
            responseAsString = httpQueryExecutor.execute(url);
        }
        catch (Exception e) {
            logger.error("Could not get url: " + url.toString());
            throw new HttpException(e);
        }

        return responseAsString;
    }
	
}
