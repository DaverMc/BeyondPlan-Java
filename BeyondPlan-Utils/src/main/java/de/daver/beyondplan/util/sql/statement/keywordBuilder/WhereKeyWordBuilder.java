package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Condition;
import de.daver.beyondplan.util.sql.statement.node.WhereNode;

public interface WhereKeyWordBuilder<SB extends KeyWordBuilder> extends KeyWordBuilder {

    default SB WHERE(Condition condition) {
        keyWords().add(new WhereNode(condition));
        return (SB) this;
    }

}
