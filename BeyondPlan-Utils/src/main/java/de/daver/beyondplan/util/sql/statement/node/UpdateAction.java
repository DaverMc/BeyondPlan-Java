package de.daver.beyondplan.util.sql.statement.node;

public record UpdateAction(String columnName, String value) implements KeyWord {

    @Override
    public String keyword() {
        return STR."\{columnName} = '\{value}'";
    }
}
