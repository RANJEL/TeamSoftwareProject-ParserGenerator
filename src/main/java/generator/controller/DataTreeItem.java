package generator.controller;

import javafx.scene.control.TreeItem;
import generator.model.DataItem;

/**
 * Template třídy soužící k zobrazování v javafx TreeView a uložení zadaných dat
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 */
public class DataTreeItem extends TreeItem<DataItem> {

    private static final String SPLITTER_REGEX = "\\.";

    private boolean selected;

    public DataTreeItem() {
        this.selected = false;
    }

    /**
     * Parametrický konstruktor vytvoří prvek a podle hodnoty určí, zda byl
     * vybrán uživatelem manuálně
     *
     * @param item Prvek stromu uchovávající data
     */
    public DataTreeItem(DataItem item) {
        super(item);
        this.selected = false;
        String itemValue = item.getValue();
        if (itemValue != null) {
            if (itemValue.matches("\\{\\{[a-zA-Z0-9].*\\.[a-zA-Z0-9].*\\.[a-zA-Z0-9].*\\}\\}")) {
                //Logger.getLogger(DataTreeItem.class.getName()).log(Level.INFO, "V tagu jsou vybrana data");
                String text[] = itemValue.substring(2, itemValue.length() - 2).split(SPLITTER_REGEX);
                item.setTableName(text[0]);
                item.setColumnName(text[1]);
                item.setType(text[2]);
                this.selected = true;
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (selected != this.selected) {
            this.changeBranchSelect(selected);
        }
    }

    /**
     * Zpropaguje změnu výběru po větvy stromu vzhůru
     *
     * @param select Hodnota změny
     */
    public void changeBranchSelect(boolean select) {
        this.selected = select;
        DataTreeItem parentItem = (DataTreeItem) getParent();
        if (parentItem != null) {
            if (select) {
                if (!parentItem.selected) {
                    parentItem.changeBranchSelect(select);
                }
            } else {
                boolean isSelect;
                for (TreeItem<DataItem> item : parentItem.getChildren()) {
                    isSelect = ((DataTreeItem) item).selected;
                    if (isSelect) {
                        return;
                    }
                }
                parentItem.changeBranchSelect(select);
            }
        }
    }
}
