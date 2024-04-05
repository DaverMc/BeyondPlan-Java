package de.daver.beyondplan.util.sql.statement.node;

import de.daver.beyondplan.util.sql.statement.KeyWord;

import java.util.List;

public record ColumnsNode(List<KeyWord.Column> columnList) implements KeyWord{

    @Override
    public String keyword() {
        return STR."(\{String.join(",\n", columnList.stream().map(Column::keyword).toList())})";
    }
}
