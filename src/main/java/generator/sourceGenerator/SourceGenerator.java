package generator.sourceGenerator;

import generator.outputs.BinaryOutput;
import generator.outputs.SourceOutput;
import parsermodel.ParserException;
import parsermodel.tree.ASTree;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.logging.Logger;

/**
 * Generátor souborů potřebných ke kompilaci parseru.
 * <p>
 * Vytvoří zdrojové soubory a zkopíruje ASTree a potřebné khihovny do
 * generovaného adresáře.
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 * @author Luis Sanchez {@literal <sanchlui@fit.cvut.cz>}
 */
public class SourceGenerator {

    private static final Logger LOGGER = Logger.getLogger(SourceGenerator.class.getName());

    private static final String ASTREE_LOCATION = "src/main/resources/astree.tree";

    private ASTree mTree;
    private OutputSettings mOutputSettings;

    public SourceGenerator(@Nonnull ASTree tree, @Nonnull final OutputSettings outputsettings) throws ParserException {
        mTree = tree;
        mOutputSettings = outputsettings;
    }

    public void createOutput() throws ParserException {
        File treeFile = new File(mOutputSettings.getOutputFile(), ASTREE_LOCATION);
        if (treeFile.getParentFile().mkdirs()) {
            LOGGER.info("Path for astree file was created.");
        }
        mTree.saveToFile(treeFile);
        SourceOutput sOut;
        if (mOutputSettings.isCompilation()) {
            sOut = new BinaryOutput(mOutputSettings.getOutputFile());
        } else {
            sOut = new SourceOutput(mOutputSettings.getOutputFile());
        }
        sOut.createOutput();
    }
}