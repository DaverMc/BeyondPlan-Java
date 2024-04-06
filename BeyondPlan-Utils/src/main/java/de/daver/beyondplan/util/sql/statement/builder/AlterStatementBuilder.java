package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.AddKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.NameKeyWordBuilder;

public class AlterStatementBuilder extends StatementBuilder implements NameKeyWordBuilder<AlterStatementBuilder>,
                                                                        AddKeyWordBuilder<AlterStatementBuilder> {

    public AlterStatementBuilder(CreateStatementBuilder.Creatable creatable) {
        super("ALTER");
        keyWords.add(creatable);
    }
}
