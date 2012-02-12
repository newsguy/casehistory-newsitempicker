package com.casehistory.newsitempicker.guardian.connectivity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public abstract class ApiResponse<T extends ApiQuery> implements Serializable {

    protected final ApiQuery query;

    private String json;
    private Integer numberOfResults;
    private UserTier userTier;
    private ResponseStatus status;
    
    public ApiResponse(){
    	this.query = null;
    }

    protected ApiResponse(T query) {
        status = ResponseStatus.error;
        this.query = query;
    }

    @SuppressWarnings("unchecked")
    public T getQuery() {
        return (T) query;
    }

    public boolean isResponseOk() {
        return ResponseStatus.ok.equals(status);
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Integer getTotalNumberOfResults() {
        return numberOfResults;
    }

    public UserTier getUserTier() {
        return userTier;
    }

    public String getJson() {
        return json;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setUserTier(UserTier userTier) {
        this.userTier = userTier;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setTotalNumberOfResults(Integer numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public void setJson(String json) {
        this.json = json;
    }
}

