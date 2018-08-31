package generator.console;

import java.io.File;
import generator.ArgumentParser;
import static generator.ParserConstants.SUCCESS_DIALOG_MESSAGE;
import parsermodel.ParserException;
import generator.ast.ASTBuilder;
import generator.cmd.CmdRootTag;
import generator.controller.DataTreeItem;
import generator.model.XHTMLFileParser;
import generator.sourceGenerator.OutputSettings;
import generator.sourceGenerator.SourceGenerator;
import parsermodel.tree.ASTree;

/**
 * Konzolové rozhraní programu.
 * <p>
 * Konzolové rozhraní se spouští parametrem -nogui.</p>
 * <p>
 * Vstupem konzolového rozhraní je soubor s vyznačenými "zajímavými částmi".
 * Podle tohoto souboru je následně vygenerován generator. Uživatel může určit zda
 * má dojít ke kompilaci pomocí argument -compile</p>
 *
 * @author Luis Sanchez
 */
public class ConsoleInterface {

    private final ASTBuilder treeBuilder;
    private XHTMLFileParser fileParser;
    private File inputFile;
    private File outputFile;
    private OutputSettings outParams;

    /**
     * Vytvoří ConsoleInterface.
     */
    public ConsoleInterface() {
        treeBuilder = new ASTBuilder();
    }

    /**
     * Hlavní metoda ConsoleInterface. Přijme argumenty a spustí generování
     * parseru.
     *
     * @param args Spouštěcí argumenty programu
     */
    public void launch(ArgumentParser args) {
        boolean compile = false;
        if (args.isArgSet("compile")) {
            compile = true;
        }

        if (args.getFileNames().size() != 2) {
            System.err.println("You need to specify input and output file!");
            System.err.println("Run with -help for more info");
            System.exit(1);
        }

        inputFile = new File(args.getFileNames().get(0));
        if (!inputFile.exists() || inputFile.isDirectory()) {
            System.err.println("Input file does not exist");
            System.exit(1);
        }

        outputFile = new File(args.getFileNames().get(1));
        outParams = new OutputSettings(compile, outputFile);
        fileParser = new XHTMLFileParser();
        try {
            fileParser.parse(inputFile, "/");
            ASTree tree = treeBuilder.createTree(new CmdRootTag((DataTreeItem) fileParser.getRoot()), "/");
            SourceGenerator generator = new SourceGenerator(tree, outParams);
            generator.createOutput();
        } catch (ParserException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        System.out.println(SUCCESS_DIALOG_MESSAGE);
        System.exit(0);
    }
}
