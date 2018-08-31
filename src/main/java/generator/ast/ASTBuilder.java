package generator.ast;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import generator.cmd.CmdRootTag;
import generator.controller.DataTreeItem;
import parsermodel.tree.ASTree;

/**
 * Tato třída vytvoří a uloží nový ASTree, podle commandu.
 *
 * @author Jan Lejnar
 */
public class ASTBuilder {

    /**
     * AST, který bude vytvořen.
     */
    private ASTree astree;

    /**
     * Metoda přidá následníky uzlu node
     *
     * @param node Uzel (otec), jehož syny chci přidat.
     * @param depth Hloubka otce.
     * @param tagHierarchy Hierarchie tagů, kterou potřebuje znát metoda
     * addSubTagNodesAndCapture
     */
    private void addSuccessorsOf(DataTreeItem node, int depth, List<String> tagHierarchy) {
        for (TreeItem successor : node.getChildren()) {
            DataTreeItem successorNode = (DataTreeItem) successor;

            // pridat do cesty
            if (depth + 1 < tagHierarchy.size()) { // staci modifikovat tento prvek
                tagHierarchy.set(depth + 1, successorNode.getValue().getTag());
            } else { // nutno tento prvek pridat na posledni misto do hierarchie
                tagHierarchy.add(successorNode.getValue().getTag());
            }

            if (successorNode.isSelected() == false) {
                // jen pridam list tagNode - symbolizuje, ze ho nechci
                this.astree.addSubTagNodesAndCapture(tagHierarchy, EnumAddCaptureNode.FALSE.value, null, null, null); // Java neumi dat implicitni parametry
                addSuccessorsOf(successorNode, depth + 1, new ArrayList<>(tagHierarchy)); // add dummy structure
            } else if (successorNode.isLeaf()) {
                this.astree.addSubTagNodesAndCapture(tagHierarchy, EnumAddCaptureNode.TRUE.value, successorNode.getValue().getTableName(), successorNode.getValue().getColumnName(), successorNode.getValue().getType());
            } else {
                this.astree.addSubTagNodesAndCapture(tagHierarchy, EnumAddCaptureNode.FALSE.value, null, null, null);
                addSuccessorsOf(successorNode, depth + 1, new ArrayList<>(tagHierarchy)); // zanor se
            }
        }
    }

    /**
     * Metoda, která rekurzivně vytvoří ASTree, podle commandu CmdRootTag.
     *
     * @param root Kořen stromu.
     * @param xPathString XPath řetězec, svázaný s novým stromem.
     * @return ASTree.
     */
    public ASTree createTree(CmdRootTag root, String xPathString) {
        DataTreeItem GUIroot = root.getRoot();

        String rootTagName = "root";
        astree = new ASTree(rootTagName, xPathString);
//        System.out.println(GUIroot.getChildren().size()); // debug

        for (TreeItem successor : GUIroot.getChildren()) {
            DataTreeItem successorNode = (DataTreeItem) successor;

            List<String> tagHierarchy = new ArrayList<>();
            tagHierarchy.add(rootTagName);
            tagHierarchy.add(successorNode.getValue().getTag());

            if (successorNode.isSelected() == false) {
                continue; // preskocit, nedavat pod root node
            } else if (successorNode.isLeaf()) {
                this.astree.addSubTagNodesAndCapture(tagHierarchy, EnumAddCaptureNode.TRUE.value, successorNode.getValue().getTableName(), successorNode.getValue().getColumnName(), successorNode.getValue().getType());
            } else {
                this.astree.addSubTagNodesAndCapture(tagHierarchy, EnumAddCaptureNode.FALSE.value, null, null, null);
                addSuccessorsOf(successorNode, 1, tagHierarchy); // v hloubce 0 je obalovaci uzel "root" // zanor se
            }
        }

        /* debug */
        System.out.println("Debug ASTree:");
        System.out.println("XPathOfAStree = " + astree.getXPathString());
        astree.printTree();
        /**/

        return astree;
    }
}
