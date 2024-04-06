package de.daver.beyondplan.util.sql.statement.node;

public record AsNode(String name) implements KeyWord{
    @Override
    public String keyword() {
        return STR."AS \{name}";
    }
}
