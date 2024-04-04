package de.daver.beyondplan.util.sql;

import de.daver.beyondplan.util.sql.driver.Driver;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Database {

    private final DatabaseConfig config;
    private final ExecutorService virtualThreadPool;
    private final ConnectionPool connectionPool;

    public Database(DatabaseConfig config) {
        this.config = config;
        this.virtualThreadPool = Executors.newVirtualThreadPerTaskExecutor();
        this.connectionPool = new ConnectionPool(config);
    }

    public void connect(Driver driver) throws SQLException{
        this.connectionPool.connect(driver);
    }

    public void disconnect() throws SQLException {
        this.connectionPool.disconnect();
    }

    public void post(Statement statement) throws SQLException, ExecutionException, InterruptedException {
        executeStatement(statement, PreparedStatement::execute);
    }

    public Future<Boolean> postAsync(Statement statement) {
        var future = new CompletableFuture<Boolean>();
        virtualThreadPool.submit(() -> {
            try {
                post(statement);
                future.complete(true);
            } catch (Exception exception) {
                future.completeExceptionally(exception);
            }
        });
        return future;
    }

    public Result request(Statement statement) throws SQLException, ExecutionException, InterruptedException {
        var resultSet = executeStatement(statement, PreparedStatement::executeQuery);
        return (resultSet == null)? null : Result.ofResultSet(resultSet);
    }

    public Future<Result> requestAsync(Statement statement) {
        var future = new CompletableFuture<Result>();
        virtualThreadPool.submit(() -> {
            try {
                var result = request(statement);
                if (result == null) future.completeExceptionally(new NullPointerException());
                future.complete(result);
            } catch (Exception exception) {
                future.completeExceptionally(exception);
            }
        });
        return future;
    }

    public List<Result> executeScript(File file){
        return new ArrayList<>(); //TODO Ein vollständiges Script soll eingeladen werden
    }

    private <V> V executeStatement(Statement statement, SQlFunction<V> function) throws SQLException, ExecutionException, InterruptedException {
        var future = connectionPool.getConnection(config.commandTimeout(), config.commandTimeoutUnit());
        var connection = future.get();
        var prepStatement = statement.prepare(connection);
        var result = function.execute(prepStatement);
        connectionPool.releaseConnection(connection);
        return result;
    }

    interface SQlFunction<V> {
        V execute(PreparedStatement statement) throws SQLException;
    }
}
