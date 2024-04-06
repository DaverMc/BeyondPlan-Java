package de.daver.beyondplan.util.sql.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Statement {

    public final String command;

    public Statement(String sql) {
        this.command = sql;
    }

    public PreparedStatement prepare(Connection connection) throws SQLException {
        var prepStatement = connection.prepareStatement(this.command);
        return prepStatement;
    }

}
