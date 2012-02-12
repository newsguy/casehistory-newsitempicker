package com.casehistory.newsitempicker.guardian.connectivity;

import static com.casehistory.newsitempicker.guardian.connectivity.TagListManipulator.addTagTypeToList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

import com.casehistory.newsitempicker.guardian.feed.Data;
import com.casehistory.newsitempicker.guardian.feed.GuardianArticle;
import com.casehistory.newsitempicker.guardian.feed.ResponseToArticleConverter;
import com.casehistory.newsitempicker.guardian.feed.Tags;
import com.google.gson.Gson;

public class ContentSearchQuery extends PaginatedSearchQuery<ContentSearchQuery> {
    
	private final Set<TagType> refinementsToReturn = new HashSet<TagType>();
	private final Set<String> tagsToFilter = new HashSet<String>();
    private DateTime fromDate;
    private DateTime toDate;
    private final ContentQueryTagFieldsDescriptor<ContentSearchQuery> contentQueryParameters = new ContentQueryTagFieldsDescriptor<ContentSearchQuery>(this);
	
	public ContentSearchQuery(HttpClient httpClient) {
		super(httpClient);
	}

    @Override
    protected void fillInApiParameters(Map<ApiParameter, Object> parameters) {
        super.fillInApiParameters(parameters);
        contentQueryParameters.fillInApiParameters(parameters);

        addHttpParameter(parameters, ApiParameter.tag, tagsToFilter);
        addHttpParameter(parameters, ApiParameter.showRefinements, refinementsToReturn);
        addHttpParameter(parameters, ApiParameter.fromDate, fromDate);
        addHttpParameter(parameters, ApiParameter.toDate, toDate);
    }
	
    public ContentSearchQuery showRefinementsOfType(TagType tagType) {
        addTagTypeToList(refinementsToReturn, tagType);
        return ContentSearchQuery.this;
    }

    public ContentQueryParameters<ContentSearchQuery> showRefinementsOfType(TagType... types) {
        for (TagType type : types)
            addTagTypeToList(refinementsToReturn, type);

        return contentQueryParameters;
    }

    public ContentSearchQuery showRefinementsOfType(String... types) {
        for (String type : types)
            showRefinementsOfType(TagType.typeFor(type));

        return ContentSearchQuery.this;
    }
    
    public ContentSearchQuery filterByTag(Tags tag) {
        tagsToFilter.add(tag.getId());
        return ContentSearchQuery.this;
    }

    public ContentQueryParameters<ContentSearchQuery> filterByTag(Tags... tags) {
        for (Tags tag : tags)
            tagsToFilter.add(tag.getId());

        return contentQueryParameters;
    }

    public ContentSearchQuery filterByTag(String... tagIds) {
        for (String tagId : tagIds)
            tagsToFilter.add(tagId);
        return ContentSearchQuery.this;
    }

	
    public List<GuardianArticle> execute() {
    	try {
           String executeQuery = httpClient.executeQuery("/search", this).trim();
           System.out.println("----- Raw response -------");
           System.out.println(executeQuery);
           System.out.println("---------------------------");
		   //parser.parseJson(executeQuery,searchResult);
           Gson gson = new Gson();
           Data  c = gson.fromJson(executeQuery, Data.class);
           ResponseToArticleConverter converter = new ResponseToArticleConverter();
           return converter.convertToArticle(c.getResponse().getResults());
        }
        catch (HttpException e) {
           e.printStackTrace();
        }
       return null;
    }
    
    public ContentSearchQuery fromDate(DateTime fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public ContentSearchQuery fromDate(Date fromDate) {
        this.fromDate = new DateTime(fromDate);
        return this;
    }

    DateTime getFromDate() {
        return fromDate;
    }

    public ContentSearchQuery toDate(DateTime toDate) {
        this.toDate = toDate;
        return this;
    }

    public ContentSearchQuery toDate(Date toDate) {
        this.toDate = new DateTime(toDate);
        return this;
    }

    DateTime getToDate() {
        return toDate;
    }
    

}
