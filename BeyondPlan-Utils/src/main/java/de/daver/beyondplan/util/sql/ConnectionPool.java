package de.daver.beyondplan.util.sql;

import de.daver.beyondplan.util.sql.driver.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConnectionPool {

    private final DatabaseConfig config;
    private final BlockingQueue<Connection> availableConnections;
    private final List<Connection> blockedConnections;
    private final ExecutorService executor;


    protected ConnectionPool(DatabaseConfig config) {
        this.config = config;
        this.availableConnections = new ArrayBlockingQueue<>(config.connectionCount());
        this.blockedConnections = new ArrayList<>();
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public void connect(Driver driver) throws SQLException {
        for(int i = 0; i < config.connectionCount() - blockedConnections.size(); i++) {
            var conn = driver.createConnection(config);
            availableConnections.add(conn);
        }
    }

    public void disconnect() throws SQLException{
        for(Connection connection : blockedConnections) connection.close();
        for(Connection connection : availableConnections) connection.close();
    }

    public Future<Connection> getConnection(long timeout, TimeUnit unit) {
        Callable<Connection> task = () -> {
            Connection connection = availableConnections.poll(timeout, unit);
            if (connection == null) {
                throw new SQLException("Could not obtain a database connection within " + timeout + " " + unit);
            }
            synchronized (blockedConnections) {
                blockedConnections.add(connection);
            }
            return connection;
        };
        return executor.submit(task);
    }

    public boolean releaseConnection(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection to release cannot be null");
        }
        boolean removed;
        synchronized (blockedConnections) {
            removed = blockedConnections.remove(connection);
        }
        if (removed) {
            availableConnections.add(connection);
            return true;
        } else {
            // Log an error or handle this case as needed.
            return false;
        }
    }
}
