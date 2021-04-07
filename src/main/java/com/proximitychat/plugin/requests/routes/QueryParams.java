package com.proximitychat.plugin.requests.routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryParams {

    private final List<QueryParam> queryParams;

    private QueryParams() {
        this(new ArrayList<>());
    }

    private QueryParams(List<QueryParam> queryParams) {
        this.queryParams = queryParams;
    }

    public static QueryParams of() {
        return new QueryParams();
    }

    public static QueryParams of(List<QueryParam> params) {
        return new QueryParams(params);
    }

    public static QueryParams of(QueryParam... params) {
        return new QueryParams(Arrays.asList(params));
    }

    public QueryParams param(String key, String value) {
        return param(QueryParam.of(key, value));
    }

    public QueryParams param(QueryParam param) {
        queryParams.add(param);
        return this;
    }

    public boolean isEmpty() {
        return queryParams.isEmpty();
    }

    public String toQueryParamStr() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < queryParams.size(); i++) {
            builder.append(i == 0 ? '?' : '&').append(queryParams.get(i).toString());
        }

        return builder.toString();
    }
}
