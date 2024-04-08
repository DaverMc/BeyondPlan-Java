package de.daver.beyondplan.client.web;

import de.daver.beyondplan.core.web.WebServerSocket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebClientTest {

    @Test
    void WebClientSocketStartUp() {
        WebServerSocket serverSocket = assertDoesNotThrow(() -> new WebServerSocket(1337), "Test failed caused by outside factors");
        serverSocket.start();

        WebClientSocket socket = assertDoesNotThrow(() ->  new WebClientSocket("localhost", 1337));
        assertTrue(socket.start());
        socket.handShake();
        assertDoesNotThrow(() -> socket.stop(false));
        serverSocket.stop(false);
    }
}