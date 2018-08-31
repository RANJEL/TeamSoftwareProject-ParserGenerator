package generator.sourceGenerator;

import java.io.File;

/**
 * Uchovává konfiguraci navolenou uživatelem k dalšímu zpracování
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class OutputSettings {

    private final boolean compilation;
    private final File outputFile;

    public OutputSettings(boolean compilation, File outputFile) {
        this.compilation = compilation;
        this.outputFile = outputFile;
    }

    public boolean isCompilation() {
        return compilation;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
