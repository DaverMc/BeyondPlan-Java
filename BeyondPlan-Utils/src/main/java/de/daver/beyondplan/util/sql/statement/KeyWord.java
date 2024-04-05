package de.daver.beyondplan.util.sql.statement;

public interface KeyWord {

    String keyword();

    record Condition(String column, String value, ComparisonOperator operator) implements KeyWord {

        Condition(String column, String value) {
            this(column, value, ComparisonOperator.EQUAL);
        }

        private Condition(String condition) {
            this(null, condition, ComparisonOperator.NONE);
        }

        public static final Condition NOT_EXISTS = new Condition("NOT EXISTS");

        @Override
        public String keyword() {
            if (operator == ComparisonOperator.NONE) return value;
            return column + operator + value;
        }
    }


    record Column(String name, ColumnType type, boolean primaryKey) implements KeyWord{

        @Override
        public String keyword() {
            return STR."\{name} \{type.keyword()} \{(primaryKey) ? "PRIMARY KEY" : ""}";
        }
    }

    record Name(String tableName) implements KeyWord{
        @Override
        public String keyword() {
            return tableName;
        }
    }

    record Command(String command) implements KeyWord{

        @Override
        public String keyword() {
            return command;
        }
    }
}
