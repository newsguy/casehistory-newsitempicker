package com.casehistory.newsitempicker.guardian.connectivity;

enum ApiParameter {

    q,
    page,
    pageSize,
    format,
    type,
    showTags,
    showFields,
    section,
    tag,
    showRefinements,
    fromDate,
    toDate,
    apiKey("api-key");

    private final String urlParam;

    private ApiParameter() {
        urlParam = null;
    }

    private ApiParameter(String urlParameterOverride) {
        urlParam = urlParameterOverride;
    }

    public String getUrlParam() {
        if (urlParam == null)
            return name();

        return urlParam;
    }

    public static ApiParameter findByUrlParam(String urlParam) {
        for (ApiParameter apiParameter : ApiParameter.values())
            if (apiParameter.getUrlParam().equals(urlParam))
                return apiParameter;

        throw new IllegalStateException("Cant find ApiParameter by param: " + urlParam);
    }
}
