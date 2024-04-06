package de.daver.beyondplan.util.sql.statement.node;

public record OnNode(String table, String column) implements KeyWord {
    @Override
    public String keyword() {
        return STR."ON \{table}(\{column})";
    }
}
