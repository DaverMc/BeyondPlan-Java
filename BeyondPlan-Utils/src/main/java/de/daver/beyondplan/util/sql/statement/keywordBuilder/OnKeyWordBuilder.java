package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.OnNode;

public interface OnKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB ON(String table, String column) {
        keyWords().add(new OnNode(table, column));
        return (KWB) this;
    }

}
