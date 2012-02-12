package com.casehistory.newsitempicker.guardian.connectivity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casehistory.newsitempicker.guardian.feed.GuardianArticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class  ApiQuery<T extends ApiQuery> implements Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(ApiQuery.class);
    private static final String formatString = "json";

    protected final HttpClient httpClient;
    private String apiKey;
    private String queryString;

    protected ApiQuery(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public T apiKey(String apiKey) {
        this.apiKey = apiKey;
        return (T) this;
    }

    public T query(String queryString) {
        this.queryString = queryString;
        return (T) this;
    }

    public T zeroTermQuery() {
        this.queryString = null;
        return (T) this;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
    
    protected void addHttpParameter(Map<ApiParameter, Object> parameters, ApiParameter param, Set<? extends ApiUrlParameter> value) {
        if (value != null && !value.isEmpty())
            parameters.put(param, value);
    }

    protected void addHttpParameter(Map<ApiParameter, Object> parameters, ApiParameter param, Object value) {
        if (value != null)
            parameters.put(param, value);
    }
    


    protected Map<ApiParameter, Object> generateParameterMap() {
        Map<ApiParameter, Object> parameters = new HashMap<ApiParameter, Object>();

        addHttpParameter(parameters, ApiParameter.apiKey, getApiKey());
        addHttpParameter(parameters, ApiParameter.format, getFormatString());
        addHttpParameter(parameters, ApiParameter.q, getQueryString());

        fillInApiParameters(parameters);

        return parameters;
    }

    protected abstract void fillInApiParameters(Map<ApiParameter, Object> parameters);
    public abstract List<GuardianArticle> execute(); 

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    String getApiKey() {
        return apiKey;
    }

    String getQueryString() {
        return queryString;
    }

    String getFormatString() {
        return formatString;
    }
}
