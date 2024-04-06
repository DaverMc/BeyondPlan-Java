package de.daver.beyondplan.util.sql.statement.node;

public record MaxNode(SelectTarget target) implements KeyWord {
    @Override
    public String keyword() {
        return STR."MAX(\{target.keyword()})";
    }
}
