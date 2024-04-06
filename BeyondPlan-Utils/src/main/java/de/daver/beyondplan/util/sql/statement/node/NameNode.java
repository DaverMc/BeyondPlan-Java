package de.daver.beyondplan.util.sql.statement.node;

public record NameNode(String tableName) implements KeyWord {
    @Override
    public String keyword() {
        return tableName;
    }
}
