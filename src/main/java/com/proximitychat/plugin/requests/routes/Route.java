package com.proximitychat.plugin.requests.routes;

import com.proximitychat.plugin.util.StringUtil;
import org.asynchttpclient.util.StringUtils;

public class Route {

    private final String baseUrl;

    private final String path;

    public Route(String baseUrl, String path) {
        this.baseUrl = StringUtil.appendIfNotExists(baseUrl, "/");
        this.path = path;
    }

    public String compile() {
        return baseUrl + path;
    }

    public String compile(QueryParams params) {
        return baseUrl + path + params.toQueryParamStr();
    }
}
