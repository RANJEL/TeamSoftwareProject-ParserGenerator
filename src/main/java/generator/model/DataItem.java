package generator.model;

/**
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class DataItem {

    private final String tag;
    private final String value;

    private String tableName;
    private String columnName;
    private String type;

    /**
     *
     * @param tag Název tagu
     * @param value Hodnota tagu
     */
    public DataItem(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "<" + tag.toUpperCase() + ">";
        } else {
            return "<" + tag.toUpperCase() + ">" + value + "</" + tag.toUpperCase() + ">";
        }
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setType(String type) {
        this.type = type;
    }

}
