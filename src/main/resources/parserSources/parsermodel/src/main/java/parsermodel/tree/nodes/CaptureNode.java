package parsermodel.tree.nodes;

import java.io.Serializable;

/**
 * Uzel AST stromu, který nese informace o tom, jakého typu data budou a kam se
 * mají uložit (tabulka + sloupec).
 *
 * @author Jan Lejnar
 */
public class CaptureNode extends Node implements Serializable {

    private final String tableName;
    private final String columnName;
    /**
     * Typ dat (text, integer, float,...) - bude namapovano na typy databáze.
     */
    private final String dataType;

    /**
     * Konstruktor, vytvářející CaptureNode (list).
     *
     * @param predecessorNode Nejvíce vnořený TagNode, přímo předcházející
     * tomuto listu.
     * @param tableName Název tabulky pro uložení dat.
     * @param columnName Název sloupce pro uložení dat.
     * @param dataType Typ nalezenych dat.
     */
    public CaptureNode(TagNode predecessorNode, String tableName, String columnName, String dataType) {
        super(predecessorNode);
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
    }

    /**
     * Debug metoda, která vypíše atributy aktuálního CaptureNodu.
     */
    @Override
    public void printNode() {
        System.out.println("tablename = " + tableName
                + ", columnName = " + columnName
                + ", dataType = " + dataType);
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    /**
     * Getter na typ dat.
     *
     * @return Typ dat.
     */
    public String getDataType() {
        return dataType;
    }
}
