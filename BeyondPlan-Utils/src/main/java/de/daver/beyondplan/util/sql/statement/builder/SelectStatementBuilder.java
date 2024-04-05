package de.daver.beyondplan.util.sql.statement.builder;


import de.daver.beyondplan.util.sql.statement.KeyWord;

public class SelectStatementBuilder extends StatementBuilder {

    public SelectStatementBuilder() {
        super("SELECT");
    }

    @Override
    protected void process() {

    }

    public SelectStatementBuilder target(SelectTarget target) {
        return this;
    }

    public SelectStatementBuilder count(SelectTarget target) {
        return this;
    }

    public SelectStatementBuilder max(SelectTarget target) {
        return this;
    }

    public SelectStatementBuilder as(String columName) {
        return this;
    }

    public interface SelectTarget extends KeyWord {

        SelectTarget ALL = () -> "*";

        static SelectTarget column(String columnName) {
            return () -> columnName;
        }

    }
    public  SelectStatementBuilder from(String table) {
        return this;
    }

}
