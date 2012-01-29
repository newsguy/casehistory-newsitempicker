/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.List;

/**
 * @author Abhinav Tripathi
 * 
 */
public class NYTimesAPIHelper {

	/*
	 * API constants
	 */

	public static final String API_BASE_URI = "http://api.nytimes.com/svc/search/v1/article?format=json";

	// Facets
	public static final String DES_FACET = "des_facet";
	public static final String DESK_FACET = "desk_facet";
	public static final String COLUMN_FACET = "column_facet";
	public static final String CLASSIFIERS_FACET = "classifiers_facet";
	public static final String GEO_FACET = "geo_facet";
	public static final String ORG_FACET = "org_facet";
	public static final String PAGE_FACET = "page_facet";
	public static final String PER_FACET = "per_facet";
	public static final String SOURCE_FACET = "source_facet";

	// Combined facets
	public static final String FACET_TERMS = "facet_terms";
	
	// Fields
	public static final String ABSTRACT = "abstract";
	public static final String AUTHOR = "author";
	public static final String BODY = "body";
	public static final String BYLINE = "byline";
	public static final String LEAD_PARAGRAPH = "lead_paragraph";
	public static final String COMMENTS = "comments";
	public static final String DATE = "date";
	public static final String DBPEDIA_RESOURCE = "dbpedia_resource";
	public static final String MULTIMEDIA = "multimedia";
	public static final String PUBLICATION_DAY = "publication_day";
	public static final String PUBLICATION_MONTH = "publication_month";
	public static final String PUBLICATION_YEAR = "publication_year";
	public static final String TEXT = "text";
	public static final String TITLE = "title";
	public static final String TOKENS = "tokens";
	public static final String URL = "url";
	public static final String WORD_COUNT = "word_count";
	
	public class ArticleSearchQueryBuilder {
		private StringBuilder query = new StringBuilder(API_BASE_URI);

		public ArticleSearchQueryBuilder withQuery(String... searchTerms) {
			query.append("&query=");
			for (String searchTerm : searchTerms) {
				query.append(searchTerm).append("+");
			}
			query.deleteCharAt(query.length() - 1);

			return this;
		}

		public ArticleSearchQueryBuilder withQuery(List<String> searchTerms) {
			query.append("&query=");
			for (String searchTerm : searchTerms) {
				query.append(searchTerm).append("+");
			}
			query.deleteCharAt(query.length() - 1);

			return this;
		}
		
		public ArticleSearchQueryBuilder withFacets(String... facets) {
			query.append("&facets=");
			for (String facet : facets) {
				query.append(facet).append("%2C");
			}
			query.deleteCharAt(query.length() - 3);

			return this;
		}

		public ArticleSearchQueryBuilder withFacetTerms() {
			query.append("&facet_terms");

			return this;
		}
		
		public ArticleSearchQueryBuilder withFields(List<String> fields) {
			query.append("&fields=");
			for (String field : fields) {
				query.append(field).append(",");
			}
			query.deleteCharAt(query.length() - 1);

			return this;
		}

		public ArticleSearchQueryBuilder withApiKey(String apiKey) {
			query.append("&api-key=").append(apiKey);

			return this;
		}

		public String formQuery() throws IllegalStateException {
			String queryStr = query.toString();
			if (!queryStr.contains("&query=") || !queryStr.contains("&api-key=")) {
				throw new IllegalStateException("Invalid query url! Missing required params: query, api-key");
			}

			return queryStr;
		}
	}

}
