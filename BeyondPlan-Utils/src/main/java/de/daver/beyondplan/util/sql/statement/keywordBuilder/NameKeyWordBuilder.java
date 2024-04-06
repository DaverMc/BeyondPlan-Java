package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.NameNode;

public interface NameKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB name(String name) {
        keyWords().add(new NameNode(name));
        return (KWB) this;
    }

}
