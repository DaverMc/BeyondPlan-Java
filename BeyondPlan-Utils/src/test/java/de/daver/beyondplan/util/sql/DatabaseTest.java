package de.daver.beyondplan.util.sql;

import de.daver.beyondplan.util.ObjectTransformer;
import de.daver.beyondplan.util.sql.driver.SQLiteDriver;
import de.daver.beyondplan.util.sql.statement.ColumnType;
import de.daver.beyondplan.util.sql.statement.KeyWord;
import de.daver.beyondplan.util.sql.statement.Statement;
import de.daver.beyondplan.util.sql.statement.builder.CreateStatementBuilder;
import de.daver.beyondplan.util.sql.statement.builder.StatementBuilder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTest {

    //region SQL-Statements
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

    //endregion

    Database database;

    @Test
    @Order(1)
    void buildDatabaseConnection() {
        var config = DatabaseConfig.builder()
                .url("localhost")
                .username("admin")
                .password("1234")
                .connectionCount(5)
                .commandTimeout(1, TimeUnit.SECONDS)
                .build();

        database = new Database(config);
        var dbFile = new File("test.db");
        assertDoesNotThrow(() -> database.connect(new SQLiteDriver(dbFile)));
    }

    @Test
    @Order(2)
    void createTable() {
        Statement statement = StatementBuilder.create(CreateStatementBuilder.Creatable.TABLE)
                .ifCondition(KeyWord.Condition.NOT_EXISTS)
                .name("mitarbeiter")
                .column("id", ColumnType.INT, true)
                .column("name", ColumnType.varchar(100))
                .column("abteilung", ColumnType.varchar(50))
                .column("eintrittsdatum", ColumnType.DATE)
                .build();
        assertDoesNotThrow(() -> database.post(statement));
    }

    @Test
    @Order(3)
    void insertInto() {
        assertDoesNotThrow(() -> database.post(INSERT_INTO));
    }

    @Test
    @Order(4)
    void update() {
        assertDoesNotThrow(() -> database.post(UPDATE));
    }

    @Test
    @Order(5)
    void deleteFrom() {
        assertDoesNotThrow(() -> database.post(DELETE_FROM));
    }

    @Test
    @Order(6)
    void selectAll() {
        Result result = assertDoesNotThrow(() -> database.request(SELECT_ALL));
        var list = result.getColumn("name", ObjectTransformer.STRING);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    @Order(7)
    void selectCountAsync() {
        var resultFuture = assertDoesNotThrow(() -> database.requestAsync(SELECT_COUNT));
        var result = assertDoesNotThrow(() -> resultFuture.get(1, TimeUnit.SECONDS));
        var count = result.get("AnzahlMitarbeiter", ObjectTransformer.INT);
        assertEquals(2, count);
    }

    @Test
    @Order(8)
    void selectMax() {
        Result result = assertDoesNotThrow(() -> database.request(SELECT_MAX));
        var date = result.get("LetzterEintritt", ObjectTransformer.STRING);
        assertEquals("2021-02-15", date);
    }

    @Test
    @Order(9)
    void alterTable() {
        assertDoesNotThrow(() -> database.postAsync(ALTER_TABLE));
    }

    @Test
    @Order(10)
    void createIndex() {
        assertDoesNotThrow(() -> database.postAsync(CREATE_INDEX));
    }

    @Test
    @Order(11)
    void dropTable() {
        assertDoesNotThrow(() -> database.post(DROP_TABLE));
    }

    @Test
    @Order(12)
    void closeConnection() {
        assertDoesNotThrow(() -> database.disconnect());
    }

}