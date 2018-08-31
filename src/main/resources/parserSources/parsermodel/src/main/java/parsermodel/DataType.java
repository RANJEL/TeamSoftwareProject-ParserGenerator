package parsermodel;

import java.util.HashSet;
import java.util.Set;

/**
 * Databázový datový typ a jeho varianty pro jednotlivé databáze.
 *
 * @author Luis
 */
public enum DataType {
    //Template:
    // <GUI type name>, <Oracle type>, <MySQL type>, <Postgres type>
    INTEGER("integer", "INTEGER", "INT", "INT"),
    DOUBLE("double", "NUMERIC", "DOUBLE", "DOUBLE PRECISION"),
    TEXT("text", "VARCHAR(250)", "VARCHAR(250)", "VARCHAR"),
    DATE("date", "DATE", "DATE", "DATE"),
    TIME("time", "TIME", "TIME", "TIME");

    private final String mTypeName;
    private final String mOracleType;
    private final String mMysqlType;
    private final String mPostresqlType;

    DataType(String typeName, String oracleType, String mysqlType, String postgresType) {
        mTypeName = typeName;
        mOracleType = oracleType;
        mMysqlType = mysqlType;
        mPostresqlType = postgresType;
    }

    /**
     * Vrátí všechny podporované typy jako množinu stringů.
     *
     * @return
     */
    public static Set<String> getTypeNames() {
        Set<String> types = new HashSet<>();
        for (DataType type : DataType.values()) {
            types.add(type.getTypeName());
        }
        return types;
    }

    /**
     * Vytvoří enum instanci ze zadaného jména
     */
    public static DataType fromName(String requestedName) {
        for (DataType type : DataType.values()) {
            if (type.getTypeName().equalsIgnoreCase(requestedName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown data type: " + requestedName);
    }

    public String getTypeName() {
        return mTypeName;
    }

    public String getOracleType() {
        return mOracleType;
    }

    public String getMysqlType() {
        return mMysqlType;
    }

    public String getPostresqlType() {
        return mPostresqlType;
    }
}