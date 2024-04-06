package de.daver.beyondplan.util.sql.statement.keywordBuilder;

import de.daver.beyondplan.util.sql.statement.node.KeyWord;
import de.daver.beyondplan.util.sql.statement.node.UpdateAction;

import java.util.Arrays;

public interface SetKeyWordBuilder<KWB extends KeyWordBuilder> extends KeyWordBuilder {

    default KWB SET(UpdateAction... action) {
        keyWords().add(new Node(action));
        return (KWB) this;
    }

    record Node(UpdateAction... actions) implements KeyWord {

        @Override
        public String keyword() {
            return STR."SET \{String.join(",", Arrays.stream(actions).map(UpdateAction::keyword).toList())}";
        }
    }
}
