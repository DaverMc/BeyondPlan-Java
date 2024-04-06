package de.daver.beyondplan.util.sql.statement.node;

public interface SelectTarget extends KeyWord {

    SelectTarget ALL = () -> "*";

    static SelectTarget column(String columnName) {
        return () -> columnName;
    }

}
