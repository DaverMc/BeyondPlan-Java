package de.daver.beyondplan.test.core;

import de.daver.beyondplan.util.ObjectTransformer;
import de.daver.beyondplan.util.sql.Database;
import de.daver.beyondplan.util.sql.DatabaseConfig;
import de.daver.beyondplan.util.sql.Result;
import de.daver.beyondplan.util.sql.Statement;
import de.daver.beyondplan.util.sql.driver.SQLiteDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public interface DatabaseTest {

    Statement CREATE_TABLE = new Statement("CREATE TABLE IF NOT EXISTS mitarbeiter (\n" +
            "    id INT PRIMARY KEY,\n" +
            "    name VARCHAR(100),\n" +
            "    abteilung VARCHAR(50),\n" +
            "    eintrittsdatum DATE\n" +
            ");");
    Statement INSERT_INTO = new Statement("INSERT INTO mitarbeiter (id, name, abteilung, eintrittsdatum) VALUES\n" +
            "(1, 'Max Mustermann', 'IT', '2021-01-10'),\n" +
            "(2, 'Erika Mustermann', 'HR', '2021-02-15'),\n" +
            "(3, 'John Doe', 'Marketing', '2021-03-01');");

    Statement UPDATE = new Statement("UPDATE mitarbeiter\n" +
            "SET abteilung = 'Finanzen'\n" +
            "WHERE id = 2;");

    Statement DELETE_FROM =  new Statement("DELETE FROM mitarbeiter\n" +
            "WHERE id = 3;");

    Statement SELECT_ALL = new Statement("SELECT * FROM mitarbeiter;\n");

    Statement SELECT_COUNT = new Statement("SELECT COUNT(*) AS AnzahlMitarbeiter FROM mitarbeiter;");

    Statement SELECT_MAX = new Statement("SELECT MAX(eintrittsdatum) AS LetzterEintritt FROM mitarbeiter;");

    Statement ALTER_TABLE = new Statement("ALTER TABLE mitarbeiter ADD gehalt DECIMAL(10,2);");

    Statement CREATE_INDEX =  new Statement("CREATE INDEX idx_abteilung ON mitarbeiter(abteilung);");

    Statement DROP_TABLE = new Statement("DROP TABLE mitarbeiter;");

    static void main(String[] args) throws Exception {
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

        db.post(CREATE_INDEX);
        db.post(INSERT_INTO);
        db.post(UPDATE);
        db.post(DELETE_FROM);

        Result res1 = db.request(SELECT_ALL);
        System.out.println(res1);
        Result res2 = db.request(SELECT_COUNT);
        System.out.println(res2.get("AnzahlMitarbeiter", ObjectTransformer.INT));
        Result res3 = db.request(SELECT_MAX);
        System.out.println(res3.get("LetzterEintritt", ObjectTransformer.STRING));

        db.post(ALTER_TABLE);
        db.post(CREATE_INDEX);
        db.post(DROP_TABLE);
    }

}
