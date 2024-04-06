package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.FromKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.WhereKeyWordBuilder;

public class DeleteStatementBuilder extends StatementBuilder implements WhereKeyWordBuilder<DeleteStatementBuilder>,
                                                                        FromKeyWordBuilder<DeleteStatementBuilder> {

    public DeleteStatementBuilder() {
        super("DELETE");
    }

}
