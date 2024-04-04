package de.daver.beyondplan.core.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncHttpHandler implements HttpHandler {

    private final ExecutorService virtualThreadPool;

    public AsyncHttpHandler(){
        this.virtualThreadPool = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        virtualThreadPool.submit(() -> {
            try {
                handleAsync(httpExchange);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        });
    }

    public abstract void handleAsync(HttpExchange httpExchange) throws IOException;
 }
