package de.daver.beyondplan.util.sql.statement.builder;

public class InsertIntoStatementBuilder extends StatementBuilder{

    public InsertIntoStatementBuilder(){
        super("INSERT INTO");
    }

    @Override
    protected void process() {

    }

    public InsertIntoStatementBuilder table(String table){
        return this;
    }

    public InsertIntoStatementBuilder column(String column){
        return this;
    }

    public InsertIntoStatementBuilder value(String...values){
        return this;
    }

}
