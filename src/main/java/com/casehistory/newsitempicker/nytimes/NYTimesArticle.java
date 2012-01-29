/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

/**
 * @author A.Tripathi
 *
 */
public class NYTimesArticle {
	
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
	public NYTimesArticle(String url, String author, String text, String date, String title) {
		this.url = url;
		this.author = author;
		this.text = text;
		this.date = date;
		this.title = title;
	}

}
