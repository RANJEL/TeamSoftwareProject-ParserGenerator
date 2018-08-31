/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 * Statická třída definuje všechny konstanty použité v projektu
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class ParserConstants {

    private ParserConstants() {
    }

    public static final String APP_TITLE = "XHTML generator";
    public static final String OPEN_DIALOG_TITLE = "Vyber soubor";
    public static final String OPEN_DIALOG_FILE_TYP_NAME1 = "XHTML";
    public static final String OPEN_DIALOG_FILE_TYP_NAME2 = "XML";
    public static final String OPEN_DIALOG_FILE_TYP1 = ".xhtml";
    public static final String OPEN_DIALOG_FILE_TYP2 = ".xml";
    public static final String OPEN_DIALOG_FILE_ALL_NAME = "Všechny soubory";
    public static final String URL_DIALOG_TITLE = "Zadej URL";
    public static final String XPATH_DIALOG_TITLE = "Zadej XPath řetězec";
    public static final String URL_DIALOG_DESCRIPTION = "Zadej URL k XHTML souboru.";
    public static final String XPATH_DIALOG_DESCRIPTION = "Zadej XPath řetězec, popisující hledané elementy.";
    public static final String URL_DIALOG_CONTENT = "URL:";
    public static final String XPATH_DIALOG_CONTENT = "XPath řetězec:";
    public static final String SAVE_DIALOG_TITLE = "Ulož soubor";
    public static final String SAVE_DIALOG_FILE_TYP_NAME = "JAR";
    public static final String SAVE_DIALOG_FILE_TYP = ".jar";
    public static final String SAVE_DIALOG_DEFAULT_FILE_NAME = "xhtmlParser";
    public static final String SAVE_DIALOG_DEFAULT_UNCOMPILED_PARSER_NAME = "generated";
    public static final String ERROR_DIALOG_TITLE = "Chyba";
    public static final String SUCCESS_DIALOG_TITLE = "Hotovo";
    public static final String SUCCESS_DIALOG_MESSAGE = "Parser byl vygenerován.";
    public static final String URL_PREFIX_HTTPS = "https://";
    public static final String URL_PREFIX_HTTP = "http://";
    public static final String PLACEHOLDER_INPUT = "in.xml";
}
