package de.daver.beyondplan.core.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServerSocket {

    private final ServerSocket server;
    private final List<Client> clients;
    private Thread thread;
    private boolean running;

    public WebServerSocket(int port) throws IOException {
        this.server = new ServerSocket(port);
        this.clients = new ArrayList<>();
    }

    public boolean start() {
        if(running) return false;
        running = true;
        thread = new Thread(this::run);
        thread.start();
        return true;
    }

    public boolean stop(boolean interrupt) {
        if(thread == null || !running) return false;
        running = false;
        if(interrupt) thread.interrupt();
        thread = null;
        return true;
    }

    private void run() {
        try {
            while (running) {
                Socket socket = server.accept();
                System.out.println("Verbindung akzeptiert: " + socket.getRemoteSocketAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream out = socket.getOutputStream();
                Client client = new Client(socket, in, out);
                clients.add(client);

                handShake(client);
            }
        } catch (IOException exception) {
            exception.printStackTrace(); //TODO Exception Handling
        }
    }

    private void handShake(Client client) throws IOException {
        // Handshake durchführen
        String data = client.in().readLine();
        Pattern get = Pattern.compile("^GET");
        Matcher matcher = get.matcher(data);

        if (matcher.find()) {
            // Ein einfacher Handshake, nicht für Produktion geeignet
            String response = "HTTP/1.1 101 Switching Protocols\r\n"
                    + "Connection: Upgrade\r\n"
                    + "Upgrade: websocket\r\n"
                    + "Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=\r\n\r\n";
            client.out().write(response.getBytes("UTF-8"));
            client.out().flush();
            // Sie müssen den 'Sec-WebSocket-Accept'-Wert entsprechend berechnen
            // basierend auf dem 'Sec-WebSocket-Key', den der Client sendet
        }
    }
    public record Client(Socket socket, BufferedReader in, OutputStream out) {}
}
