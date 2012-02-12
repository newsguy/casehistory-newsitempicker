package com.casehistory.newsitempicker.guardian.connectivity;

public abstract class PaginatedSearchResult<T extends PaginatedSearchQuery> extends ApiResponse<T> {

    private Integer startIndex;
    private Integer pageSize;
    private Integer currentPage;
    private Integer pages;
    
    public PaginatedSearchResult(){
    	super();
    }

    @SuppressWarnings("unchecked")
    protected PaginatedSearchResult(T query) {
        super(query);
    }

    public Integer getNumberOfPages() {
        return pages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    @SuppressWarnings("unchecked")
    public Iterable<T> getResultPageQueries() {
        return new PageQueryIterable(this);
    }

    @SuppressWarnings("unchecked")
    public T getPageQuery(Integer page) {
        if (page > getNumberOfPages())
            throw new IllegalStateException("Cannot page to a page that does not exist");

        return (T) ((PaginatedSearchQuery) query).cloneForNextPageQuery(page);
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }


}
