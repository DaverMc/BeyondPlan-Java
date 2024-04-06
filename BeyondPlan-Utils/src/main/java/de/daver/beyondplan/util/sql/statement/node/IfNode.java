package de.daver.beyondplan.util.sql.statement.node;

public record IfNode(Condition condition) implements KeyWord{
    @Override
    public String keyword() {
        return STR."IF \{condition.keyword()}";
    }
}
