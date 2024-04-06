package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Entry;
import de.daver.beyondplan.util.sql.statement.node.ValuesNode;

public interface ValuesKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB VALUES(Entry...entries) {
        keyWords().add(new ValuesNode(entries));
        return (KWB) this;
    }


}
