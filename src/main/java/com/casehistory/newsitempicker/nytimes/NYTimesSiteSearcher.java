/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesSiteSearcher {

	// http://query.nytimes.com/search/sitesearch?query=eurozone+economic+crisis&less=multimedia&more=past_365
	private final String BASE_URL = "http://query.nytimes.com/search/sitesearch?query=";
	private final String ONLY_ARTICLES = "&less=multimedia";
	private final String PAST_7_DAYS = "&more=past_7";
	private final String PAST_30_DAYS = "&more=past_30";
	private final String PAST_365_DAYS = "&more=past_365";
	private final String ALL = "&more=date_all";

	private final int MAX_RESULT_PAGES = 10;
	private static final Logger logger = Logger.getLogger(NYTimesSiteSearcher.class);

	private Set<String> getResultUrls(String[] query) throws IOException {
		Set<String> resultUrls = new HashSet<String>();
		String queryUrl = BASE_URL;
		for (int i = 0; i < query.length - 1; i++) {
			queryUrl += query[i] + "+";
		}
		queryUrl += query[query.length - 1];
		// TODO: make this configurable
		queryUrl += ONLY_ARTICLES + PAST_30_DAYS;

		Document doc = Jsoup.connect(queryUrl).get();
		for (Element element : doc.getElementById("search_results").getElementsByClass("clearfix")) {
			for (Element link : element.getElementsByAttribute("href")) {
				resultUrls.add(link.attr("href"));
			}
		}

		boolean morePagesToFetch = true;
		int pageCount = 0;
		Document nextPageDoc = doc;
		while (morePagesToFetch) {
			if (nextPageDoc.getElementById("srchPagination") != null) {
				if (nextPageDoc.getElementById("srchPagination").getElementsByClass("next") != null
						&& !nextPageDoc.getElementById("srchPagination").getElementsByClass("next").isEmpty()) {
					String nextPageUrl = nextPageDoc.getElementById("srchPagination").getElementsByClass("next").get(0)
							.attr("href");
					pageCount++;
					if (pageCount == MAX_RESULT_PAGES) {
						morePagesToFetch = false;
					} else {
						nextPageDoc = Jsoup.connect(nextPageUrl).get();
						for (Element element : nextPageDoc.getElementById("search_results").getElementsByClass(
								"clearfix")) {
							for (Element link : element.getElementsByAttribute("href")) {
								resultUrls.add(link.attr("href"));
							}
						}
					}
				} else {
					morePagesToFetch = false;
				}
			} else {
				morePagesToFetch = false;
			}
		}

		return resultUrls;
	}

	private String getBody(String url) throws IOException {
		StringBuilder allText = new StringBuilder();
		String pageUrl = url;
		int pageNum = 1;
		try {
			Document doc = Jsoup.connect(url).get();
			int maxPageNum = getNumPages(doc);
			while (pageNum <= maxPageNum) {
				pageUrl = pageNum > 1 ? url + "?pagewanted=" + pageNum : url;
				doc = Jsoup.connect(pageUrl).get();
				for (Element element : doc.getElementsByClass("articleBody")) {
					allText.append(element.text()).append(" ");
				}
				pageNum++;
			}
		} catch (IOException e) {
			logger.info("Failed to retrieve contents of " + pageUrl);
			if (pageNum == 1)
				throw e;
		}

		return allText.toString();
	}

	private int getNumPages(Document doc) {
		int maxPageNum = 1;
		if (doc.getElementById("pageLinks") != null) {
			for (Element pageLink : doc.getElementById("pageLinks").getElementsByAttribute("title")) {
				if (!pageLink.attr("title").startsWith("Next Page")) {
					maxPageNum = Integer.parseInt(pageLink.attr("title").substring(5));
				} else {
					break;
				}
			}
		}

		return maxPageNum;
	}

	public List<NYTimesArticle> getNewsArticles(String[] query) throws IOException {
		List<NYTimesArticle> articles = new ArrayList<NYTimesArticle>();
		Set<String> resultUrls = getResultUrls(new String[] { "eurozone", "\"economic", "crisis\"" });
		for (String resultUrl : resultUrls) {
			try {
				String text = getBody(resultUrl);
				if (text != null && !text.isEmpty())
					articles.add(new NYTimesArticle(resultUrl, "", text, "", ""));
				logger.info("Fetched article at url " + resultUrl);
				logger.info(text + "\n");
			} catch (IOException e) {
				logger.info("Failed to fetch article at " + resultUrl);
			}
		}

		return articles;
	}

}
