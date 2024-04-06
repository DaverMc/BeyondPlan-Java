package de.daver.beyondplan.util.sql.statement.node;

import java.util.Arrays;

public record SetNode(UpdateAction... actions) implements KeyWord {

    @Override
    public String keyword() {
        return STR."SET \{String.join(",", Arrays.stream(actions).map(UpdateAction::keyword).toList())}";
    }
}
