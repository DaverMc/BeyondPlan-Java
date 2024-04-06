package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Condition;
import de.daver.beyondplan.util.sql.statement.node.IfNode;

public interface IfKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB IF(Condition condition) {
        keyWords().add(new IfNode(condition));
        return (KWB) this;
    }

}
