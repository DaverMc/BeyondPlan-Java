package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;
import de.daver.beyondplan.util.sql.statement.node.SelectTarget;

public interface MaxKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB MAX(SelectTarget target) {
        keyWords().add(new Node(target));
        return (KWB) this;
    }

    record Node(SelectTarget target) implements KeyWord {
        @Override
        public String keyword() {
            return STR."MAX(\{target.keyword()})";
        }
    }
}
