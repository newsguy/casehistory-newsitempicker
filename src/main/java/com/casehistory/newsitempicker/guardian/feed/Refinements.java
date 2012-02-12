
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Refinements{
   	private String apiUrl;
   	private Number count;
   	private String displayName;
   	private String id;
   	private String refinedUrl;

 	public String getApiUrl(){
		return this.apiUrl;
	}
	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}
 	public Number getCount(){
		return this.count;
	}
	public void setCount(Number count){
		this.count = count;
	}
 	public String getDisplayName(){
		return this.displayName;
	}
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getRefinedUrl(){
		return this.refinedUrl;
	}
	public void setRefinedUrl(String refinedUrl){
		this.refinedUrl = refinedUrl;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
