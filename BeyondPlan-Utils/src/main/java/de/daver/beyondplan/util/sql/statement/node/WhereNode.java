package de.daver.beyondplan.util.sql.statement.node;


public record WhereNode(Condition condition) implements KeyWord {
    @Override
    public String keyword() {
        return STR."WHERE \{condition.keyword()}";
    }
}
