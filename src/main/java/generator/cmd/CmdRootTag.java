package generator.cmd;

import generator.controller.DataTreeItem;
import generator.interfaces.IASTCommand;

/**
 * AstCommand, přikazující tvorbu nového ASTree.
 *
 * @author Jan Lejnar
 */
public class CmdRootTag implements IASTCommand {

    /**
     * Kořenový uzel.
     */
    private final DataTreeItem root;

    /**
     * Konstruktor, který přikazuje tvorbu nového ASTree na základě reprezentace
     * z GUI.
     *
     * @param root Kořenový uzel z GUI.
     */
    public CmdRootTag(DataTreeItem root) {
        this.root = root;
    }

    /**
     * Getter na kořenový uzel.
     *
     * @return Kořenový uzel.
     */
    @Override
    public DataTreeItem getRoot() {
        return root;
    }
}
