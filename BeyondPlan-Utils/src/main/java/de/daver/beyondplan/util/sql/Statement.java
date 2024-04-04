package de.daver.beyondplan.util.sql;

import de.daver.beyondplan.util.MultiHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Statement {

    public final String command;
    public final MultiHashMap<SQLType, Argument> arguments;

    public Statement(String sql) {
        this.command = sql;
        this.arguments = new MultiHashMap<>();
    }

    public Statement setArg(SQLType type, int index, Object value) {
        arguments.add(type, new Argument(index, value));
        return this;
    }

    public Statement setArgs(SQLType type, Argument...arguments) {
        this.arguments.addAll(type, arguments);
        return this;
    }

    protected PreparedStatement prepare(Connection connection) throws SQLException {
        var prepStatement = connection.prepareStatement(this.command);
        //TODO Set Arguments
        return prepStatement;
    }

    public record Argument(int index, Object value){}

}
