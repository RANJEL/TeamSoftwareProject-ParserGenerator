package parser.model.dao.daoImplementations;

import parser.core.databaseStructure.Column;
import parser.core.InsertData;
import parser.core.databaseStructure.Table;
import parser.model.dao.AbstractDao;
import parsermodel.DataType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 25.10.2016.
 */
public class MysqlDao extends AbstractDao {

    public MysqlDao(@Nonnull String connectionString, @Nonnull String driverClassName) throws Exception {
        super(connectionString, driverClassName);
    }

    @Nonnull
    @Override
    protected String createConnectionUrl(@Nonnull String host, @Nonnull String port, @Nonnull String databaseName) {
        return "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
    }

    @Nonnull
    @Override
    protected String[] createTableScript(@Nonnull Table table) {
        String script = "CREATE TABLE " + table.getName() + " (\n  "
                + table.getIdentifier().getName() + " " + getType(table.getIdentifier().getDataType()) + " NOT NULL";
        for (Column column : table.getColumns()) {
            script += ",\n  " + column.getName() + " " + getType(column.getDataType());
        }
        script += "\n)";
        return new String[]{script};
    }

    @Nonnull
    @Override
    protected String[] alterTableAutoincrementPrimaryKey(@Nonnull Table table) {
        String[] scripts = new String[2];
        scripts[0] = "ALTER TABLE " + table.getName() + "\n" +
                "   ADD CONSTRAINT " + table.getName() + "_PK PRIMARY KEY (" + table.getIdentifier().getName() + ")";
        scripts[1] = "ALTER TABLE " + table.getName() + " MODIFY COLUMN " + table.getIdentifier().getName() + " " + getType(table.getIdentifier().getDataType()) + " NOT NULL AUTO_INCREMENT";
        return scripts;
    }

    @Nonnull
    @Override
    protected String[] alterTableScript(@Nonnull Table table, @Nonnull List<Column> columns) {
        String[] scripts = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            scripts[i] = "ALTER TABLE " + table.getName() + "\n" +
                    "   ADD " + columns.get(i).getName() + " " + getType(columns.get(i).getDataType());
        }
        return scripts;
    }

    @Nonnull
    @Override
    protected String[] insertScript(@Nonnull InsertData insertData) {
        String firstPart = "INSERT INTO " + insertData.getTableName() + " (";
        String secondPart = "VALUES (";
        int count = insertData.getData().size();
        for (Map.Entry<String, Object> entry : insertData.getData().entrySet()) {
            firstPart += entry.getKey();
            secondPart += "\'" + entry.getValue() + "\'";
            if (--count != 0) {
                firstPart += ", ";
                secondPart += ", ";
            }
        }
        firstPart += ") ";
        secondPart += ");";
        return new String[]{firstPart + secondPart};
    }

    @Override
    protected String getDatabaseType(@Nonnull DataType dataType) {
        return dataType.getMysqlType();
    }
}
