package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.CountNode;
import de.daver.beyondplan.util.sql.statement.node.SelectTarget;

public interface CountKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB COUNT(SelectTarget target) {
        keyWords().add(new CountNode(target));
        return (KWB) this;
    }

}
