package parser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public enum CommandLineArgumentsEnum {

    ARG_FILE("f", "file", false, true, "file", "If we are using file from disc. Give here relative path to it."),
    ARG_URL("u", "url", false, true, "url", "If we are using xml file from web. Assign it with https://www."),
    ARG_DATABASE_NAME("db", "dbName", true, true, "db", "Name of our database."),
    ARG_CONNECTION_STRING("cs", "cString", true, true, "string", "Connection string for database. For more information look at our User manual."),
    ARG_SERVICE("s", "service", false, true, "period", "For start our program as service. For more information look at our User manual."),
    ARG_HELP("help", null, false, false, null, "Basic usage."),
    ARG_VERSION("version", null, false, false, null, "Version of our program.");

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
