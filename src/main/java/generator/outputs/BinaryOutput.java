package generator.outputs;

import org.apache.commons.io.FileUtils;
import parsermodel.ParserException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída sloužící k sestavení binárního výstupu ze zdrojových souborů. Třída se
 * stará o kompilaci a zabalení do výsledného .jar souboru.
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class BinaryOutput extends SourceOutput {

    private static final Logger LOGGER = Logger.getLogger(BinaryOutput.class.getName());

    private static final String WORKING_NAME = "generated";

    private File mWorkingFile;

    public BinaryOutput(@Nonnull File outputFile) {
        super(outputFile);
    }

    /**
     * Vytvoří binární výstup. Provede kompilaci a uložení do .jar souboru. Do
     * souboru jsou přibaleny i potřebné knihovny.
     *
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    @Override
    public void createOutput() throws ParserException {
        super.createOutput();
        mWorkingFile = new File(mOutput.getParentFile(), WORKING_NAME);
        if (mOutput.renameTo(mWorkingFile)) {
            LOGGER.info("Directory was renamed.");
        }
        this.compile();
    }

    private void compile() throws ParserException {
        try {
            String gradlewScript;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                gradlewScript = "gradlew.bat";
            } else {
                gradlewScript = "gradlew";
                File gradlewFile = mWorkingFile.toPath().resolve(gradlewScript).toFile();
                if (!gradlewFile.setExecutable(true)) {
                    LOGGER.warning("Could not set gradlew executable permission. Please run gradlew manually.");
                }
            }

            Process gradlewProcess = Runtime.getRuntime().exec(
                    new String[]{mWorkingFile.getAbsolutePath() + File.separator + gradlewScript, "createJar"},
                    null,
                    mWorkingFile
            );

            int returnCode = gradlewProcess.waitFor();
            LOGGER.info("Compilation resulted in code " + returnCode);

            // Print gradlew process outputs
            Scanner errorText = new Scanner(gradlewProcess.getErrorStream());
            while (errorText.hasNext()) {
                LOGGER.warning(errorText.nextLine());
            }
            Scanner outText = new Scanner(gradlewProcess.getInputStream());
            while (outText.hasNext()) {
                LOGGER.info(outText.nextLine());
            }

            if (returnCode == 0) {
                copyOutput();
                FileUtils.deleteDirectory(mWorkingFile);
                LOGGER.info("Compilation successful");
            } else {
                LOGGER.warning("Compilation not successful");
            }
        } catch (InterruptedException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new ParserException(e.getMessage());
        }
    }

    /**
     * Zkopíruje výstupy gradle scriptu mimo "generated" adresář.
     */
    private void copyOutput() throws IOException {
        FileFilter jarFileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathName) {
                return pathName.isFile() && pathName.getPath().endsWith(".jar");
            }
        };

        File buildDir = new File(mWorkingFile, "build/libs");
        if (!buildDir.isDirectory()
                && (buildDir.listFiles(jarFileFilter) == null || buildDir.listFiles(jarFileFilter).length > 0)) {
            LOGGER.warning("Invalid output directory structure!");
            return;
        }

        File parserFile = buildDir.listFiles(jarFileFilter)[0];

        // Copy parser
        Files.copy(parserFile.toPath(), mOutput.toPath(), StandardCopyOption.REPLACE_EXISTING);
        LOGGER.info("Files copied");
    }
}