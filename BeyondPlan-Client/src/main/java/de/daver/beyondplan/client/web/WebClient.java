package de.daver.beyondplan.client.web;

import de.daver.beyondplan.util.json.JsonObject;
import de.daver.beyondplan.util.json.JsonParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WebClient {

    private final HttpClient client;

    public WebClient() {
        this.client = HttpClient.newHttpClient();
    }

    public JsonObject request() throws URISyntaxException, ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/test"))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response = this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return JsonParser.ofHttpResponse(response.get()); //TODO Timeout einbauen in WebConfig festlegen
    }
}
