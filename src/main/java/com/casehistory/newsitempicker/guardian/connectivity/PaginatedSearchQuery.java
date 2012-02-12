package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class PaginatedSearchQuery<T extends PaginatedSearchQuery> extends ApiQuery<T> {

	    private static final Logger logger = LoggerFactory.getLogger(PaginatedSearchQuery.class);
	    private static final Integer maxPageSize = 50;

	    private Integer pageSize;
	    private String sectionId;
	    private int pageStartIndex = 1;

	    protected PaginatedSearchQuery(HttpClient httpClient) {
	        super(httpClient);
	    }

	    public T pageSize(Integer pageSize) {
	        if (pageSize < 1 || pageSize > maxPageSize)
	            throw new IllegalArgumentException("Page size must be between 1 - 50");

	        this.pageSize = pageSize;
	        return (T) this;
	    }

	    public T section(String sectionId) {
	        this.sectionId = sectionId;
	        return (T) this;
	    }

	    public T startPage(int page) {
	        pageStartIndex = page;
	        return (T) this;
	    }

	    public int getStartPage() {
	        return pageStartIndex;
	    }

	    public Integer getPageSize() {
	        return pageSize;
	    }

	    public T cloneForNextPageQuery(int page) {
	        T clone;

	        try {
	            clone = (T) this.clone();
	            clone.startPage(page);
	        }
	        catch (CloneNotSupportedException e) {
	            logger.error("Could not clone", e);
	            throw new RuntimeException(e);
	        }

	        return clone;
	    }

	    @Override
	    protected void fillInApiParameters(Map<ApiParameter, Object> parameters) {
	        addHttpParameter(parameters, ApiParameter.pageSize, getPageSize());
	        addHttpParameter(parameters, ApiParameter.section, getSection());
	        addHttpParameter(parameters, ApiParameter.page, getStartPage());
	    }

	    String getSection() {
	        return sectionId;
	    }


}
