package parser.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import parser.CommandLineArgumentsEnum;
import parser.model.dao.DaoEnum;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static parser.CommandLineArgumentsEnum.*;

/**
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public final class CommandLineArgumentsUtils {

    private static Options sOptions;

    private CommandLineArgumentsUtils() {
    }

    public static Options getOptions() {
        if (sOptions == null) {
            sOptions = new Options();
            for (CommandLineArgumentsEnum argument : CommandLineArgumentsEnum.values()) {
                sOptions.addOption(
                        Option.builder(argument.getShortOpt())
                                .longOpt(argument.getLongOpt())
                                .hasArg(argument.hasArgument())
                                .argName(argument.getArgumentName())
                                .desc(argument.getDescription())
                                .build()
                );
            }
        }
        return sOptions;
    }

    public static void check(@Nonnull final CommandLine commandLine) throws IllegalArgumentException, MalformedURLException {
        for (CommandLineArgumentsEnum argument : CommandLineArgumentsEnum.values()) {
            if (argument.isMandatory() && !commandLine.hasOption(argument.getShortOpt())) {
                throw new IllegalArgumentException("Argument " + argument.getShortOpt() + " is mandatory.");
            }
        }

        if (commandLine.hasOption(ARG_DATABASE_NAME.getShortOpt())
                && isUnknownDatabaseName(commandLine.getOptionValue(ARG_DATABASE_NAME.getShortOpt()))) {
            throw new IllegalArgumentException("Database name is unknown.");
        }

        if (commandLine.hasOption(ARG_FILE.getShortOpt()) && commandLine.hasOption(ARG_URL.getShortOpt())) {
            throw new IllegalArgumentException("Must be only one data source.");
        } else if (commandLine.hasOption(ARG_FILE.getShortOpt())) {
            File file = new File(commandLine.getOptionValue(ARG_FILE.getShortOpt()));
            if (!file.exists()) {
                throw new IllegalArgumentException("File isn't exist.");
            }
        } else if (commandLine.hasOption(ARG_URL.getShortOpt())) {
            new URL(commandLine.getOptionValue(ARG_URL.getShortOpt()));
        } else {
            throw new IllegalArgumentException("Data source isn't booked up.");
        }
    }

    private static boolean isUnknownDatabaseName(@Nonnull String inName) {
        for (DaoEnum.DatabaseName name : DaoEnum.DatabaseName.values()) {
            if (name.getName().equals(inName)) {
                return false;
            }
        }
        return true;
    }
}
