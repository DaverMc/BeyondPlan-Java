package de.daver.beyondplan.util.sql.statement.node;

public record AddNode(Column column) implements KeyWord {

    @Override
    public String keyword() {
        return STR."ADD \{column.keyword()}";
    }
}
