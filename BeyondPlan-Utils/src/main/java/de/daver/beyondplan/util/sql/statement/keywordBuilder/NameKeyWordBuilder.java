package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface NameKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB name(String name) {
        keyWords().add(new Node(name));
        return (KWB) this;
    }

    record Node(String tableName) implements KeyWord {
        @Override
        public String keyword() {
            return tableName;
        }
    }
}
