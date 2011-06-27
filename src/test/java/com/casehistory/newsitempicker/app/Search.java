package com.casehistory.newsitempicker.app;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.nutch.searcher.Hit;
import org.apache.nutch.searcher.HitDetails;
import org.apache.nutch.searcher.Hits;
import org.apache.nutch.searcher.NutchBean;
import org.apache.nutch.searcher.Query;
import org.apache.nutch.segment.SegmentReader;
import org.apache.nutch.util.NutchConfiguration;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search {
	public static void main(String[] args) {
		try {
			// define a keyword for the search
			String nutchSearchString = "smart";

			// configure nutch
			Configuration nutchConf = NutchConfiguration.create();
			SegmentReader reader = new SegmentReader(nutchConf, true, false, false, false, false, true);
			NutchBean nutchBean = new NutchBean(nutchConf);
			// build the query
			Query nutchQuery = Query.parse(nutchSearchString, nutchConf);
			// optionally specify the maximum number of hits (default is 10)
			// nutchQuery.getParams().setNumHits(100);
			// nutchQuery.getParams().setMaxHitsPerDup(100);
			Hits nutchHits = nutchBean.search(nutchQuery);

			// display the number of hits
			System.out.println("Found " + nutchHits.getLength() + " hits.\n");

			// get the details about each hit (includes title, URL, a summary
			// and the date when this was fetched)
			for (int i = 0; i < nutchHits.getLength(); i++) {
				Hit hit = nutchHits.getHit(i);
				HitDetails details = nutchBean.getDetails(hit);
				String title = details.getValue("title");
				String url = details.getValue("url");
				String segment = details.getValue("segment");
				String summary = nutchBean.getSummary(details, nutchQuery).toString();
				System.out.println("Title is: " + title);
				System.out.println("(" + url + ")");
				Date date = new Date(nutchBean.getFetchDate(details));
				System.out.println("Date Fetched: " + date);
				System.out.println(summary + "\n");
				System.out.println("Segment " + segment);
				System.out.println("----------------------------------------");
				Path path = new Path(segment);
				List<Path> paths = new ArrayList<Path>();
				paths.add(path);
				reader.list(paths, new BufferedWriter(new OutputStreamWriter(System.out)));
			}

			// as usually, don't forget to close the resources
			nutchBean.close();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}