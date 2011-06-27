package com.casehistory.newsitempicker.app;

import java.util.ArrayList;
import java.util.List;

import com.casehistory.newsitempicker.model.WebPage;

/**
 * The data structure for the list of pages retrieved from Nutch indexes, that
 * will be forwarded to the sender for further delivery.
 * 
 * @author Abhinav Tripathi
 */
public class NewsItemsPayload {

	private String userId;
	private String query;
	private List<WebPage> pages;

	public NewsItemsPayload(String queryMessage) {
		String[] fieldsAndValues = queryMessage.split(",");
		for (String fieldAndValue : fieldsAndValues) {
			if (fieldAndValue.startsWith("userId")) {
				userId = fieldAndValue.split(":")[1];
			}
			if (fieldAndValue.startsWith("query")) {
				query = fieldAndValue.split(":")[1];
			}
		}
	}

	public String getUserId() {
		return userId;
	}

	public String getQuery() {
		return query;
	}

	public List<WebPage> getPages() {
		return pages;
	}

	public void setPages(List<WebPage> pages) {
		this.pages = pages;
	}

	public List<String> getAsMessages() {
		List<String> messages = new ArrayList<String>();
		for (WebPage page : pages) {
			messages.add("userId:" + userId + ",query:" + query + ",pageurl:" + page.getUrl() + ",pagetitle:"
					+ page.getTitle() + ",pagecontent:" + page.getHtml());
		}

		return messages;
	}

}
