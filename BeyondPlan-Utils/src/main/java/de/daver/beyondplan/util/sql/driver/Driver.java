package de.daver.beyondplan.util.sql.driver;

import de.daver.beyondplan.util.sql.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public interface Driver {

    Connection createConnection(DatabaseConfig config) throws SQLException;

}
