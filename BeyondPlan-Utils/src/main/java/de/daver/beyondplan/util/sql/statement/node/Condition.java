package de.daver.beyondplan.util.sql.statement.node;

public record Condition(String column, String value, ComparisonOperator operator) implements KeyWord {

    public Condition(String column, String value) {
        this(column, value, ComparisonOperator.EQUAL);
    }

    private Condition(String condition) {
        this(null, condition, ComparisonOperator.NONE);
    }

    public static final de.daver.beyondplan.util.sql.statement.node.Condition NOT_EXISTS = new de.daver.beyondplan.util.sql.statement.node.Condition("NOT EXISTS");

    @Override
    public String keyword() {
        if (operator == ComparisonOperator.NONE) return value;
        return STR."\{column} \{operator} \{value}";
    }
}
