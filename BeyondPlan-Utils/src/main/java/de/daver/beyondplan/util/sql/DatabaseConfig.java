package de.daver.beyondplan.util.sql;

import java.util.concurrent.TimeUnit;

public record DatabaseConfig(String url,
                             String username,
                             String password,
                             int connectionCount,
                             long commandTimeout,
                             TimeUnit commandTimeoutUnit) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String url;
        private String username;
        private String password;
        private int connectionCount;
        private long commandTimeout;
        private TimeUnit commandTimeoutUnit;

        private Builder() {
            this.url = "localhost";
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(url, username, password, connectionCount, commandTimeout, commandTimeoutUnit);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder connectionCount(int connections){
            this.connectionCount = connections;
            return this;
        }

        public Builder commandTimeout(long timeout, TimeUnit units) {
            this.commandTimeout = timeout;
            this.commandTimeoutUnit = units;
            return this;
        }

    }

}
