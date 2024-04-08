package de.daver.beyondplan.client;

import java.net.http.HttpClient;

public class Client {

    private final HttpClient httpClient;

    public Client() {
        this.httpClient = HttpClient.newHttpClient();
    }

}
