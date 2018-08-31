package parser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public enum CommandLineArgumentsEnum {

    ARG_FILE("f", "file", false, true, "file", "TODO: Description"),
    ARG_URL("u", "url", false, true, "url", "TODO: Description"),
    ARG_DATABASE_NAME("db", "dbName", true, true, "db", "TODO: Description"),
    ARG_CONNECTION_STRING("cs", "cString", true, true, "string", "TODO: Description"),
    ARG_SERVICE("s", "service", false, true, "period", "TODO: Description"),
    ARG_HELP("help", null, false, false, null, "TODO: Description"),
    ARG_VERSION("version", null, false, false, null, "TODO: Description");

    private String mShortOpt;
    private String mLongOpt;
    private boolean mMandatory;
    private boolean mHasArgument;
    private String mArgumentName;
    private String mDescription;

    CommandLineArgumentsEnum(@Nonnull String shortOpt, @Nullable String longOpt, boolean mandatory,
                             boolean hasArgument, @Nullable String argumentName, @Nullable String description) {
        mShortOpt = shortOpt;
        mLongOpt = longOpt;
        mMandatory = mandatory;
        mHasArgument = hasArgument;
        mArgumentName = argumentName;
        mDescription = description;
    }

    public String getShortOpt() {
        return mShortOpt;
    }

    public String getLongOpt() {
        return mLongOpt;
    }

    public boolean isMandatory() {
        return mMandatory;
    }

    public boolean hasArgument() {
        return mHasArgument;
    }

    public String getArgumentName() {
        return mArgumentName;
    }

    public String getDescription() {
        return mDescription;
    }
}
