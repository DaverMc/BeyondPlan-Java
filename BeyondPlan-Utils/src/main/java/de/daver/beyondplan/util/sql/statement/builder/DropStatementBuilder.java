package de.daver.beyondplan.util.sql.statement.builder;

public class DropStatementBuilder extends StatementBuilder {

    public DropStatementBuilder(CreateStatementBuilder.Creatable creatable) {
        super("DROP");
        keyWords.add(creatable);
    }

    public DropStatementBuilder name(String tableName) {
        return this;
    }

    @Override
    protected void process() {

    }
}
