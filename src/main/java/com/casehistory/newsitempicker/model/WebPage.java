/**
 * 
 */
package com.casehistory.newsitempicker.model;

/**
 * A simple data structure that holds the web page HTML and attributes like its
 * url and title.
 * 
 * @author Abhinav Tripathi
 */
public class WebPage {

	private final String url;
	private final String title;
	private final String html;

	/**
	 * @param url
	 * @param html
	 */
	public WebPage(String url, String title, String html) {
		super();
		this.url = url;
		this.title = title;
		this.html = html;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getHtml() {
		return html;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebPage other = (WebPage) obj;
		if (html == null) {
			if (other.html != null)
				return false;
		} else if (!html.equals(other.html))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
