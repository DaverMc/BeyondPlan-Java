package de.daver.beyondplan.client;

import de.daver.beyondplan.client.web.WebClient;
import de.daver.beyondplan.client.web.WebClientSocket;

public class TestClient {

    public static void main(String[] args) throws Exception {
        testSocket();
        testWeb();
    }

    private static void testWeb() throws Exception{
        WebClient client = new WebClient();
        while (true) {
            client.request();
        }
    }

    private static void testSocket() throws Exception {
        WebClientSocket socket = new WebClientSocket("localhost", 1337);
        socket.start();
        socket.handShake();
    }

}
