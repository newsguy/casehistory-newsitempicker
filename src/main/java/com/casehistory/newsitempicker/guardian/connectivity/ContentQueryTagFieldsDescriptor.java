package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static java.util.Collections.unmodifiableSet;
import static com.casehistory.newsitempicker.guardian.connectivity.TagListManipulator.addTagTypeToList;

class ContentQueryTagFieldsDescriptor<T extends ApiQuery> implements ContentQueryParameters<T> {

    private final Set<TagType> tagTypesToQuery = new HashSet<TagType>();
    private final Set<FieldType> fieldsToQuery = new HashSet<FieldType>();
    private final T apiQuery;

    ContentQueryTagFieldsDescriptor(T apiQuery) {
        this.apiQuery = apiQuery;
    }

    public T showAllTagTypes() {
        tagTypesToQuery.clear();
        tagTypesToQuery.add(TagType.all);
        return apiQuery;
    }

    public T showTagTypes(String... tagIds) {
        for (String tagId : tagIds)
            addTagTypeToList(tagTypesToQuery, TagType.typeFor(tagId));

        return apiQuery;
    }

    public T showTagTypes(TagType... tags) {
        for (TagType tag : tags)
            addTagTypeToList(tagTypesToQuery, tag);

        return apiQuery;
    }

    public T allFields() {
        fieldsToQuery.clear();
        fieldsToQuery.add(FieldType.showAllFields);
        return apiQuery;
    }

    public T showField(String... fieldNames) {
        for (String fieldName : fieldNames)
            fieldsToQuery.add(FieldType.typeFor(fieldName));

        return apiQuery;
    }

    public T showField(FieldType... fieldTypes) {
        fieldsToQuery.addAll(Arrays.asList(fieldTypes));
        return apiQuery;
    }

    Set<TagType> getTagTypesToFind() {
        return unmodifiableSet(tagTypesToQuery);
    }

    boolean isRetrievingAllFields() {
        return fieldsToQuery.contains(FieldType.showAllFields);
    }

//    Set<? extends ApiUrlParameter> getFieldsToFind() {
//        return (Set<? extends ApiUrlParameter>) unmodifiableSet(fieldsToQuery);
//    }

    public void fillInApiParameters(Map<ApiParameter, Object> parameters) {
        apiQuery.addHttpParameter(parameters, ApiParameter.showFields, fieldsToQuery);
        apiQuery.addHttpParameter(parameters, ApiParameter.showTags, tagTypesToQuery);
    }
}

