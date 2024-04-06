package de.daver.beyondplan.util.sql.statement;

import de.daver.beyondplan.util.sql.statement.builder.CreateStatementBuilder;
import de.daver.beyondplan.util.sql.statement.builder.StatementBuilder;
import de.daver.beyondplan.util.sql.statement.node.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementBuilderTest {

    @Test
    void createTableStatement() {
        String expectedSQL = "CREATE TABLE IF NOT EXISTS mitarbeiter (id INT PRIMARY KEY, name VARCHAR(100), abteilung VARCHAR(50), eintrittsdatum DATE);";
        String sql = StatementBuilder.create(CreateStatementBuilder.Creatable.TABLE)
                .IF(Condition.NOT_EXISTS)
                .name("mitarbeiter")
                .columns(new Column("id", ColumnType.INT, true),
                        new Column("name", ColumnType.varchar(100)),
                        new Column("abteilung", ColumnType.varchar(50)),
                        new Column("eintrittsdatum", ColumnType.DATE))
                .toString();
        assertEquals(expectedSQL, sql);
    }

    @Test
    void insertStatement() {
        String expectedSQL ="INSERT INTO mitarbeiter (id, name, abteilung, eintrittsdatum) VALUES (1, 'Max Mustermann', 'IT', '2021-01-10'),\n" +
                "(2, 'Erika Mustermann', 'HR', '2021-02-15'),\n" +
                "(3, 'John Doe', 'Marketing', '2021-03-01');";

        String actualSQL = StatementBuilder.insertInto()
                .name("mitarbeiter")
                .columns("id","name", "abteilung", "eintrittsdatum")
                .VALUES(new Entry(1, "Max Mustermann", "IT", "2021-01-10"),
                        new Entry(2, "Erika Mustermann", "HR", "2021-02-15"),
                        new Entry(3, "John Doe", "Marketing", "2021-03-01"))
                .toString();
        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void updateStatement() {
        String expectedSQL = "UPDATE mitarbeiter SET abteilung = 'Finanzen' WHERE id = 2;";

        String actualSQL = StatementBuilder.update()
                .name("mitarbeiter")
                .SET(new UpdateAction("abteilung", "Finanzen"))
                .WHERE(new Condition("id", "2"))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void deleteStatement() {
        String expectedSQL = "DELETE FROM mitarbeiter WHERE id = 3;";

        String actualSQL = StatementBuilder.delete()
                .FROM("mitarbeiter")
                .WHERE(new Condition("id", "3"))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectAllStatement() {
        String expectedSQL = "SELECT * FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .target(SelectTarget.ALL)
                .FROM("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectCountStatement() {
        String expectedSQL = "SELECT COUNT(*) AS AnzahlMitarbeiter FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .COUNT(SelectTarget.ALL)
                .AS("AnzahlMitarbeiter")
                .FROM("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void selectMaxStatement() {
        String expectedSQL = "SELECT MAX(eintrittsdatum) AS LetzterEintritt FROM mitarbeiter;";

        String actualSQL = StatementBuilder.select()
                .MAX(SelectTarget.column("eintrittsdatum"))
                .AS("LetzterEintritt")
                .FROM("mitarbeiter")
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void alterTableStatement() {
        String expectedSQL = "ALTER TABLE mitarbeiter ADD gehalt DECIMAL(10,2);";

        String actualSQL = StatementBuilder.alter(CreateStatementBuilder.Creatable.TABLE)
                .name("mitarbeiter")
                .ADD("gehalt", ColumnType.decimal(10, 2))
                .toString();

        assertEquals(expectedSQL, actualSQL);
    }

    @Test
    void createIndexStatement() {
        String expectedSQL = "CREATE INDEX idx_abteilung ON mitarbeiter(abteilung);";

        String actualSQL = StatementBuilder.create(CreateStatementBuilder.Creatable.INDEX)
                .name("idx_abteilung")
                .ON("mitarbeiter", "abteilung")
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