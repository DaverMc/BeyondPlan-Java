package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.NameKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.SetKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.WhereKeyWordBuilder;


public class UpdateStatementBuilder extends StatementBuilder implements WhereKeyWordBuilder<UpdateStatementBuilder>,
        NameKeyWordBuilder<UpdateStatementBuilder>,
        SetKeyWordBuilder<UpdateStatementBuilder>
{

    public UpdateStatementBuilder(){
        super("UPDATE");
    }
}
