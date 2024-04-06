package de.daver.beyondplan.util.sql.statement.node;

public record Command(String command) implements KeyWord {

    @Override
    public String keyword() {
        return command;
    }
}
