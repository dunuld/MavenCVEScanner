package com.example;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface HttpProvider {
    HttpResponseData send(HttpRequest request) throws IOException, InterruptedException;

    static HttpProvider defaultProvider() {
        HttpClient client = HttpClient.newHttpClient();
        return request -> {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpResponseData(response.statusCode(), response.body());
        };
    }
}
