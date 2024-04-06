package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.NameKeyWordBuilder;

public class DropStatementBuilder extends StatementBuilder implements NameKeyWordBuilder<DropStatementBuilder> {

    public DropStatementBuilder(CreateStatementBuilder.Creatable creatable) {
        super("DROP");
        keyWords.add(creatable);
    }
}
