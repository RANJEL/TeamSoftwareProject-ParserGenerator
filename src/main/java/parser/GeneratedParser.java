package parser;

import org.apache.commons.cli.*;
import parser.core.InsertData;
import parser.core.ScriptsData;
import parser.core.databaseStructure.Table;
import parser.model.ParserLogic;
import parser.model.dao.AbstractDao;
import parser.model.dao.DaoEnum;
import parser.utils.CommandLineArgumentsUtils;
import parser.utils.PeriodParser;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static parser.CommandLineArgumentsEnum.*;

/**
 * TODO CLASS_EXCEPTION
 *
 * @author Jakub Šedý
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class GeneratedParser {

    private static final Logger LOGGER = Logger.getLogger(GeneratedParser.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            CommandLineParser commandLineParser = new DefaultParser();
            CommandLine commandLine = commandLineParser.parse(CommandLineArgumentsUtils.getOptions(), args);

            if (commandLine.hasOption(ARG_HELP.getShortOpt())) {
                printHelp();
                System.exit(0);
            }
            if (commandLine.hasOption(ARG_VERSION.getShortOpt())) {
                System.out.println("parser version \"3.0.0\"");
                System.exit(0);
            }
            CommandLineArgumentsUtils.check(commandLine);

            ParserLogic parserLogic = new ParserLogic("/astree.tree");
            AbstractDao concreteDao = getConcreteDao(
                    commandLine.getOptionValue(ARG_DATABASE_NAME.getShortOpt()),
                    commandLine.getOptionValue(ARG_CONNECTION_STRING.getShortOpt())
            );

            if (commandLine.hasOption(ARG_SERVICE.getShortOpt())) {
                PeriodParser periodParser = PeriodParser.parse(commandLine.getOptionValue(ARG_SERVICE.getShortOpt()));
                ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
                if (commandLine.hasOption(ARG_FILE.getShortOpt())) {
                    scheduledExecutorService.scheduleAtFixedRate(
                            () -> fileInputProcess(parserLogic, commandLine.getOptionValue(ARG_FILE.getShortOpt()), concreteDao),
                            0,
                            periodParser.getPeriod(),
                            periodParser.getTimeUnit()
                    );
                } else if (commandLine.hasOption(ARG_URL.getShortOpt())) {
                    scheduledExecutorService.scheduleAtFixedRate(
                            () -> urlInputProcess(parserLogic, commandLine.getOptionValue(ARG_URL.getShortOpt()), concreteDao),
                            0,
                            periodParser.getPeriod(),
                            periodParser.getTimeUnit()
                    );
                } else {
                    throw new UnsupportedOperationException("Type of data source isn't defined.");
                }
            } else {
                if (commandLine.hasOption(ARG_FILE.getShortOpt())) {
                    fileInputProcess(parserLogic, commandLine.getOptionValue(ARG_FILE.getShortOpt()), concreteDao);
                } else if (commandLine.hasOption(ARG_URL.getShortOpt())) {
                    urlInputProcess(parserLogic, commandLine.getOptionValue(ARG_URL.getShortOpt()), concreteDao);
                } else {
                    throw new UnsupportedOperationException("Type of data source isn't defined.");
                }
            }
        } catch (ParseException | IllegalArgumentException | MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            printHelp();
            System.exit(1);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.out.println("Nastala chyba, kvůli které aplikace nemůže pokračovat.");
            System.exit(2);
        }
    }

    private static void fileInputProcess(@Nonnull final ParserLogic parserLogic, @Nonnull String input,
                                         @Nonnull final AbstractDao concreteDao) {
        try (FileInputStream inputStream = new FileInputStream(input)) {
            ScriptsData scriptsData = parserLogic.parseDocument(inputStream);
            saveData(scriptsData, concreteDao);
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.out.println("Aktuální data nebyla zpracována.");
        }
    }

    private static void urlInputProcess(@Nonnull final ParserLogic parserLogic, @Nonnull String input,
                                        @Nonnull final AbstractDao concreteDao) {
        try {
            ScriptsData scriptsData = parserLogic.parseDocument(new URL(input));
            saveData(scriptsData, concreteDao);
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.out.println("Aktuální data nebyla zpracována.");
        }
    }

    private static void saveData(@Nonnull final ScriptsData scriptsData,
                                 @Nonnull final AbstractDao concreteDao) throws SQLException {
        concreteDao.connect();
        for (Table table : scriptsData.getTables()) {
            concreteDao.createTable(table);
        }
        for (InsertData insertData : scriptsData.getInsertData()) {
            concreteDao.insert(insertData);
        }
        concreteDao.closeConnection();
    }

    @Nonnull
    private static AbstractDao getConcreteDao(@Nonnull String databaseName, @Nonnull String connectionString)
            throws RuntimeException {
        for (DaoEnum dao : DaoEnum.values()) {
            if (dao.getDbName().getName().equals(databaseName)) {
                try {
                    return dao.getDaoImplClass()
                            .getConstructor(String.class, String.class)
                            .newInstance(connectionString, dao.getDbDriverClass());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    throw new RuntimeException("Fatal error", e);
                } catch (InvocationTargetException e) {
                    throw new IllegalArgumentException(e.getTargetException());
                }
            }
        }
        throw new RuntimeException("Fatal error");
    }

    private static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        String header = "[1] parser.jar -cs <connection string> -db <name> -f <file> [-s <period>]\n"
                + "[2] parser.jar -cs <connection string> -db <name> -u <url> [-s <period>]\n\n";
        helpFormatter.printHelp("Two options:", header, CommandLineArgumentsUtils.getOptions(), null);
    }
}