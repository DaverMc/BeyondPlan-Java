package de.daver.beyondplan.util.sql.statement.node;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Entry(Object... values) implements KeyWord {

    @Override
    public String keyword() {
        return STR."(\{Arrays.stream(values)
                .map(obj -> obj instanceof String ? "'" + obj + "'" : obj.toString())
                .collect(Collectors.joining(", "))})";
    }
}
