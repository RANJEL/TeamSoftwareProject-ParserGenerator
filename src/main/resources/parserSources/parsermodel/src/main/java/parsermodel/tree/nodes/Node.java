package parsermodel.tree.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstraktní uzel AST stromu.
 * <p>
 * Všechny uzly kromě root uzlu mají rodiče.
 * <p>
 * Uzel může být buď typu TagNode, který nese informaci o tom, jaký tag byl
 * hledán.
 * <p>
 * Nebo může být list typu CaptureNode, který přesně popisuje, jakého typu data
 * jsou a kam se mají uložit (tabulka + sloupec).
 *
 * @author Jan Lejnar
 */
public abstract class Node implements Serializable {

    /**
     * Předchůdce uzlu
     */
    protected Node predecessorNode = null;
    /**
     * Seznam následníků uzlu
     */
    protected List<Node> successorNodes = new ArrayList<>();

    /**
     * Konstruktor pro vytvoření prvního uzlu.
     */
    public Node() {
//        System.out.println("Root node created."); //debug
    }

    /**
     * Konstruktor, který vytvoří nový uzel na základě předcházejícího.
     *
     * @param predNode Uzel otce, pod který se nový uzel připojí.
     */
    public Node(Node predNode) {
        predNode.successorNodes.add(this);
        this.predecessorNode = predNode;
//        System.out.println("Node created with predecessor."); //debug
    }

    /**
     * Getter na předchůdce uzlu.
     *
     * @return předchůdce uzlu
     */
    public Node getPredecessorNode() {
        return predecessorNode;
    }

    /**
     * Getter na následníky uzlu.
     *
     * @return následníci uzlu
     */
    public List<Node> getSuccessorNodes() {
        return successorNodes;
    }

    /**
     * Po změnách v architektuře už je jasné, že tam, kde je TagNode nemůže být
     * CaptureNode a naopak, tedy by bylo lepší použít skládání než dědičnost,
     * ale takto lze obejít.
     *
     * @return Jestli jde o TagNode.
     */
    public boolean isTagNode() {
        return this instanceof TagNode;
    }

    /**
     * Po změnách v architektuře už je jasné, že tam, kde je TagNode nemůže být
     * CaptureNode a naopak, tedy by bylo lepší použít skládání než dědičnost,
     * ale takto lze obejít.
     *
     * @return Jestli jde o CaptureNode.
     */
    public boolean isCaptureNode() {
        return this instanceof CaptureNode;
    }

    /**
     * Jde o poslední TagNode? tzn., že pod ním může být už jen CaptureNode
     *
     * @return Jestli jde o poslední TagNode, tzn., že pod ním může být už jen
     * CaptureNode.
     */
    public boolean isLastTagNode() {
        if (!this.isTagNode()) {
            return false;
        }

        for (Node successorNode : successorNodes) {
            if (successorNode.isTagNode()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Má uzel jediného potomka typu CaptureNode?
     *
     * @return Jestli uzel má jediného potomka typu CaptureNode
     */
    public boolean hasOneCaptureNodeSon() {
        if (this.successorNodes.size() != 1) {
            return false;
        }

        return successorNodes.get(0).isCaptureNode();
    }

    /**
     * Debug abstraktní metoda, která vypíše informace o uzlu.
     */
    public abstract void printNode();
}
