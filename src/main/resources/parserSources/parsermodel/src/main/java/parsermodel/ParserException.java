package parsermodel;

/**
 * Výjímka programu. Zobrazuje chybu uživateli
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class ParserException extends Exception {

    public static final String FILE_NOT_FOUND = "Nenalezen zadaný soubor.";
    public static final String JSOUP_PARSE = "Nepodařilo se zpracovat zadaný soubor.";
    public static final String CONNECT = "Nepodařilo se připojit na zadanou adresu.";
    public static final String FILE_NOT_CREATE_OR_MODIFICATE = "Nepodařilo se vytvořit výstupní soubor.";
    public static final String ASTREE_WRITE = "Nepodařilo se uložit stromovou strukturu parseru.";
    public static final String ASTREE_READ = "Nepodařilo se obnovit stromovou strukturu parseru.";
    public static final String COMPILE_ERROR = "Kompilátor vrátil chybový návratový kód.";
    public static final String COMPILE_JAR = "Nepodařilo se vytvořit .jar soubor.";
    public static final String COMPILER_NOT_FOUND = "Nebyl nalezen kompilátor. Je JDK (min verze 1.8) nainstalováno?";

    public ParserException(String string) {
        super(string);
    }
}
