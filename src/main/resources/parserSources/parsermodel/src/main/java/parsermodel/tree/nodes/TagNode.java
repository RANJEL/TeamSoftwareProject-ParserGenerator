package parsermodel.tree.nodes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Uzel AST stromu reprezentující XHTML Tag.
 *
 * @author Jan Lejnar
 */
public class TagNode extends Node implements Serializable {

    /**
     * Název tagu - BEZ {@literal <>}
     */
    protected String tagName;

    /**
     * Konstruktor, vytvářející root TagNode.
     * <p>
     * Vždy předpokládám právě jeden root TagNode.
     *
     * @param tag Název tagu.
     */
    public TagNode(String tag) {
        super();
        this.tagName = tag;
//        System.out.println("Root TagNode(" + tag + ") created."); //debug
    }

    /**
     * Konstruktor, vytvářející nový TagNode na základě předchozího TagNode.
     *
     * @param predecessorNode Tag o uroven výše.
     * @param tag Název nového tagu.
     */
    public TagNode(TagNode predecessorNode, String tag) {
        super(predecessorNode);
        this.tagName = tag;
//        System.out.println("TagNode(" + predecessorNode.tagName + ", " + tag + ") created"); // debug
    }

    /**
     * Pro případ ukládání do hash kolekcí také překryto.
     *
     * @return Hash TagNodu.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.tagName);
        return hash;
    }

    /**
     *
     * Porovnává 2 TagNode jen na základě názvu jejich tagů.
     *
     * @param obj Objekt k porovnání.
     * @return Jestli se jiný objekt(ideálně TagNode) rovná tomuto TagNodu.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final TagNode other = (TagNode) obj;
        if (!Objects.equals(this.tagName, other.tagName)) {
            return false;
        }
        return true;
    }

    /**
     * Debug metoda, která vypíše informace (název tagu) aktuálního TagNodu.
     */
    @Override
    public void printNode() {
        System.out.println("tagName = " + tagName);
    }

    /**
     * Getter na název tagu.
     *
     * @return Název TagNodu.
     */
    public String getTagName() {
        return tagName;
    }

}
