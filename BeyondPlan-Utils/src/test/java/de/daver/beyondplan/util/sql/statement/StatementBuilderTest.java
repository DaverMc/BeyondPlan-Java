package de.daver.beyondplan.util.sql.statement;

import de.daver.beyondplan.util.sql.Database;
import de.daver.beyondplan.util.sql.statement.builder.CreateStatementBuilder;
import de.daver.beyondplan.util.sql.statement.builder.SelectStatementBuilder;
import de.daver.beyondplan.util.sql.statement.builder.StatementBuilder;
import de.daver.beyondplan.util.sql.statement.builder.UpdateStatementBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementBuilderTest {

    @Test
    void createTableStatement() {
        String expectedSQL = "CREATE TABLE IF NOT EXISTS mitarbeiter (\n" +
                "    id INT PRIMARY KEY,\n" +
                "    name VARCHAR(100),\n" +
                "    abteilung VARCHAR(50),\n" +
                "    eintrittsdatum DATE\n" +
                ");";
        String sql = StatementBuilder.create(CreateStatementBuilder.Creatable.TABLE)
                .ifCondition(KeyWord.Condition.NOT_EXISTS)
                .name("mitarbeiter")
                .column("id", ColumnType.INT, true)
                .column("name", ColumnType.varchar(100))
                .column("abteilung", ColumnType.varchar(50))
                .column("eintrittdatum", ColumnType.DATE)
                .toString();
        assertEquals(expectedSQL, sql);
    }

    @Test
    void insertStatement() {
        String expectedSQL = "INSERT INTO mitarbeiter (id, name, abteilung, eintrittsdatum) VALUES\n" +
                "(1, 'Max Mustermann', 'IT', '2021-01-10'),\n" +
                "(2, 'Erika Mustermann', 'HR', '2021-02-15'),\n" +
                "(3, 'John Doe', 'Marketing', '2021-03-01');";

        String actualSQL = StatementBuilder.insertInto()
                .table("mitarbeiter")
                .column("id")
                .column("name")
                .column("abteilung")
                .column("eintrittsdatum")
                .value("1", "Max Mustermann", "IT", "2021-01-10")
                .value("2", "Erika Mustermann", "HR", "2021-02-15")
                .value("3", "John Doe", "Marketing", "2021-03-01")
                .toString();
        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void updateStatement() {
        String expectedSQL = "UPDATE mitarbeiter\n" +
                "SET abteilung = 'Finanzen'\n" +
                "WHERE id = 2;";

        String actualSQL = StatementBuilder.update()
                .table("mitarbeiter")
                .set(new UpdateStatementBuilder.UpdateAction("abteilung", "Finanzen"))
                .where(new KeyWord.Condition("id", "2"))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void deleteStatement() {
        String expectedSQL = "DELETE FROM mitarbeiter WHERE id = 3;";

        String actualSQL = StatementBuilder.delete()
                .from("mitarbeiter")
                .where(new KeyWord.Condition("id", "3"))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectAllStatement() {
        String expectedSQL = "SELECT * FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .target(SelectStatementBuilder.SelectTarget.ALL)
                .from("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectCountStatement() {
        String expectedSQL = "SELECT COUNT(*) AS AnzahlMitarbeiter FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .count(SelectStatementBuilder.SelectTarget.ALL)
                .as("AnzahlMitarbeiter")
                .from("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectMaxStatement() {
        String expectedSQL = "SELECT MAX(eintrittsdatum) AS LetzterEintritt FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .max(SelectStatementBuilder.SelectTarget.column("eintrittsdatum"))
                .as("LetzterEintritt")
                .from("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void alterTableStatement() {
        String expectedSQL = "ALTER TABLE mitarbeiter ADD gehalt DECIMAL(10,2);";

        String actualSQL = StatementBuilder.alter(CreateStatementBuilder.Creatable.TABLE)
                .name("mitarbeiter")
                .addColumn("gehalt", ColumnType.decimal(10, 2))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void createIndexStatement() {
        String expectedSQL = "CREATE INDEX idx_abteilung ON mitarbeiter(abteilung);";

        String actualSQL = StatementBuilder.create(CreateStatementBuilder.Creatable.INDEX)
                .name("idx_abteilung")
                .on("mitarbeiter", "abteilung")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void dropTableStatement() {
        String expectedSQL = "DROP TABLE mitarbeiter;";

        String actualSQL = StatementBuilder.drop(CreateStatementBuilder.Creatable.TABLE)
                .name("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }
}