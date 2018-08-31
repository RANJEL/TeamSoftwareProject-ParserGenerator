package generator.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import static org.jsoup.parser.Parser.xmlParser;
import org.jsoup.select.Elements;
import parsermodel.ParserException;
import generator.controller.DataTreeItem;
import parsermodel.utils.ConverterElem2String;
import us.codecraft.xsoup.Xsoup;

/**
 * Třída pro pársování XHTML souborů
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 * @author Jan Lejnar {@literal <lejnajan@fit.cvut.cz>}
 */
public class XHTMLFileParser {

    /**
     * Mnozina udrzujici unikatni posloupnost tagu je documentElements
     */
    private Elements documentElements;
    private DataTreeItem root;

    /**
     * Konstruktor třídy
     *
     */
    public XHTMLFileParser() {
        this.documentElements = new Elements();
        this.root = null;
    }

    /**
     * Provede pársování souboru pomocí frameworku Jsoup
     *
     * @param file XHTML soubor
     * @param xPathStr XPathString popisujici, co si uzivatel preje zobrazit.
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    public void parse(File file, String xPathStr) throws ParserException {
        try (FileInputStream fis = new FileInputStream(file)) {
            Document document = Jsoup.parse(fis, "UTF-8", "", xmlParser());

            Elements elems = Xsoup.select(document, xPathStr).getElements();
//            System.out.println("Elems size = " + elems.size()); // debug

            Set<String> setOfStructures = new HashSet<>();

            for (Element elem : elems) {
//                System.out.println("-----------------------"); // debug
//                System.out.println(elem.tagName()); // debug
//                System.out.println(elem.toString()); // debug
                String elemStructure = ConverterElem2String.getStructureInString(elem);
//                System.out.println(elemStructure); // debug
                if (!setOfStructures.contains(elemStructure)) {
                    setOfStructures.add(elemStructure);
                    documentElements.add(elem);
                }
            }

            /* debug check 
            for (Element elem : documentElements) {
                System.out.println("-----------------------");
                System.out.println(elem.toString());
                String elemStructure = ConverterElem2String.getStructureInString(elem);
                System.out.println(elemStructure);
            }
             */
        } catch (FileNotFoundException FNFException) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Nenalezen vstupni soubor", FNFException);
            throw new ParserException(ParserException.FILE_NOT_FOUND);
        } catch (IOException IOEx) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Chyba pri parsovani souboru", IOEx);
            throw new ParserException(ParserException.JSOUP_PARSE);
        } catch (Exception ex) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Chyba v XSoup", ex);
        }
    }

    /**
     * Provede pársování xhtml souboru z webu pomocí frameworku Jsoup
     *
     * @param url Webová adresa ke stažení XHTML souboru
     * @param xPathStr XPathString popisujici, co si uzivatel preje zobrazit.
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    public void parseByURL(String url, String xPathStr) throws ParserException {
        try {
            Document document = Jsoup.connect(url).get();

            Elements elems = Xsoup.select(document, xPathStr).getElements();

            Set<String> setOfStructures = new HashSet<>();
            
            for (Element elem : elems) {
                String elemStructure = ConverterElem2String.getStructureInString(elem);
                if (!setOfStructures.contains(elemStructure)) {
                    setOfStructures.add(elemStructure);
                    documentElements.add(elem);
                }
            }

        } catch (IllegalArgumentException IAEx) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Chybna url/Nepodarilo se pripojit na web", IAEx);
            throw new ParserException(ParserException.CONNECT);
        } catch (IOException IOEx) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Chyba pri parsovani souboru", IOEx);
            throw new ParserException(ParserException.JSOUP_PARSE);
        } catch (Exception ex) {
            Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.SEVERE, "Chyba v XSoup", ex);
        }
    }

    /**
     * Vrátí kořen strumové struktury, pokud struktura není vytvořena, vytvoří
     * ji
     *
     * @return Kořen stromové struktury pro zobrazení v prvku javafx TreeView
     */
    public DataTreeItem getRoot() {
        if (root == null) {
            root = new DataTreeItem();
            buildTree(this.documentElements, root);
        }
        return root;
    }

    /**
     * Rekurzivní metoda pro postavení stromové struktury k zobrazení v prvku
     * javafx TreeView
     *
     * @param parentJsoup Uzel stromu získaný pársováním soubory pomocí Jsoup
     * @param parentItem Uzel stromu pro zobrazení
     */
    private void buildTree(Elements parentJsoup, DataTreeItem parentItem) {
        for (Element elem : parentJsoup) {
            DataTreeItem item = new DataTreeItem(new DataItem(elem.tagName(), elem.children().isEmpty() ? elem.text() : null));
            item.setExpanded(true);
            parentItem.getChildren().add(item);
            if (item.isSelected()) {
                //Logger.getLogger(XHTMLFileParser.class.getName()).log(Level.INFO, "prvek byl vybran uz v souboru");
                item.changeBranchSelect(true);
            }
            buildTree(elem.children(), item);
        }
    }
}
