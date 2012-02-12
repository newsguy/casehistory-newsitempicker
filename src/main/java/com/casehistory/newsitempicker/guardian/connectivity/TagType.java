package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.HashSet;
import java.util.Set;

public class TagType implements ApiUrlParameter {

    public static final Set<TagType> allTypes = new HashSet<TagType>();

    public static final TagType keyword = type("keyword");
    public static final TagType blog = type("blog");
    public static final TagType contributor = type("contributor");
    public static final TagType series = type("series");
    public static final TagType tone = type("tone");
    public static final TagType contentType = type("type");

    public static final TagType all = new TagType("all");

    public static TagType typeFor(String type) {
        return new TagType(type);
    }

    private static TagType type(String type) {
        TagType tagType = typeFor(type);
        allTypes.add(tagType);
        return tagType;
    }

    private final String typeParameter;

    TagType(String type) {
        this.typeParameter = type;
    }

    public String asApiUrlParameter() {
        return typeParameter;
    }

    @Override
    public int hashCode() {
        return typeParameter.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TagType && isEqual((TagType) obj);
    }

    @Override
    public String toString() {
        return "TagType [" + typeParameter + "]";
    }

    protected boolean isEqual(TagType tagType) {
        return typeParameter != null && tagType.asApiUrlParameter() != null && asApiUrlParameter().equals(tagType.asApiUrlParameter());
    }
}
