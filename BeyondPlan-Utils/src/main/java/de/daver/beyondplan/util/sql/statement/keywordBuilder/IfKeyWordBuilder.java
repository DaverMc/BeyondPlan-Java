package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Condition;
import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface IfKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB IF(Condition condition) {
        keyWords().add(new Node(condition));
        return (KWB) this;
    }

    record Node(Condition condition) implements KeyWord {
        @Override
        public String keyword() {
            return STR."IF \{condition.keyword()}";
        }
    }
}
