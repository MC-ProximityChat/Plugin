package com.proximitychat.plugin.requests;

import com.proximitychat.plugin.ProximityChat;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class RestResponse<S extends CommandSender, T extends ResponseBody> {

    private final S sender;

    private final HTTPStatus statusCode;

    private final Map<String, String> headers;

    private final T body;

    private final String bodyStr;

    private RestResponse(S sender, HTTPStatus statusCode, Map<String, String> headers, T body, String bodyStr) {
        this.sender = sender;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
        this.bodyStr = bodyStr;
    }

    public static <S extends CommandSender, T extends ResponseBody> Builder<S, T> builder(S sender, Class<T> clazz) {
        return new Builder<>(sender, clazz);
    }

    public S getSender() {
        return sender;
    }

    public HTTPStatus getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }

    public String getBodyStr() {
        return bodyStr;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static class Builder<S extends CommandSender, T extends ResponseBody> {

        private final S sender;

        private final Class<T> clazz;

        private HTTPStatus statusCode;

        private Map<String, String> headers;

        private T body;

        private String bodyStr;

        public Builder(S sender, Class<T> clazz) {
            this.sender = sender;
            this.clazz = clazz;
        }

        public Builder<S, T> statusCode(int statusCode) {
            this.statusCode = HTTPStatus.from(statusCode);
            return this;
        }

        public Builder<S, T> headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder<S, T> bodyStr(String bodyStr) {
            this.bodyStr = bodyStr;
            this.body = fromBodyStr(bodyStr);
            return this;
        }

        private T fromBodyStr(String bodyStr) {
            return ProximityChat.GSON.fromJson(bodyStr, clazz);
        }

        public RestResponse<S, T> build() {
            return new RestResponse<>(sender, statusCode, headers, body, bodyStr);
        }

    }
}
