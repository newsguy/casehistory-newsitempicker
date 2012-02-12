
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Fields{
   	private String headline;
   	private String shortUrl;
   	private String thumbnail;

 	public String getHeadline(){
		return this.headline;
	}
	public void setHeadline(String headline){
		this.headline = headline;
	}
 	public String getShortUrl(){
		return this.shortUrl;
	}
	public void setShortUrl(String shortUrl){
		this.shortUrl = shortUrl;
	}
 	public String getThumbnail(){
		return this.thumbnail;
	}
	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
