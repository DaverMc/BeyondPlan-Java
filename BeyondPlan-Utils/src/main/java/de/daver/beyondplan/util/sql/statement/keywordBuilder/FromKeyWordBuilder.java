package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.FromNode;

public interface FromKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder{

    default KWB FROM(String table) {
        keyWords().add(new FromNode(table));
        return (KWB) this;
    }

}
