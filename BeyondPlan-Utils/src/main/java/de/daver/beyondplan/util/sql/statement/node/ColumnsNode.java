package de.daver.beyondplan.util.sql.statement.node;

import java.util.Arrays;

public record ColumnsNode(Column[] columnList) implements KeyWord{

    @Override
    public String keyword() {
        return STR."(\{String.join(", ",
                Arrays.stream(columnList)
                        .map(Column::keyword)
                        .toList())})";
    }
}
