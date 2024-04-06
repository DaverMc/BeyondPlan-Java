package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Condition;
import de.daver.beyondplan.util.sql.statement.node.KeyWord;

public interface WhereKeyWordBuilder<SB extends KeyWordBuilder> extends KeyWordBuilder {

    default SB WHERE(Condition condition) {
        keyWords().add(new WhereNode(condition));
        return (SB) this;
    }

    record WhereNode(Condition condition) implements KeyWord {
        @Override
        public String keyword() {
            return STR."WHERE \{condition.keyword()}";
        }
    }
}
