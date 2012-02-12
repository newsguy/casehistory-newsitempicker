package com.casehistory.newsitempicker.guardian.connectivity;


interface ContentQueryParameters<T extends ApiQuery> {

    T showAllTagTypes();

    T showTagTypes(String... tagIds);

    T showTagTypes(TagType... tags);

    T allFields();

    T showField(String... fieldNames);

    T showField(FieldType... fieldTypes);
}
