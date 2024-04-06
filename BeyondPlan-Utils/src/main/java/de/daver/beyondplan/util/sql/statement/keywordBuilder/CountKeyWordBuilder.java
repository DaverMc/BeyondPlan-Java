package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;
import de.daver.beyondplan.util.sql.statement.node.SelectTarget;

public interface CountKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB COUNT(SelectTarget target) {
        keyWords().add(new Node(target));
        return (KWB) this;
    }

    record Node(SelectTarget target) implements KeyWord {

        @Override
        public String keyword() {
            return STR."COUNT(\{target.keyword()})";
        }
    }
}
