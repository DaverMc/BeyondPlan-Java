package de.daver.beyondplan.util.sql.statement.node;

public record CountNode(SelectTarget target) implements KeyWord {

    @Override
    public String keyword() {
        return STR."COUNT(\{target.keyword()})";
    }
}
