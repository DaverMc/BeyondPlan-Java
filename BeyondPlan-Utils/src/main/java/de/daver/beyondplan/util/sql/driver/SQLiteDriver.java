package de.daver.beyondplan.util.sql.driver;


import de.daver.beyondplan.util.sql.DatabaseConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDriver implements Driver {

    private final File file;

    public SQLiteDriver(File file) throws IOException, ClassNotFoundException {
        if(!file.getName().endsWith(".db")) throw new IOException("File isn't in the right format. Should be a '.db' file.");
        this.file = file;

    }

    @Override
    public Connection createConnection(DatabaseConfig config) throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("Driver 'org.sqlite.JDBC' not Found!");
        }
        return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath(), config.username(), config.password());
    }
}
