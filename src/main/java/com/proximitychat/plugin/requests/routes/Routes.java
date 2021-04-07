package com.proximitychat.plugin.requests.routes;

public class Routes {

    private static final String BASE_URL = "http://localhost:8081/v1/";

    public static class Server {
        public static Route informationUrl() {
            return new Route(BASE_URL, "server/");
        }

        public static Route joinUrl(String id) {
            return new Route(BASE_URL, "server/join/" + id);
        }
    }
}
