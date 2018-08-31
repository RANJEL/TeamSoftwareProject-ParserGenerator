package parser.core;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 30.10.2016.
 */
public class InsertData {

    private String mTableName;
    private Map<String,Object> mData;

    public InsertData(String tableName, String column, Object data) {
        mTableName = tableName;
        mData = new HashMap<>();
        mData.put(column, data);
    }
    
    public void addColumn(String column, Object data)
    {
        mData.put(column, data);
    }

    public String getTableName() {
        return mTableName;
    }

    public Map<String, Object> getData() {
        return mData;
    }

    public void print()
    {
        System.out.println("Table name=" + mTableName);
        for (Map.Entry<String, Object> entry : mData.entrySet())
        {
            System.out.println("    " + entry.getKey() + "/" + entry.getValue());
        }
    }
}