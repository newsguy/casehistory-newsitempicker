
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Response{

	private Number currentPage;
   	private String orderBy;
   	private Number pageSize;
   	private Number pages;
   	private List<RefinementGroups> refinementGroups;
   	private List<Results> results;
   	private Number startIndex;
   	private String status;
   	private Number total;
   	private String userTier;

 	public Number getCurrentPage(){
		return this.currentPage;
	}
	public void setCurrentPage(Number currentPage){
		this.currentPage = currentPage;
	}
 	public String getOrderBy(){
		return this.orderBy;
	}
	public void setOrderBy(String orderBy){
		this.orderBy = orderBy;
	}
 	public Number getPageSize(){
		return this.pageSize;
	}
	public void setPageSize(Number pageSize){
		this.pageSize = pageSize;
	}
 	public Number getPages(){
		return this.pages;
	}
	public void setPages(Number pages){
		this.pages = pages;
	}
 	public List<RefinementGroups> getRefinementGroups(){
		return this.refinementGroups;
	}
	public void setRefinementGroups(List<RefinementGroups> refinementGroups){
		this.refinementGroups = refinementGroups;
	}
 	public List<Results> getResults(){
		return this.results;
	}
	public void setResults(List<Results> results){
		this.results = results;
	}
 	public Number getStartIndex(){
		return this.startIndex;
	}
	public void setStartIndex(Number startIndex){
		this.startIndex = startIndex;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public Number getTotal(){
		return this.total;
	}
	public void setTotal(Number total){
		this.total = total;
	}
 	public String getUserTier(){
		return this.userTier;
	}
	public void setUserTier(String userTier){
		this.userTier = userTier;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
