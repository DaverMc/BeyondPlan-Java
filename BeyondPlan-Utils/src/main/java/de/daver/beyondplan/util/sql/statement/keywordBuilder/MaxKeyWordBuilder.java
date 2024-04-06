package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.MaxNode;
import de.daver.beyondplan.util.sql.statement.node.SelectTarget;

public interface MaxKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB MAX(SelectTarget target) {
        keyWords().add(new MaxNode(target));
        return (KWB) this;
    }

}
