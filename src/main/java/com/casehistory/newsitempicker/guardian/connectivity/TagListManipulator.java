package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.Collection;


abstract class TagListManipulator {

    static void addTagTypeToList(Collection<TagType> list, TagType tagType) {
        if (TagType.all.equals(tagType))
            list.clear();
        else
            list.remove(TagType.all);

        list.add(tagType);
    }
}