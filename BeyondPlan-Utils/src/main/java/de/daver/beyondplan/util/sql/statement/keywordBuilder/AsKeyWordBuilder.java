package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface AsKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB AS(String name) {
        keyWords().add(new Node(name));
        return (KWB) this;
    }

    record Node(String name) implements KeyWord {
        @Override
        public String keyword() {
            return STR."AS \{name}";
        }
    }
}
