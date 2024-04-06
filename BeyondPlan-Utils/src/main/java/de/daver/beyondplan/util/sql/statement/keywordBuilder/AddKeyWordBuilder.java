package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.AddNode;
import de.daver.beyondplan.util.sql.statement.node.Column;
import de.daver.beyondplan.util.sql.statement.node.ColumnType;

public interface AddKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB ADD(String name, ColumnType type, boolean primaryKey) {
        keyWords().add(new AddNode(new Column(name, type, primaryKey)));
        return (KWB) this;
    }

    default KWB ADD(String name, ColumnType type) {
        return ADD(name, type, false);
    }

}
