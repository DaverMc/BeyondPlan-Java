package de.daver.beyondplan.client.web;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WebClient {

    //TODO Client Config machen
    private final HttpClient client;

    public WebClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String request(HttpRequest request) throws ExecutionException, InterruptedException {
        CompletableFuture<HttpResponse<String>> response = this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.get().body();
    }
}
