package parser.core;

import parser.core.databaseStructure.Column;
import parser.core.databaseStructure.Table;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 16.01.2017.
 */
public class ScriptsData {

    private final Map<String, Table> mTables = new HashMap<>();
    private final Map<String, InsertData> mInsertDataMap = new HashMap<>();
    private final List<InsertData> mInserts = new ArrayList<>();

    public void addTableOrColumn(@Nonnull String tableName, @Nonnull String columnName, @Nonnull String dataType) {
        Table currentTable = mTables.getOrDefault(tableName, new Table(tableName));
        currentTable.getColumns().add(new Column(columnName, dataType));
        mTables.putIfAbsent(tableName, currentTable);
    }

    public void addColumnToInsertDataMap(@Nonnull String tableName, @Nonnull String columnName, @Nullable String data) {
        InsertData insertData = mInsertDataMap.get(tableName);
        if (insertData == null) {
            insertData = new InsertData(tableName, columnName, data);
            mInsertDataMap.put(tableName, insertData);
        } else {
            insertData.addColumn(columnName, data);
        }
    }

    public void addInsertsFromMapToList() {
        for (InsertData data : mInsertDataMap.values()) {
            mInserts.add(data);
        }
        mInsertDataMap.clear();
    }

    public Collection<Table> getTables() {
        return mTables.values();
    }

    public Collection<InsertData> getInsertData() {
        return mInserts;
    }
}
