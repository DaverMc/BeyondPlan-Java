package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.KeyWord;

public class DeleteStatementBuilder extends StatementBuilder {

    public DeleteStatementBuilder() {
        super("DELETE");
    }

    @Override
    protected void process() {

    }

    public DeleteStatementBuilder from(String table) {
        return this;
    }

    public DeleteStatementBuilder where(KeyWord.Condition condition){
        return this;
    }

}
