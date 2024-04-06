package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.SetNode;
import de.daver.beyondplan.util.sql.statement.node.UpdateAction;

public interface SetKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB SET(UpdateAction... action) {
        keyWords().add(new SetNode(action));
        return (KWB) this;
    }

}
