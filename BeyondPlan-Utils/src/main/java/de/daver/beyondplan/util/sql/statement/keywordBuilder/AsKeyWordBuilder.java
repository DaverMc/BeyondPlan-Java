package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.AsNode;

public interface AsKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB AS(String name) {
        keyWords().add(new AsNode(name));
        return (KWB) this;
    }

}
