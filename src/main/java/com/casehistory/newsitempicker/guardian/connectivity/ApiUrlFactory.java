package com.casehistory.newsitempicker.guardian.connectivity;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.casehistory.newsitempicker.guardian.connectivity.GuardianApiHostProvider.getGuardianApiHost;

public class ApiUrlFactory {

    private static final Logger logger = LoggerFactory.getLogger(ApiUrlFactory.class);

    URL constructApiUrl(String path, ApiQuery<?> apiQuery) {
        Map<ApiParameter, Object> params = apiQuery.generateParameterMap();
        String hostPrefix = getGuardianApiHost();
        StringBuilder url = new StringBuilder();

        if (path.startsWith(hostPrefix)) {
            url.append(path);
        } else {
            url.append(hostPrefix);

            if (!path.startsWith("/"))
                url.append("/");

            url.append(path);
        }

        Iterator<ApiParameter> parameterIter = params.keySet().iterator();
        if (parameterIter.hasNext()) {
            url.append("?");

            while (parameterIter.hasNext()) {
                appendParam(params, url, parameterIter.next());

                if (parameterIter.hasNext())
                    url.append("&");
            }
        }

        return convertToUrl(url);
    }

    private URL convertToUrl(StringBuilder urlString) {
        URL url;
        try {
            url = new URL(urlString.toString());
        }
        catch (MalformedURLException e) {
            logger.error("Could not convert URL: " + urlString.toString());
            throw new RuntimeException(e);
        }

        return url;
    }

    private void appendParam(Map<ApiParameter, Object> params, StringBuilder url, ApiParameter param) {
        Object value = params.get(param);

        url.append(param.getUrlParam());
        url.append("=");
        url.append(value == null ? "" : UrlEncoder.encode(generateUrlRepresentation(value)));
    }

    private String generateUrlRepresentation(Object parameter) {
        if (parameter instanceof Set)
            return generateUrlRepresentation((Set) parameter);

        if (parameter instanceof Integer)
            return generateUrlRepresentation((Integer) parameter);

        if (parameter instanceof String)
            return (String) parameter;

        if (parameter instanceof DateTime)
            return generateUrlRepresentation((DateTime) parameter);

        throw new RuntimeException("Cannot convert paramter " + parameter.getClass());
    }

    private String generateUrlRepresentation(DateTime date) {
        StringBuilder dateBuilder = new StringBuilder();

        dateBuilder.append(date.getYear());
        dateBuilder.append("-");
        dateBuilder.append(date.getMonthOfYear());
        dateBuilder.append("-");
        dateBuilder.append(date.getDayOfMonth());

        return dateBuilder.toString();
    }

    private String generateUrlRepresentation(Integer parameter) {
        return Integer.toString(parameter);
    }
    
    private String generateUrlRepresentation(Set parameter) {
        StringBuilder builder = new StringBuilder();
        Iterator iter = parameter.iterator();

        while (iter.hasNext()) {
            Object part = iter.next();

            if (part instanceof ApiUrlParameter)
                builder.append(((ApiUrlParameter) part).asApiUrlParameter());
            else
                builder.append(part.toString());

            if (iter.hasNext())
                builder.append(",");
        }

        return builder.toString();
    }


}
