package parser.core.databaseStructure;

import parsermodel.DataType;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 29.10.2016.
 */
public class Table {

    private final String mName;
    private final Column mIdentifier;

    private Set<Column> mColumns = new TreeSet<>();

    public Table(@Nonnull String name) {
        mName = name;
        mIdentifier = new Column(name + "_id", DataType.INTEGER.getTypeName());
    }

    public Table(@Nonnull String name, @Nonnull Column identifier) {
        mName = name;
        mIdentifier = identifier;
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return mName.equals(obj);
    }

    public String getName() {
        return mName;
    }

    public Column getIdentifier() {
        return mIdentifier;
    }

    public Set<Column> getColumns() {
        return mColumns;
    }

    public void setColumns(Set<Column> columns) {
        mColumns = columns;
    }
}
