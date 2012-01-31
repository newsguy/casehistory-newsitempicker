/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.casehistory.newsitempicker.json.JSONArray;
import com.casehistory.newsitempicker.json.JSONException;
import com.casehistory.newsitempicker.json.JSONObject;
import com.casehistory.newsitempicker.nytimes.NYTimesAPIHelper.ArticleSearchQueryBuilder;

import static com.casehistory.newsitempicker.nytimes.NYTimesAPIHelper.*;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesArticleSearcher {

	private final String apiKey;
	private static final Logger logger = Logger.getLogger(NYTimesArticleSearcher.class);

	public NYTimesArticleSearcher() {
		ResourceBundle bundle = ResourceBundle.getBundle("nytimes");
		apiKey = bundle.getString("api-key");
	}

	public String getResponse(String[] query) {
		StringBuilder response = new StringBuilder();
		ArticleSearchQueryBuilder queryBuilder = new NYTimesAPIHelper().new ArticleSearchQueryBuilder();
		URL queryUrl = null; // TODO: replace null with Option<T> as suggested
								// by FPFJD book
		try {
			queryUrl = new URL(
					queryBuilder
							.withQuery(Arrays.asList(query))
							.withFields(
									Arrays.asList(new String[] { BODY, URL, AUTHOR, BYLINE, TITLE, DATE, ABSTRACT,
											WORD_COUNT })).withApiKey(apiKey).formQuery());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLConnection cnxn;
		BufferedReader in = null;
		try {
			cnxn = queryUrl.openConnection();
			in = new BufferedReader(new InputStreamReader(cnxn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response.toString();
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

	public static String prettyFormat(String text) {
		StringBuilder builder = new StringBuilder();
		int numToken = 0;
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		int length = tokenizer.countTokens();
		for (int i = 0; i < length; i++) {
			numToken++;
			if (numToken < 10) {
				builder.append(tokenizer.nextToken()).append(" ");
			} else {
				builder.append(tokenizer.nextToken()).append(" ").append("\n");
				numToken = 0;
			}
		}

		return builder.toString();
	}

	public List<NYTimesArticle> getNewsArticles(String[] query) throws JSONException {
		List<NYTimesArticle> articles = new ArrayList<NYTimesArticle>();
		JSONObject json = new JSONObject(getResponse(query));
		JSONArray results = json.getJSONArray("results");
		for (int i = 0; i < results.length(); i++) {
			try {
				String url = results.getJSONObject(i).get(URL).toString();
				String author = "";
				if (results.getJSONObject(i).has(BYLINE))
					author = results.getJSONObject(i).get(BYLINE).toString().substring(3);
				String text = getBody(url);
				String date = results.getJSONObject(i).get(DATE).toString();
				String title = results.getJSONObject(i).get(TITLE).toString();
				articles.add(new NYTimesArticle(url, author, text, date, title));
				logger.info("Fetched article at url " + url);
				// logger.info(prettyFormat(searcher.getBody(results.getJSONObject(i).get(URL).toString())));
				// logger.info(searcher.getBody(results.getJSONObject(i).get(URL).toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.info("Failed to fetch article!");
			}
		}

		return articles;
	}

	public static void main(String[] args) throws JSONException {
		String[] query = args;
		NYTimesArticleSearcher searcher = new NYTimesArticleSearcher();
		try {
			searcher.getNewsArticles(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
