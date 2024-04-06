package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.Entry;
import de.daver.beyondplan.util.sql.statement.node.KeyWord;

import java.util.Arrays;

public interface ValuesKeyWordBuilder <KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB VALUES(Entry...entries) {
        keyWords().add(new ValuesNode(entries));
        return (KWB) this;
    }


    record ValuesNode(Entry... entries) implements KeyWord {
        @Override
        public String keyword() {
            return STR."VALUES \{String.join(",\n", Arrays.stream(entries).map(Entry::keyword).toList())}";
        }
    }
}
