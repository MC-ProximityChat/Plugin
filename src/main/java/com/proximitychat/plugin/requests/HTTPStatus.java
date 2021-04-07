package com.proximitychat.plugin.requests;

@SuppressWarnings("unused")
public enum HTTPStatus {

    CONTINUE(100, "Continue", Series.INFORMATIONAL),
    SWITCHING_PROTOCOLS(101, "Switching Protocols", Series.INFORMATIONAL),

    OK(200, "OK", Series.SUCCESS),
    CREATED(201, "Created", Series.SUCCESS),
    ACCEPTED(202, "Accepted", Series.SUCCESS),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information", Series.SUCCESS),
    NO_CONTENT(204, "No Content", Series.SUCCESS),
    RESET_CONTENT(205, "Reset Content", Series.SUCCESS),

    MULTIPLE_CHOICES(300, "Multiple Choices", Series.REDIRECTION),
    MOVED_PERMANENTLY(301, "Moved Permanently", Series.REDIRECTION),
    FOUND(302, "Found", Series.REDIRECTION),
    SEE_OTHER(303, "See Other", Series.REDIRECTION),
    NOT_MODIFIED(304, "Not Modified", Series.REDIRECTION),
    USE_PROXY(305, "Use Proxy", Series.REDIRECTION),
    TEMPORARY_REDIRECT(307, "Temporary Redirect", Series.REDIRECTION),
    PERMANENT_REDIRECT(308, "Permanent Redirect", Series.REDIRECTION),

    BAD_REQUEST(400, "Bad Request", Series.CLIENT),
    UNAUTHORIZED(401, "Unauthorized", Series.CLIENT),
    PAYMENT_REQUIRED(402, "Payment Required", Series.CLIENT),
    FORBIDDEN(403, "Forbidden", Series.CLIENT),
    NOT_FOUND(404, "Not Found", Series.CLIENT),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", Series.CLIENT),
    NOT_ACCEPTABLE(406, "Not Acceptable", Series.CLIENT),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", Series.CLIENT),
    REQUEST_TIMEOUT(408, "Request Timeout", Series.CLIENT),
    CONFLICT(409, "Conflict", Series.CLIENT),
    GONE(410, "Gone", Series.CLIENT),
    BLAZE_IT(420, "Blaze It", Series.CLIENT),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error", Series.SERVER),
    NOT_IMPLEMENTED(501, "Not Implemented", Series.SERVER),
    BAD_GATEWAY(502, "Bad Gateway", Series.SERVER),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", Series.SERVER),
    GATEWAY_TIMEOUT(504, "Gateway Timeout", Series.SERVER);

    private final int code;
    private final String message;
    private final Series series;

    HTTPStatus(int code, String message, Series series) {
        this.code = code;
        this.message = message;
        this.series = series;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Series getSeries() {
        return series;
    }

    public boolean isInformational() {
        return this.series == Series.INFORMATIONAL;
    }

    public boolean isSuccessful() {
        return this.series == Series.SUCCESS;
    }

    public boolean isRedirection() {
        return this.series == Series.REDIRECTION;
    }

    public boolean isClientError() {
        return this.series == Series.CLIENT;
    }

    public boolean isServerError() {
        return this.series == Series.SERVER;
    }

    public boolean isError() {
        return isClientError() || isServerError();
    }

    @Override
    public String toString() {
        return this.code + " " + this.getMessage();
    }


    /**
     * Gets HTTP Status from its appropriate status code. Also checks if its null, so you dont mess things up.
     */
    public static HTTPStatus from(int statusCode) {
        HTTPStatus status = getStatusFrom(statusCode);

        if (status == null)
            throw new IllegalArgumentException("Valid status doesn't exist for " + statusCode + "!");

        return status;
    }

    /**
     * Performs logic for getting HTTP Status from its appropriate status code.
     */
    private static HTTPStatus getStatusFrom(int statusCode) {
        for (HTTPStatus s : values()) {
            if (s.getCode() == statusCode)
                return s;
        }
        return null;
    }

    private enum Series {
        INFORMATIONAL("Informational Response"),
        SUCCESS("Success"),
        REDIRECTION("Redirection"),
        CLIENT("Client Error"),
        SERVER("Server Error");

        private final String title;

        Series(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public int getValue() {
            return this.ordinal() + 1;
        }
    }
}
