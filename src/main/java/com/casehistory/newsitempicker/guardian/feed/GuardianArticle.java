package com.casehistory.newsitempicker.guardian.feed;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class GuardianArticle {


	public final String url;
	public final String author;
	public final String text;
	public final String date;
	public final String title;
	
	/**
	 * @param url
	 * @param author
	 * @param text
	 * @param date
	 * @param title
	 */
	public GuardianArticle(String url, String author, String text, String date, String title) {
		this.url = url;
		this.author = author;
		this.text = text;
		this.date = date;
		this.title = title;
	}

	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
