package com.proximitychat.plugin.http;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class RequestSender {

    CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
}
