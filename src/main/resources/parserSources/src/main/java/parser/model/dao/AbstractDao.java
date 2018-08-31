package parser.model.dao;

import parser.core.InsertData;
import parser.core.databaseStructure.Column;
import parser.core.databaseStructure.Table;
import parser.utils.ConnectionStringParser;
import parsermodel.DataType;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 25.10.2016.
 */
public abstract class AbstractDao {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final ConnectionStringParser mConnectionStringParser;

    private Connection mConnection;

    public AbstractDao(@Nonnull String connectionString, @Nonnull String driverClassName) throws Exception {
        Class.forName(driverClassName);
        mConnectionStringParser = ConnectionStringParser.parse(connectionString);
    }

    public void connect() throws SQLException {
        if (mConnection == null) {
            mConnection = DriverManager.getConnection(
                    createConnectionUrl(
                            mConnectionStringParser.getHost(),
                            mConnectionStringParser.getPort(),
                            mConnectionStringParser.getDatabaseName()
                    ),
                    mConnectionStringParser.getUsername(),
                    mConnectionStringParser.getPassword()
            );
            mConnection.setAutoCommit(true);
        }
    }

    public void closeConnection() throws SQLException {
        if (mConnection != null && !mConnection.isClosed()) {
            mConnection.close();
            mConnection = null;
        }
    }

    public void createTable(Table table) throws SQLException {
        ResultSet rs = mConnection.getMetaData().getTables(null, null, table.getName(), null);
        if (!rs.next()) {
            Statement statement = mConnection.createStatement();
            prepareSqlScript(createTableScript(table), statement);
            prepareSqlScript(alterTableAutoincrementPrimaryKey(table), statement);
            statement.executeBatch();
            statement.close();
        } else {
            // TODO: 18.01.2017 Add ALTER_TABLE request 
//            List<Column> newColumns = new ArrayList<>(table.getColumns().size());
//            ResultSetMetaData rsm = rs.getMetaData();
//            for (int i = 0; i < rsm.getColumnCount(); i++) {
//                for (Column column : table.getColumns()) {
//                    if (column.getName().equals(rsm.getColumnName(i))) {
//                        break;
//                    }
//                    newColumns.add(column);
//                }
//            }
//            if (!newColumns.isEmpty()) {
//                Statement statement = mConnection.createStatement();
//                prepareSqlScript(alterTableScript(table, newColumns), statement);
//                statement.executeBatch();
//                statement.close();
//            }
        }
        rs.close();
    }

    public void insert(InsertData insertData) throws SQLException {
        Statement statement = mConnection.createStatement();
        prepareSqlScript(insertScript(insertData), statement);
        statement.executeBatch();
        statement.close();
    }

    @Nonnull
    protected String getType(@Nonnull String uniType) {
        return getDatabaseType(DataType.fromName(uniType));
    }

    @Nonnull
    protected abstract String createConnectionUrl(@Nonnull String host, @Nonnull String port, @Nonnull String databaseName);

    @Nonnull
    protected abstract String[] createTableScript(@Nonnull Table table);

    @Nonnull
    protected abstract String[] alterTableAutoincrementPrimaryKey(@Nonnull Table table);

    @Nonnull
    protected abstract String[] alterTableScript(@Nonnull Table table, @Nonnull List<Column> columns);

    @Nonnull
    protected abstract String[] insertScript(@Nonnull InsertData insertData);

    @Nonnull
    protected abstract String getDatabaseType(@Nonnull DataType dataType);

    private void prepareSqlScript(@Nonnull String[] sqlCommands, @Nonnull final Statement statement) throws SQLException {
        for (String command : sqlCommands) {
            LOGGER.info(command);
            statement.addBatch(command);
        }
    }
}
