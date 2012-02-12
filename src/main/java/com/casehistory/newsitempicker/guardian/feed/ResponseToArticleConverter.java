package com.casehistory.newsitempicker.guardian.feed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ResponseToArticleConverter {
	
	public List<GuardianArticle> convertToArticle(List<Results> results){
		List<GuardianArticle> articles = new ArrayList<GuardianArticle>();
		for (Results result : results) {
			GuardianArticle article = new GuardianArticle(result.getWebUrl(), result.getApiUrl(), getBody(result.getWebUrl()), result.getWebPublicationDate(), result.getWebTitle());
		    System.out.println(article.toString());
		}
		return articles;
	}

	private String getBody(String url) {
		StringBuilder allText = new StringBuilder();
		String pageUrl = url;
		try {
			Document doc = Jsoup.connect(url).get();
			int pageNum = 1;
			int maxPageNum = getNumPages(doc);
			while (pageNum <= maxPageNum) {
				pageUrl = pageNum > 1 ? url + "?pagewanted=" + pageNum : url;
				doc = Jsoup.connect(pageUrl).get();
				for (Element element : doc.select("div#article-body-blocks")) {
					allText.append(element.text()).append(" ");
				}
				pageNum++;
			}
		} catch (IOException e) {
			System.out.println("Failed to retrieve contents of " + pageUrl);
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

}
