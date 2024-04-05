package de.daver.beyondplan.core;

import com.sun.net.httpserver.HttpExchange;
import de.daver.beyondplan.core.web.AsyncHttpHandler;
import de.daver.beyondplan.core.web.WebServer;
import de.daver.beyondplan.core.web.WebServerSocket;
import de.daver.beyondplan.util.ObjectTransformer;
import de.daver.beyondplan.util.json.JsonObject;
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


public class TestServer {

    //FIXME
    //TODO

    public static void main(String[] args) throws Exception {
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

    public static void dbTest() throws IOException, SQLException, ExecutionException, InterruptedException, ClassNotFoundException, TimeoutException {
        var config = DatabaseConfig.builder()
                .url("localhost")
                .username("admin")
                .password("1234")
                .connectionCount(5)
                .commandTimeout(1, TimeUnit.SECONDS)
                .build();

        var db = new Database(config);
        var dbFile = new File("test.db");
        db.connect(new SQLiteDriver(dbFile));

        var s1 = new Statement("CREATE TABLE IF NOT EXISTS mitarbeiter (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(100),\n" +
                "    abteilung VARCHAR(50),\n" +
                "    eintrittsdatum DATE\n" +
                ");");

        var s2 = new Statement("INSERT INTO mitarbeiter (id, name, abteilung, eintrittsdatum) VALUES\n" +
                "(1, 'Max Mustermann', 'IT', '2021-01-10'),\n" +
                "(2, 'Erika Mustermann', 'HR', '2021-02-15'),\n" +
                "(3, 'John Doe', 'Marketing', '2021-03-01');");

        var s3 = new Statement("UPDATE mitarbeiter\n" +
                "SET abteilung = 'Finanzen'\n" +
                "WHERE id = 2;");

        var s4 = new Statement("DELETE FROM mitarbeiter\n" +
                "WHERE id = 3;");

        var s5 = new Statement("SELECT * FROM mitarbeiter;\n");
        var s6 = new Statement("SELECT COUNT(*) AS AnzahlMitarbeiter FROM mitarbeiter;");
        var s7 = new Statement("SELECT MAX(eintrittsdatum) AS LetzterEintritt FROM mitarbeiter;");

        var s8 = new Statement("ALTER TABLE mitarbeiter ADD gehalt DECIMAL(10,2);");
        var s9 = new Statement("CREATE INDEX idx_abteilung ON mitarbeiter(abteilung);");
        var s10 = new Statement("DROP TABLE mitarbeiter;");

        db.post(s1);
        db.post(s2);
        db.post(s3);
        db.post(s4);

        Result res1 = db.request(s5);
        System.out.println(res1);
        Result res2 = db.request(s6);
        System.out.println(res2.get("AnzahlMitarbeiter", ObjectTransformer.INT));
        Result res3 = db.request(s7);
        System.out.println(res3.get("LetzterEintritt", ObjectTransformer.STRING));

        db.post(s8);
        db.post(s9);
        db.post(s10);
    }
}
