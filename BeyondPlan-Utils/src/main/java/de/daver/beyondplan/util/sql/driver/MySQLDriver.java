package de.daver.beyondplan.util.sql.driver;


import de.daver.beyondplan.util.sql.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDriver implements Driver {

    public MySQLDriver() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {

        }
    }

    @Override
    public Connection createConnection(DatabaseConfig config) throws SQLException {

        return DriverManager.getConnection("jdbc:mysql:" + config.url(), config.username(), config.password());
    }
}
