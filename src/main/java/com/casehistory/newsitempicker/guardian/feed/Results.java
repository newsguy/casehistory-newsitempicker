
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Results{
   	private String apiUrl;
   	private Fields fields;
   	private String id;
   	private String sectionId;
   	private String sectionName;
   	private List<Tags> tags;
   	private String webPublicationDate;
   	private String webTitle;
   	private String webUrl;

 	public String getApiUrl(){
		return this.apiUrl;
	}
	public void setApiUrl(String apiUrl){
		this.apiUrl = apiUrl;
	}
 	public Fields getFields(){
		return this.fields;
	}
	public void setFields(Fields fields){
		this.fields = fields;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getSectionId(){
		return this.sectionId;
	}
	public void setSectionId(String sectionId){
		this.sectionId = sectionId;
	}
 	public String getSectionName(){
		return this.sectionName;
	}
	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}
 	public List<Tags> getTags(){
		return this.tags;
	}
	public void setTags(List<Tags> tags){
		this.tags = tags;
	}
 	public String getWebPublicationDate(){
		return this.webPublicationDate;
	}
	public void setWebPublicationDate(String webPublicationDate){
		this.webPublicationDate = webPublicationDate;
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
