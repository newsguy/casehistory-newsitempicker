/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.List;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesAPIResponse {

	public final int offset;
	public final List<NYTimesAPIResponseResult> results;

	public NYTimesAPIResponse(int offset, List<NYTimesAPIResponseResult> results) {
		this.offset = offset;
		this.results = results;
	}

	public class NYTimesAPIResponseResult {
		public final String url;
		public final String author;
		public final String byline;
		public final String articleAbstract;
		public final String body;
		public final String date;
		public final String title;

		public NYTimesAPIResponseResult(String url, String author, String byline, String articleAbstract, String body,
				String date, String title) {
			this.url = url;
			this.author = author;
			this.byline = byline;
			this.articleAbstract = articleAbstract;
			this.body = body;
			this.date = date;
			this.title = title;
		}

		@Override
		public String toString() {
			return "[url=" + url + ", author=" + author + ", byline=" + byline + ", articleAbstract=" + articleAbstract
					+ ", body=" + body + ", date=" + date + ", title=" + title + "]";
		}
	}

}
