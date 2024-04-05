package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.ColumnType;

public class AlterStatementBuilder extends StatementBuilder {

    public AlterStatementBuilder(CreateStatementBuilder.Creatable creatable) {
        super("ALTER");
        keyWords.add(creatable);
    }

    public AlterStatementBuilder name(String name) {
        return this;
    }

    public AlterStatementBuilder addColumn(String name, ColumnType type, boolean primaryKey) {
        return this;
    }

    public AlterStatementBuilder addColumn(String name, ColumnType type) {
        return addColumn(name, type, false);
    }

    @Override
    protected void process() {

    }
}
