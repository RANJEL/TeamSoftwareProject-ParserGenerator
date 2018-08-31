package parser.utils;

import javax.annotation.Nonnull;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 08.11.2016.
 * Created by Matěj Schuh [schuhmat@fit.cvut.cz] on 08.11.2016.
 */
public final class ConnectionStringParser {

    private final String mUsername;
    private final String mPassword;
    private final String mHost;
    private final String mPort;
    private final String mDatabaseName;

    private ConnectionStringParser(String username, String password, String host, String port, String databaseName) {
        mUsername = username;
        mPassword = password;
        mHost = host;
        mPort = port;
        mDatabaseName = databaseName;
    }

    public static ConnectionStringParser parse(@Nonnull String connectionString) throws IllegalArgumentException {
        try {
            String username;
            String password;
            String host;
            String port;
            String databaseName;

            username = connectionString.substring(0, connectionString.indexOf("/"));
            connectionString = connectionString.substring(connectionString.indexOf("/") + 1);

            password = connectionString.substring(0, connectionString.indexOf("@"));
            connectionString = connectionString.substring(connectionString.indexOf("@") + 1);

            host = connectionString.substring(0, connectionString.indexOf(":"));
            connectionString = connectionString.substring(connectionString.indexOf(":") + 1);

            port = connectionString.substring(0, connectionString.indexOf("/"));
            connectionString = connectionString.substring(connectionString.indexOf("/") + 1);

            databaseName = connectionString;

            return new ConnectionStringParser(username, password, host, port, databaseName);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Your connection string is in wrong format.\n"
                    + "Expected format:\n"
                    + "<username>/<password>@<host>:<port>/<database name>");
        }
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getHost() {
        return mHost;
    }

    public String getPort() {
        return mPort;
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }
}
