package parser.core.databaseStructure;

import javax.annotation.Nonnull;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 29.10.2016.
 */
public class Column implements Comparable<Column> {

    private final String mName;
    private final String mDataType;

    public Column(@Nonnull String name, @Nonnull String dataType) {
        mName = name;
        mDataType = dataType;
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return mName.equals(obj);
    }

    @Nonnull
    public String getName() {
        return mName;
    }

    @Nonnull
    public String getDataType() {
        return mDataType;
    }

    @Override
    public int compareTo(Column o) {
        return mName.compareTo(o.getName());
    }
}
