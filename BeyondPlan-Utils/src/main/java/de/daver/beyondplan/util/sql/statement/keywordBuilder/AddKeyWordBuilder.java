package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Column;
import de.daver.beyondplan.util.sql.statement.node.ColumnType;
import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface AddKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB ADD(String name, ColumnType type, boolean primaryKey) {
        keyWords().add(new Node(new Column(name, type, primaryKey)));
        return (KWB) this;
    }

    default KWB ADD(String name, ColumnType type) {
        return ADD(name, type, false);
    }

    record Node(Column column) implements KeyWord {

        @Override
        public String keyword() {
            return STR."ADD \{column.keyword()}";
        }
    }
}
