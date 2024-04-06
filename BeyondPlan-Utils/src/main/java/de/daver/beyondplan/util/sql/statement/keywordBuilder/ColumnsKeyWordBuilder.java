package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Column;
import de.daver.beyondplan.util.sql.statement.node.ColumnsNode;

import java.util.Arrays;

public interface ColumnsKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB columns(Column... columns) {
        keyWords().add(new ColumnsNode(columns));
        return (KWB) this;
    }

    default KWB columns(String... columnNames) {
        return columns(Arrays.stream(columnNames).map(Column::new).toArray(Column[]::new));
    }

}
