package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface OnKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB ON(String table, String column) {
        keyWords().add(new Node(table, column));
        return (KWB) this;
    }

    record Node(String table, String column) implements KeyWord {
        @Override
        public String keyword() {
            return STR."ON \{table}(\{column})";
        }
    }
}
