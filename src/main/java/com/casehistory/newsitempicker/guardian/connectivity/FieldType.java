package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.HashMap;
import java.util.Map;

public class FieldType implements ApiUrlParameter {

    // TODO: Hack for short URLs. Remove when API is fixed!
    private static final Map<String, String> overrides = new HashMap<String, String>();

    static {
        overrides.put("shortUrl", "short-url");
    }

    public static final FieldType headline = typeFor("headline");
    public static final FieldType byline = typeFor("byline");
    public static final FieldType shortUrl = typeFor("shortUrl");

    public static final FieldType showAllFields = typeFor("all");

    public static FieldType typeFor(String name) {
        String override = overrides.get(name);

        if (override == null)
            return new FieldType(name);

        return new FieldType(name, override);
    }

    private final String name;
    private final String override;

    private FieldType(String name, String override) {
        this.name = name;
        this.override = override;
    }

    private FieldType(String name) {
        this.name = name;
        this.override = name;
    }

    public String asApiUrlParameter() {
        return override;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof FieldType && isEqual((FieldType) obj);
    }

    protected boolean isEqual(FieldType fieldType) {
        return name != null && fieldType.asApiUrlParameter() != null && asApiUrlParameter().equals(fieldType.asApiUrlParameter());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
