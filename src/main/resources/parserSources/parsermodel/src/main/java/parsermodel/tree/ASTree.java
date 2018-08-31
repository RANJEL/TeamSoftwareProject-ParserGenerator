package parsermodel.tree;

import parsermodel.ParserException;
import parsermodel.tree.nodes.CaptureNode;
import parsermodel.tree.nodes.Node;
import parsermodel.tree.nodes.TagNode;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AST strom reprezentuje strukturu XHTML stromu, která složí jako vzor, jaký se
 * bude hledat ve vstupním souboru.
 * <p>
 * Pokud budou sedět tagy po cestě, v listu bude ASTree obsahovat CaptureNode,
 * ve kterém je informace od uživatele o typu a popisu dat.
 * <p>
 * Abychom byli schopni rozlišit, že uživatel hledá například 3. výskyt tagu TD,
 * bude do stromu uloženo 3x TagNode TD, ale jen pod tím 3. bude CaptureNode.
 * Pokud je v AST list typu TagNode, pouze to značí přeskoč tento tag, někde dál
 * bude něco, co mě zajímá.
 * <p>
 * Musíme implementovat Serializable, protože budeme objekt typu ASTree chtít
 * ukládat a načítat ze souboru. Proto musí být serializovatelné i všechny
 * generator.tree.nodes.
 *
 * @author Jan Lejnar
 */
public class ASTree implements Serializable {

    /**
     * Kořenový uzel s tagem - předpokládáme, že bude bez "sourozenců".
     */
    protected TagNode rootNode;
    protected String xPathString;

    /**
     * Konstruktor vytvoří nový strom s kořenovým uzlem.
     *
     * @param rootTag     Název tagu kořenového TagNodu.
     * @param xPathString XPath řetězec svázaný s tímto ASTree.
     */
    public ASTree(String rootTag, String xPathString) {
        rootNode = new TagNode(rootTag);
        this.xPathString = xPathString;
    }

    /**
     * Konstruktor, který vytváří ASTree deserializací ze souboru.
     *
     * @param fileInputStream Soubor, ve kterém je serializovaný ASTree.
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    public ASTree(InputStream fileInputStream) throws ParserException {
        /* loadFromFile */
        try (ObjectInput in = new ObjectInputStream(fileInputStream)) {
            ASTree fromFileASTree = (ASTree) in.readObject();
            this.rootNode = fromFileASTree.rootNode;
            this.xPathString = fromFileASTree.getXPathString();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ASTree.class.getName()).log(Level.SEVERE, "Neznama trida pro deserializaci", ex);
            throw new ParserException(ParserException.ASTREE_READ);
        } catch (IOException ex) {
            Logger.getLogger(ASTree.class.getName()).log(Level.SEVERE, "Chyba pri deserializaci", ex);
            throw new ParserException(ParserException.ASTREE_READ);
        }
    }

    /**
     * Klíčová metoda, která umí upravit ASTree = přidat hierarchii TagNodů a
     * případně i s CaptureNodem (nebo bez, což symbolizuje, aby byl tento tag
     * přeskočen při hledání).
     * <p>
     * Metoda jde podle hierarchie tagů a hledá shodu s ASTree, pokud nenajde,
     * začne vytvářet TagNody, aby tato cesta existovala - tedy umí přidat 1
     * tagNode, nebo i 10 tagNodu najednou, ale potřebuje pro svoji funkčnost
     * tuto hierarchii.
     * <p>
     *
     * @param tagHierarchy Hierarchie tagů, která se má přidat.
     * @param addCapture   Příznak, zda-li se má pod poslední tagNode přidat
     *                     captureNode ({@literal <=>} nejde o dummy tag)
     * @param tableName    Název tabulky pro uložení dat v listu
     * @param columnName   Název sloupce pro uložení dat v listu
     * @param dataType     Typ dat v listu
     * @throws IllegalArgumentException Návrh vyžaduje nejdříve vytvořit strom s
     *                                  kořenovým uzlem, potom terpve upravovat strom.
     */
    public void addSubTagNodesAndCapture(List<String> tagHierarchy, boolean addCapture, String tableName, String columnName, String dataType) throws IllegalArgumentException {
        if (tagHierarchy.size() < 2) {
            throw new IllegalArgumentException("Koren uz musi byt vytvoren konstuktorem!");
        }

        /* dokud nejsem v poslednim tagu */
        TagNode currentNode = rootNode;
        for (int i = 1; i < tagHierarchy.size() - 1; i++) {
            String tag = tagHierarchy.get(i);
            TagNode tmpNode = new TagNode(tag);

            int index = currentNode.getSuccessorNodes().lastIndexOf(tmpNode);

            if (index == -1) { // musim pridat tag
                TagNode newTag = new TagNode(currentNode, tag);
                currentNode = newTag;
            } else { // tento tag uz tam je, muzu jit dal v hierarchii
                currentNode = (TagNode) currentNode.getSuccessorNodes().get(index);
            }
        }
        /* posledni tag pridat vzdy */
        String tag = tagHierarchy.get(tagHierarchy.size() - 1);
        TagNode newTag = new TagNode(currentNode, tag);
        currentNode = newTag;

        if (addCapture) {
            new CaptureNode(currentNode, tableName, columnName, dataType);
        }
    }

    /**
     * Serializace objektu typu ASTre.
     *
     * @param file Souboru, kam se bude serializovat objekt typu ASTree.
     * @throws ParserException Vyjímka oznamující chybu uživateli.
     */
    public void saveToFile(File file) throws ParserException {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
        } catch (FileNotFoundException FNFEx) {
            Logger.getLogger(ASTree.class.getName()).log(Level.SEVERE, "Nenalezen vystupni soubor/nepovedl se vytvorit", FNFEx);
            throw new ParserException(ParserException.FILE_NOT_CREATE_OR_MODIFICATE);
        } catch (IOException ex) {
            Logger.getLogger(ASTree.class.getName()).log(Level.SEVERE, "Nepovedlo se serializovat ASTree.", ex);
            throw new ParserException(ParserException.ASTREE_WRITE);
        }
    }

    /**
     * Debug metoda, která zahájí rekurzivní výpis ASTree.
     */
    public void printTree() {
        printNodeRec(rootNode, 0);
    }

    /**
     * Rekurzivní metoda, vypíše info o akt. uzlu a zavolá se rekurzivně nad
     * potomky.
     *
     * @param node  Aktuální uzel.
     * @param depth Aktuální hloubka uzlu.
     */
    private void printNodeRec(Node node, int depth) {
        String indent = new String();
        for (int i = 0; i < depth; i++) {
            indent += "\t";
        }

        System.out.print(indent);
        node.printNode();

        for (Node successorNode : node.getSuccessorNodes()) {
            printNodeRec(successorNode, depth + 1);
        }
    }

    /**
     * Vrátí počet následníků uzlu.
     *
     * @param node Uzel, jehož počet následníků zjišťuji.
     */
    public void getNumberOfSuccesors(Node node) {
        System.out.println("pocet nasledovniku: " + node.getSuccessorNodes().size());
        for (int i = 0; i < node.getSuccessorNodes().size(); i++) {
            getNumberOfSuccesors(node.getSuccessorNodes().get(i));
        }
    }

    /**
     * Getter na kořenový TagNode
     *
     * @return root TagNode
     */
    public TagNode getRootNode() {
        return rootNode;
    }

    /**
     * Getter na xPath retezec, ktery je k tomuto stromu svazan.
     *
     * @return root TagNode
     */
    public String getXPathString() {
        return xPathString;
    }
}
