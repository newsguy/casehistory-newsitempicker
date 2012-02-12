package com.casehistory.newsitempicker.guardian.feed;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.casehistory.newsitempicker.guardian.connectivity.ApiUrlFactory;
import com.casehistory.newsitempicker.guardian.connectivity.ContentSearchQuery;
import com.casehistory.newsitempicker.guardian.connectivity.HttpClient;
import com.casehistory.newsitempicker.guardian.connectivity.HttpQueryExecutor;
import com.casehistory.newsitempicker.guardian.connectivity.TagType;

public class GuardianFeed {

	public static void main(String[] args){
		HttpQueryExecutor executor = new HttpQueryExecutor();
		ApiUrlFactory urlFactory = new ApiUrlFactory();
		HttpClient httpClient = new HttpClient(urlFactory, executor);
		ContentSearchQuery query =  new ContentSearchQuery(httpClient)
		                               .apiKey("wtz47kjkt9r9fvuz2vb4bxvc")
		                               .showRefinementsOfType(TagType.tone)
		                               .query("wayne rooney").fromDate(new DateTime(2010, 2, 12, 9, 15, 15, 15, DateTimeZone.UTC)).toDate(new DateTime(2011, 2, 12, 9, 15, 15, 15, DateTimeZone.UTC));
		List<GuardianArticle> articles = query.execute();
//		ContentSearchQuery nextPage = query.cloneForNextPageQuery(2);
//		System.out.println(nextPage.execute());

	}
	
	
}
