package de.daver.beyondplan.util.sql.statement.node;

import java.util.Arrays;

public record ValuesNode(Entry... entries) implements KeyWord {
    @Override
    public String keyword() {
        return STR."VALUES \{String.join(",\n", Arrays.stream(entries).map(Entry::keyword).toList())}";
    }
}
