package de.daver.beyondplan.util.sql.statement;

public interface ColumnType extends KeyWord{

    ColumnType DATE = () -> "DATE";
    ColumnType INT = () -> "INT";

    static ColumnType varchar(int size){
        return new VarCharColumnType(size);
    }

    static ColumnType decimal(int precision, int scale){
        return new DecimalColumnType(precision, scale);
    }

    record VarCharColumnType(int size) implements ColumnType{

        @Override
        public String keyword() {
            return STR."varchar(\{size})";
        }
    }

    record DecimalColumnType(int precision, int scale) implements ColumnType{

        @Override
        public String keyword() {
            return STR."DECIMAL(\{precision},\{scale})";
        }
    }

}
