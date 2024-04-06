package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface FromKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder{

    default KWB FROM(String table) {
        keyWords().add(new Node(table));
        return (KWB) this;
    }

    record Node(String table) implements KeyWord {
        @Override
        public String keyword() {
            return STR."FROM \{table}";
        }
    }
}
