
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Tags{
   	private String apiUrl;
   	private String id;
   	private String type;
   	private String webTitle;
   	private String webUrl;

 	public String getApiUrl(){
		return this.apiUrl;
	}
	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public String getWebTitle(){
		return this.webTitle;
	}
	public void setWebTitle(String webTitle){
		this.webTitle = webTitle;
	}
 	public String getWebUrl(){
		return this.webUrl;
	}
	public void setWebUrl(String webUrl){
		this.webUrl = webUrl;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
