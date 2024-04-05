package de.daver.beyondplan.client.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebClientTest {

    @Test
    void WebClientSocketStartUp() {
        WebClientSocket socket = assertDoesNotThrow(() ->  new WebClientSocket("localhost", 1337));
        assertTrue(socket.start());
        socket.handShake();
        assertDoesNotThrow(() -> socket.stop(false));
    }
}