package de.daver.beyondplan.test;

import com.sun.net.httpserver.HttpExchange;
import de.daver.beyondplan.client.web.WebClient;
import de.daver.beyondplan.client.web.WebClientSocket;
import de.daver.beyondplan.core.web.AsyncHttpHandler;
import de.daver.beyondplan.core.web.WebServer;
import de.daver.beyondplan.core.web.WebServerSocket;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientTest {

    @Test
    void oneServerOneClientHttpRequest() {
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

        WebClient webClient = new WebClient();
        assertDoesNotThrow(webClient::request);

        server.stop();
    }

    @Test
    void oneServerOneClientWebSocket() {
        WebServerSocket serverSocket = assertDoesNotThrow( () -> new WebServerSocket(1337));
        assertTrue(serverSocket.start());

        WebClientSocket socket = assertDoesNotThrow(() ->  new WebClientSocket("localhost", 1337));
        assertTrue(socket.start());
        socket.handShake();
        assertDoesNotThrow(() -> socket.stop(false));

        assertTrue(serverSocket.stop(false));
    }

}
