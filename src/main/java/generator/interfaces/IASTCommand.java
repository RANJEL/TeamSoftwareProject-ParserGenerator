package generator.interfaces;

import generator.controller.DataTreeItem;

/**
 * @author Jan Lejnar
 */
public interface IASTCommand {

    /**
     * Metoda, která vrátí kořen AST, který má command vytvořit.
     *
     * @return Kořen AST.
     */
    public DataTreeItem getRoot();
}
