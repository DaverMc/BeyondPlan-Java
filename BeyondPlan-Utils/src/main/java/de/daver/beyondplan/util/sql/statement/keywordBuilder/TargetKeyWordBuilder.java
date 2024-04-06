package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.SelectTarget;

public interface TargetKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB target(SelectTarget target) {
        keyWords().add(target);
        return (KWB) this;
    }

}
