package de.daver.beyondplan.core.web;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class WebServerTest {

    @Test
    void httpRequestStartUp() {
        var server = assertDoesNotThrow(() -> new WebServer(8080));
        server.createContext("/test", new AsyncHttpHandler() {
            @Override
            public void handleAsync(HttpExchange httpExchange) throws IOException {
                System.out.println("Request");
                String response = "This is the response";
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.start();
        server.stop();
    }

    @Test
    void webServerSocketStartUp() {
        WebServerSocket socket = assertDoesNotThrow( () -> new WebServerSocket(1337));
        assertTrue(socket.start());
        assertTrue(socket.stop(false));
        assertTrue(socket.start());
        assertDoesNotThrow( () -> assertTrue(socket.stop(true)));
    }



}