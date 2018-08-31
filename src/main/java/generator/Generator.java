package generator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static generator.ParserConstants.APP_TITLE;
import generator.console.ConsoleInterface;

/**
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class Generator extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layout/main.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(APP_TITLE);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, "Nepodarilo se zobrazit GUI", ex);
            System.err.println("Nepodarilo se zobrazit graficke rozhrani.");
            System.err.println("Zkuste spustit aplikaci bez grafickeho rozhrani.");
            System.exit(1);
        }
    }

    /**
     * Vypíše nápovědu k programu na standardní výstup
     */
    public static void printUsage() {
        System.out.println("XHTML Parser Generator");
        System.out.println("Usage:");
        System.out.println("Application <arguments> inputFile outputFile");
        ArgumentParser.printValidArgs(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArgumentParser arguments = new ArgumentParser(args);

        if (!arguments.isArgSet("nogui")) {
            launch(args);
        } else {
            if (arguments.isArgSet("help")) {
                printUsage();
                System.exit(0);
            }

            ConsoleInterface consInt = new ConsoleInterface();
            consInt.launch(arguments);
        }
    }
}
