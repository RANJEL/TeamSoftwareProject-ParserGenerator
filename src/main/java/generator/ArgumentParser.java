package generator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parsuje pole argumentů do mapy klíčů a hodnot
 *
 * @author Luis Sanchez
 */
public class ArgumentParser {

    private HashMap<String, String> argMap;
    private ArrayList<String> fileNames;
    private static ArrayList<String> validArgs;

    /**
     * Vytvoří ArgumentParser ze spouštěcích parametrů
     *
     * @param args Spouštěcí parametry
     */
    public ArgumentParser(String[] args) {
        addValidArgs();

        argMap = new HashMap<>();
        fileNames = new ArrayList<>();

        for (String arg : args) {
            String argName;
            String argValue = new String();
            if (arg.charAt(0) == '-') {
                argName = arg.substring(1);
            } else {
                addFileName(arg); //arguments without '-' are considered to be file names
                continue;
            }
            /* //Pouze pro párové argumenty //TODO pokud potřeba
            if(i+1<args.length && args[i].charAt(0)!='-')
            {
            argValue=args[i+1];
            i++;
            }*/
            try {
                addArg(argName, argValue);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid argument!");
                printValidArgs(true);
                System.exit(1);
            }
        }
    }

    /**
     * Vypíše všechny platné argumenty
     *
     * @param error Vypsat na error stream?
     */
    public static void printValidArgs(boolean error) {
        PrintStream str;
        if (error) {
            str = System.err;
        } else {
            str = System.out;
        }

        str.println("Valid arguments:");
        for (String argName : validArgs) {
            str.println("-" + argName);
        }

    }

    /**
     * Kontroluje zda existuje parametr s názvem
     *
     * @param argName Název parametru
     * @return true parametr existuje
     */
    public boolean isArgSet(String argName) {
        return argMap.containsKey(argName);
    }

    /**
     * Zjistí hodnotu paraemtru
     *
     * @param argName Jméno parametru
     * @return Hodnota parametru
     */
    public String getArgValue(String argName) {
        return argMap.get(argName);
    }

    /**
     * Vrátí seznam argumentů považovaných za jména souborů
     *
     * @return seznam jmen souborů
     */
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    /**
     * Uloží parsovaný argument (Pokud je potřeba dá se zde kontrolovat
     * validita)
     */
    private void addArg(String argName, String argValue) {
        if (validArgs.contains(argName)) {
            argMap.put(argName, argValue);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Uloží parsovaný nepřepínačový argument (Pokud je potřeba dá se zde
     * kontrolovat validita)
     */
    private void addFileName(String fileName) {
        fileNames.add(fileName);
    }

    /**
     * Načte platné argumenty
     */
    private void addValidArgs() {
        if (validArgs != null) {
            return;
        }

        validArgs = new ArrayList<>();
        validArgs.add("nogui");
        validArgs.add("compile");
        validArgs.add("help");
    }
}
