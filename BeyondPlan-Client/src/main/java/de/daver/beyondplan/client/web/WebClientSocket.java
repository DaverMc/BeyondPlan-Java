package de.daver.beyondplan.client.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WebClientSocket {

    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String host;
    private final int port;

    private Thread thread;
    private boolean running;

    public WebClientSocket(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void handShake() {
        // Handshake senden
        out.println("GET / HTTP/1.1");
        out.println("Host: " + host + ":" + port);
        out.println("Upgrade: websocket");
        out.println("Connection: Upgrade");
        out.println("Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==");
        out.println("Sec-WebSocket-Version: 13");
        out.println(""); // Wichtig: Zusätzliche Leerzeile am Ende des Headers
        out.flush();
    }

    public boolean start() {
        if(running || thread != null) return false;
        running = true;
        thread = new Thread(this::run);
        thread.start();
        return true;
    }

    private void run() {
        try {
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }
        } catch (IOException exception) {
            exception.printStackTrace(); //TODO Exception Handling
        }
    }

    public boolean stop(boolean interrupt) {
        if(!running || thread == null) return false;
        running = false;
        if(interrupt) thread.interrupt();
        thread = null;
        return true;
    }
}
