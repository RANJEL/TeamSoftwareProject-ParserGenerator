package generator.outputs;

import org.apache.commons.io.FileUtils;
import parsermodel.ParserException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída uchovávající informace o zdrojových souborech a knihovnách generovaného
 * parseru.
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class SourceOutput {

    private static final Logger LOGGER = Logger.getLogger(SourceOutput.class.getName());

    private static final String RESOURCES_SOURCE_FOLDER = "parserSources/";

    protected final File mOutput;

    /**
     * Konstruktor
     *
     * @param outputFile Výstupní soubor, v případě zdrojového výstupu slouží k
     */
    public SourceOutput(@Nonnull File outputFile) {
        mOutput = outputFile;
    }

    /**
     * Připraví výstup.
     *
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    public void createOutput() throws ParserException {
        final String absolutePath = mOutput.getAbsolutePath() + File.separator;
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "build.gradle", absolutePath + "build.gradle");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "config.gradle", absolutePath + "config.gradle");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "gradle/wrapper/gradle-wrapper.jar", absolutePath + "gradle/wrapper/gradle-wrapper.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "gradle/wrapper/gradle-wrapper.properties", absolutePath + "gradle/wrapper/gradle-wrapper.properties");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "gradlew", absolutePath + "gradlew");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "gradlew.bat", absolutePath + "gradlew.bat");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "libs/commons-cli-1.3.1.jar", absolutePath + "libs/commons-cli-1.3.1.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "libs/mysql-connector-java-6.0.5.jar", absolutePath + "libs/mysql-connector-java-6.0.5.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "libs/ojdbc6-11.2.0.3.jar", absolutePath + "libs/ojdbc6-11.2.0.3.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "libs/postgresql-9.4.1212.jre7.jar", absolutePath + "libs/postgresql-9.4.1212.jre7.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/build.gradle", absolutePath + "parsermodel/build.gradle");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/libs/jsoup-1.10.1.jar", absolutePath + "parsermodel/libs/jsoup-1.10.1.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/libs/xsoup-0.3.1.jar", absolutePath + "parsermodel/libs/xsoup-0.3.1.jar");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/DataType.java", absolutePath + "parsermodel/src/main/java/parsermodel/DataType.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/ParserException.java", absolutePath + "parsermodel/src/main/java/parsermodel/ParserException.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/tree/ASTree.java", absolutePath + "parsermodel/src/main/java/parsermodel/tree/ASTree.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/tree/nodes/CaptureNode.java", absolutePath + "parsermodel/src/main/java/parsermodel/tree/nodes/CaptureNode.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/tree/nodes/Node.java", absolutePath + "parsermodel/src/main/java/parsermodel/tree/nodes/Node.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/tree/nodes/TagNode.java", absolutePath + "parsermodel/src/main/java/parsermodel/tree/nodes/TagNode.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "parsermodel/src/main/java/parsermodel/utils/ConverterElem2String.java", absolutePath + "parsermodel/src/main/java/parsermodel/utils/ConverterElem2String.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "README.md", absolutePath + "README.md");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "settings.gradle", absolutePath + "settings.gradle");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/CommandLineArgumentsEnum.java", absolutePath + "src/main/java/parser/CommandLineArgumentsEnum.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/core/databaseStructure/Column.java", absolutePath + "src/main/java/parser/core/databaseStructure/Column.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/core/databaseStructure/Table.java", absolutePath + "src/main/java/parser/core/databaseStructure/Table.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/core/InsertData.java", absolutePath + "src/main/java/parser/core/InsertData.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/core/ScriptsData.java", absolutePath + "src/main/java/parser/core/ScriptsData.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/GeneratedParser.java", absolutePath + "src/main/java/parser/GeneratedParser.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/dao/AbstractDao.java", absolutePath + "src/main/java/parser/model/dao/AbstractDao.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/dao/DaoEnum.java", absolutePath + "src/main/java/parser/model/dao/DaoEnum.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/dao/daoImplementations/MysqlDao.java", absolutePath + "src/main/java/parser/model/dao/daoImplementations/MysqlDao.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/dao/daoImplementations/OracleDao.java", absolutePath + "src/main/java/parser/model/dao/daoImplementations/OracleDao.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/dao/daoImplementations/PostgresqlDao.java", absolutePath + "src/main/java/parser/model/dao/daoImplementations/PostgresqlDao.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/model/ParserLogic.java", absolutePath + "src/main/java/parser/model/ParserLogic.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/utils/CommandLineArgumentsUtils.java", absolutePath + "src/main/java/parser/utils/CommandLineArgumentsUtils.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/utils/ConnectionStringParser.java", absolutePath + "src/main/java/parser/utils/ConnectionStringParser.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/utils/ConverterNode2String.java", absolutePath + "src/main/java/parser/utils/ConverterNode2String.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/main/java/parser/utils/PeriodParser.java", absolutePath + "src/main/java/parser/utils/PeriodParser.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/test/java/parser/model/dao/daoImplementations/MysqlDaoTest.java", absolutePath + "src/test/java/parser/model/dao/daoImplementations/MysqlDaoTest.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/test/java/parser/model/dao/daoImplementations/OracleDaoTest.java", absolutePath + "src/test/java/parser/model/dao/daoImplementations/OracleDaoTest.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/test/java/parser/model/dao/daoImplementations/PostgresqlDaoTest.java", absolutePath + "src/test/java/parser/model/dao/daoImplementations/PostgresqlDaoTest.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/test/java/parser/utils/ConnectionStringParserTest.java", absolutePath + "src/test/java/parser/utils/ConnectionStringParserTest.java");
        copyResourcesFile(RESOURCES_SOURCE_FOLDER + "src/test/java/parser/utils/PeriodParserTest.java", absolutePath + "src/test/java/parser/utils/PeriodParserTest.java");
    }

    /**
     * @param fileName
     * @param outFileName
     * @throws ParserException
     */
    private void copyResourcesFile(@Nonnull String fileName, @Nonnull String outFileName) throws ParserException {
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
            FileUtils.copyToFile(in, new File(outFileName));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ParserException(ParserException.FILE_NOT_FOUND);
        }
    }
}
