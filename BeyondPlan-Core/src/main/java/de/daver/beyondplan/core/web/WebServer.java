package de.daver.beyondplan.core.web;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {

    private final HttpServer server;

    public WebServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

    }

    public void start() {
        server.setExecutor(null);
        server.start();
    }

    public void stop() {
        server.stop(1); //TODO in WebServerConfig umbauen
    }

    public void createContext(String path, HttpHandler handler) {
        server.createContext(path, handler);
    }

}
