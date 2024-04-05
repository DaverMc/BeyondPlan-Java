package de.daver.beyondplan.util.sql.statement.node;

import de.daver.beyondplan.util.sql.statement.KeyWord;

public record IfNode(KeyWord.Condition condition) implements KeyWord{
    @Override
    public String keyword() {
        return STR."IF \{condition.keyword()}";
    }
}
