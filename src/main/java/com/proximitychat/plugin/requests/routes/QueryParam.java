package com.proximitychat.plugin.requests.routes;

import com.proximitychat.plugin.util.tuples.ImmutablePair;

public class QueryParam {

    private final ImmutablePair<String, String> param;

    protected QueryParam(String key, String value) {
        this.param = ImmutablePair.of(key, value);
    }

    public static QueryParam of(String key, String value) {
        return new QueryParam(key, value);
    }

    public String getKey() {
        return param.getKey();
    }

    public String getValue() {
        return param.getValue();
    }

    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }
}
