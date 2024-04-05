package de.daver.beyondplan.util.sql.statement;

public enum ComparisonOperator {
    EQUAL("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    NONE("");

    private final String operator;

    ComparisonOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }
}
