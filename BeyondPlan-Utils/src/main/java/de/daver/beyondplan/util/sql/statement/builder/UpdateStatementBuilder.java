package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.KeyWord;

public class UpdateStatementBuilder extends StatementBuilder{

    public UpdateStatementBuilder(){
        super("UPDATE");
    }

    @Override
    protected void process() {

    }

    public UpdateStatementBuilder table(String table){
        return this;
    }

    public UpdateStatementBuilder where(KeyWord.Condition condition){
        return this;
    }

    public UpdateStatementBuilder set(UpdateAction action){
        return this;
    }

    public record UpdateAction(String columnName, String value) implements KeyWord {

        @Override
        public String keyword() {
            return STR."'\{columnName}' = '\{value}'";
        }
    }

}
