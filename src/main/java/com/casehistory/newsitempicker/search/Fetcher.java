/**
 * 
 */
package com.casehistory.newsitempicker.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.searcher.Hit;
import org.apache.nutch.searcher.HitDetails;
import org.apache.nutch.searcher.Hits;
import org.apache.nutch.searcher.NutchBean;
import org.apache.nutch.searcher.Query;
import org.apache.nutch.util.NutchConfiguration;

import com.casehistory.newsitempicker.model.WebPage;

/**
 * Searches the Nutch index using the provided query string, and returns a list
 * of web pages. The exact structure returned per web page is {@link WebPage}.
 * 
 * @author Abhinav Tripathi
 */
public class Fetcher {

	private static Logger logger = Logger.getLogger(Fetcher.class);

	public static List<WebPage> getPages(String query) {
		List<WebPage> pages = new ArrayList<WebPage>();
		Configuration nutchConf = NutchConfiguration.create();
		NutchBean bean;
		Query nutchQuery = null;
		try {
			bean = new NutchBean(nutchConf);
			nutchQuery = Query.parse(query, nutchConf);

			nutchQuery.getParams().setNumHits(20);
			// nutchQuery.getParams().setMaxHitsPerDup(100);
			Hits nutchHits = bean.search(nutchQuery);

			for (int i = 0; i < nutchHits.getLength(); i++) {
				Hit hit = nutchHits.getHit(i);
				HitDetails details = bean.getDetails(hit);
				String title = details.getValue("title");
				String url = details.getValue("url");
				Metadata metaData = bean.getParseData(details).getContentMeta();
				String content = null;
				String contentType = (String) metaData.get(Metadata.CONTENT_TYPE);
				if (contentType.startsWith("text/html")) {
					String encoding = (String) metaData.get("CharEncodingForConversion");
					if (encoding != null) {
						try {
							content = new String(bean.getContent(details), encoding);
						} catch (UnsupportedEncodingException e) {
							content = new String(bean.getContent(details), "windows-1252");
						}
					} else
						content = new String(bean.getContent(details));
				}
				WebPage page = new WebPage(url, title, content);
				pages.add(page);
			}
			bean.close();
		} catch (IOException e) {
			logger.error("Failed to fetch search results!");
			logger.error(e.getMessage());
		}

		return pages;
	}

}
