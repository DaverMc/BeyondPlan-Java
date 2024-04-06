package de.daver.beyondplan.util.sql.statement.node;

public record Column(String name, ColumnType type, boolean primaryKey) implements KeyWord {

    public Column(String name, ColumnType type) {
        this(name, type, false);
    }

    public Column(String name) {
        this(name, null);
    }

    @Override
    public String keyword() {
        if(type == null) return name;
        return STR."\{name} \{type.keyword()}\{(primaryKey) ? " PRIMARY KEY" : ""}";
    }
}
