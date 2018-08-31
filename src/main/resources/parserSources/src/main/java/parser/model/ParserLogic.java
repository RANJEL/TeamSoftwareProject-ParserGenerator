package parser.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.core.ScriptsData;
import parser.utils.ConverterNode2String;
import parsermodel.ParserException;
import parsermodel.tree.ASTree;
import parsermodel.tree.nodes.CaptureNode;
import parsermodel.tree.nodes.Node;
import parsermodel.tree.nodes.TagNode;
import parsermodel.utils.ConverterElem2String;
import us.codecraft.xsoup.Xsoup;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jsoup.parser.Parser.xmlParser;

/**
 * @author Jakub Šedý
 */
public class ParserLogic {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final Map<String, Node> stringToNodeMap;
    private final String xPathString;

    /**
     * Vytvoří třídu, načte ASTree a vyparsuje elementy všech stromů do stringu pro rychlejší práci
     *
     * @param pathToTreeInResources Cesta k astree
     */
    public ParserLogic(String pathToTreeInResources) {
        ASTree tree = null;
        try (InputStream in = getClass().getResourceAsStream(pathToTreeInResources)) {
            tree = new ASTree(in);
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            System.exit(1);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "ASTree nebyl nalezen!", ex);
            System.exit(1);
        }

        Node n = tree.getRootNode();
        xPathString = tree.getXPathString();

        stringToNodeMap = new HashMap<>();
        for (Node successorNode : n.getSuccessorNodes()) {
            stringToNodeMap.put(ConverterNode2String.getStructureInString(successorNode), successorNode);
        }
    }

    @Nonnull
    public synchronized ScriptsData parseDocument(URL documentUrl) throws IOException {
        Document document = Jsoup.parse(documentUrl, 5000);
        ScriptsData scriptsData = new ScriptsData();
        parseAll(document, scriptsData);
        return scriptsData;
    }

    @Nonnull
    public synchronized ScriptsData parseDocument(InputStream documentInputStream) throws IOException {
        Document document = Jsoup.parse(documentInputStream, "UTF-8", "", xmlParser());
        ScriptsData scriptsData = new ScriptsData();
        parseAll(document, scriptsData);
        return scriptsData;
    }

    /**
     * Rozparsuje xml file podle šablony astree. Pro všechny elementy z xpath porovná, jestli nějaký sedí šabloně.
     * Pokud ano, uloží ho.
     *
     * @param document    Parsovaný dokument
     * @param scriptsData Struktura pro uložení dat
     */
    private void parseAll(@Nonnull Document document, @Nonnull ScriptsData scriptsData) {
        Elements elements = Xsoup.select(document, xPathString).getElements();
        for (Element elem : elements) {
            String elemStructure = ConverterElem2String.getStructureInString(elem);
            Node n = stringToNodeMap.get(elemStructure);
            if (n != null) {
                parse(elem, n, scriptsData);
                scriptsData.addInsertsFromMapToList();
            }
        }
    }

    /**
     * Rekurzivní metoda pro ukládaní dat volaná pouze tehdy, pokud struktura xml souboru a astree sedí.
     */
    private boolean parse(Element e, Node n, ScriptsData scriptsData) {
        int i = 0;
        int j = 0;
        while (i < n.getSuccessorNodes().size()) {
            if (j == e.children().size()) {
                return false;
            }
            while (j < e.children().size()) {
                if (e.child(j).nodeName().compareToIgnoreCase(((TagNode) n.getSuccessorNodes().get(i)).getTagName()) == 0) {
                    if (n.getSuccessorNodes().get(i).getSuccessorNodes().isEmpty()) {
                        j++;
                        i++;
                        break;

                    } else {
                        if (!n.getSuccessorNodes().get(i).isLastTagNode()) {
                            if (!parse(e.child(j), n.getSuccessorNodes().get(i), scriptsData)) {
                                return false;
                            }
                            j++;
                            i++;
                            break;
                        }
                        //získání capture node
                        CaptureNode cptNode = (CaptureNode) n.getSuccessorNodes().get(i).getSuccessorNodes().get(0);
                        scriptsData.addTableOrColumn(cptNode.getTableName(), cptNode.getColumnName(), cptNode.getDataType());
                        scriptsData.addColumnToInsertDataMap(cptNode.getTableName(), cptNode.getColumnName(), e.child(j).text());
                        j++;
                        i++;
                        break;
                    }
                }
                j++;
            }
        }
        return true;
    }
}