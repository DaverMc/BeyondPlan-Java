package de.daver.beyondplan.util.sql.statement.node;

public record FromNode(String table) implements KeyWord{
    @Override
    public String keyword() {
        return STR."FROM \{table}";
    }
}
