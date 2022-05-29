package ru.netology.request;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class QueryParams {
    private final List<NameValuePair> queryParameters;

    public QueryParams(String params) {
        queryParameters = URLEncodedUtils.parse(params, Charset.defaultCharset());

    }

    public List<NameValuePair> getQueryParams() throws NullPointerException {
        return queryParameters;
    }

    public List<NameValuePair> getQueryParam(String name) {
        return queryParameters.stream().filter(pair -> pair.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "QueryParams{" +
                "queryParameters=" + queryParameters +
                '}';
    }
}