package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.ColumnsKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.NameKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.ValuesKeyWordBuilder;

public class InsertIntoStatementBuilder extends StatementBuilder implements NameKeyWordBuilder<InsertIntoStatementBuilder>,
                                                                            ColumnsKeyWordBuilder<InsertIntoStatementBuilder>,
                                                                            ValuesKeyWordBuilder<InsertIntoStatementBuilder> {

    public InsertIntoStatementBuilder(){
        super("INSERT INTO");
    }

}
