package de.daver.beyondplan.test.core;

import com.sun.net.httpserver.HttpExchange;
import de.daver.beyondplan.core.web.AsyncHttpHandler;
import de.daver.beyondplan.core.web.WebServer;
import de.daver.beyondplan.core.web.WebServerSocket;
import de.daver.beyondplan.util.ObjectTransformer;
import de.daver.beyondplan.util.sql.Database;
import de.daver.beyondplan.util.sql.DatabaseConfig;
import de.daver.beyondplan.util.sql.Result;
import de.daver.beyondplan.util.sql.Statement;
import de.daver.beyondplan.util.sql.driver.SQLiteDriver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class WebServerTest {

    //FIXME
    //TODO

    public static void main(String[] args) throws Exception {
        httpTest();
        testSocket();
    }

    public static void httpTest() throws Exception {
        var server = new WebServer(8080);
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
    }

    public static void testSocket() throws Exception {
        WebServerSocket socket = new WebServerSocket(1337);
        socket.start();
    }
}
