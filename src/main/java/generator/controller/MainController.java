package generator.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import static generator.ParserConstants.*;
import parsermodel.ParserException;
import generator.ast.ASTBuilder;
import generator.cmd.CmdRootTag;
import generator.model.DataItem;
import parsermodel.DataType;
import generator.model.XHTMLFileParser;
import generator.sourceGenerator.OutputSettings;
import generator.sourceGenerator.SourceGenerator;
import parsermodel.tree.ASTree;

/**
 *
 * @author Filip Šmíd {@literal <smidfil3@fit.cvut.cz>}
 * @author Jan Lejnar {@literal <lejnajan@fit.cvut.cz>}
 */
public class MainController implements Initializable {

    @FXML
    private TreeView<DataItem> xhtmlView;

    @FXML
    private CheckBox chbSelect;

    @FXML
    private VBox vbSelect;

    @FXML
    private Label lblTableName;

    @FXML
    private TextField txtTableName;

    @FXML
    private Label lblColumnName;

    @FXML
    private TextField txtColumnName;

    @FXML
    private Label lblDataType;

    @FXML
    private ComboBox<String> cmbDataTypes;

    @FXML
    private CheckBox chbCompile;

    @FXML
    private CheckBox chbURLInput;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnGenerate;

    @FXML
    private Label lblXPathString;

    @FXML
    private TextField txtXPathString;

    @FXML
    private void btnOpenClick(ActionEvent event) {
        XHTMLFileParser fileParser = new XHTMLFileParser();
        try {
            if (chbURLInput.isSelected()) {
                TextInputDialog dialog = new TextInputDialog("http://");
                dialog.setTitle(URL_DIALOG_TITLE);
                dialog.setHeaderText(URL_DIALOG_DESCRIPTION);
                dialog.setContentText(URL_DIALOG_CONTENT);
                dialog.showAndWait();

                if (dialog.getResult() != null) {
                    // zadat XPathString
                    TextInputDialog dialogXPath = new TextInputDialog("/");
                    dialogXPath.setTitle(XPATH_DIALOG_TITLE);
                    dialogXPath.setHeaderText(XPATH_DIALOG_DESCRIPTION);
                    dialogXPath.setContentText(XPATH_DIALOG_CONTENT);
                    dialogXPath.showAndWait();

                    if (dialogXPath.getResult() != null && !dialog.getResult().equals("")) {
                        String xPathStr = dialogXPath.getResult();

                        /* Fix Zabraneni spatneho zobrazeni: bud prvni Document, nebo by vratilo duplicitni uzly. */
                        if (xPathStr.equals("*") || xPathStr.equals("//") || xPathStr.equals("//*")
                                || xPathStr.equals(".") || xPathStr.equals("src/main")) {
                            xPathStr = "/";
                        }
                        /**/

                        txtXPathString.setText(xPathStr);
                        fileParser.parseByURL(dialog.getResult(), xPathStr);
                        xhtmlView.rootProperty().set(fileParser.getRoot());
                    }
                }
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(OPEN_DIALOG_TITLE);
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                ArrayList<String> expectedTypes = new ArrayList<>(2);
                expectedTypes.add("*" + OPEN_DIALOG_FILE_TYP1);
                expectedTypes.add("*" + OPEN_DIALOG_FILE_TYP2);
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(OPEN_DIALOG_FILE_TYP_NAME1 + ", " + OPEN_DIALOG_FILE_TYP_NAME2, expectedTypes));
//                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(OPEN_DIALOG_FILE_TYP_NAME1, "*" + OPEN_DIALOG_FILE_TYP1));
//                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(OPEN_DIALOG_FILE_TYP_NAME2, "*" + OPEN_DIALOG_FILE_TYP2));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(OPEN_DIALOG_FILE_ALL_NAME, "*"));
                File file = fileChooser.showOpenDialog(null);

                if (file != null) {
                    chbSelect.selectedProperty().set(false);
                    vbSelect.setDisable(true);
                    txtTableName.clear();
                    txtColumnName.clear();
                    cmbDataTypes.getSelectionModel().clearSelection();

                    // zadat XPathString
                    TextInputDialog dialog = new TextInputDialog("/");
                    dialog.setTitle(XPATH_DIALOG_TITLE);
                    dialog.setHeaderText(XPATH_DIALOG_DESCRIPTION);
                    dialog.setContentText(XPATH_DIALOG_CONTENT);
                    dialog.showAndWait();

                    if (dialog.getResult() != null && !dialog.getResult().equals("")) {
                        String xPathStr = dialog.getResult();
//                        System.out.println(xPathStr); // debug

                        /* Fix Zabraneni spatneho zobrazeni: bud prvni Document, nebo by vratilo duplicitni uzly. */
                        if (xPathStr.equals("*") || xPathStr.equals("//") || xPathStr.equals("//*")
                                || xPathStr.equals(".") || xPathStr.equals("src/main")) {
                            xPathStr = "/";
                        }
                        /**/

                        txtXPathString.setText(xPathStr);
                        fileParser.parse(file, xPathStr);
                        xhtmlView.rootProperty().set(fileParser.getRoot());
                    }
                }
            }
        } catch (ParserException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR_DIALOG_TITLE);
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void btnGenerateClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SAVE_DIALOG_TITLE);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if (chbCompile.isSelected()) {
        fileChooser.setInitialFileName(SAVE_DIALOG_DEFAULT_FILE_NAME + SAVE_DIALOG_FILE_TYP);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(SAVE_DIALOG_FILE_TYP_NAME, "*" + SAVE_DIALOG_FILE_TYP));
        } else {
            fileChooser.setInitialFileName(SAVE_DIALOG_DEFAULT_UNCOMPILED_PARSER_NAME);
        }
        File outputFile = fileChooser.showSaveDialog(null);

        if (outputFile != null) {
            OutputSettings outParams = new OutputSettings(chbCompile.isSelected(), outputFile);
            ASTBuilder astbuilder = new ASTBuilder();
            try {
                ASTree tree = astbuilder.createTree(new CmdRootTag((DataTreeItem) this.xhtmlView.getRoot()), this.txtXPathString.getText());
                SourceGenerator sourceGenerator = new SourceGenerator(tree, outParams);
                sourceGenerator.createOutput();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(SUCCESS_DIALOG_TITLE);
                alert.setHeaderText(SUCCESS_DIALOG_MESSAGE);
                alert.showAndWait();
            } catch (ParserException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR_DIALOG_TITLE);
                alert.setHeaderText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources
    ) {
        chbSelect.setDisable(true);
        vbSelect.setDisable(true);
        cmbDataTypes.setItems(FXCollections.observableArrayList(DataType.getTypeNames()));

        xhtmlView.setShowRoot(false);
        xhtmlView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<DataItem>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<DataItem>> observable, TreeItem<DataItem> oldValue, TreeItem<DataItem> newValue) {
                if (newValue != null) {
                    if (newValue.isLeaf()) {
                        chbSelect.setDisable(false);
                    } else {
                        chbSelect.setDisable(true);
                    }
                    chbSelect.selectedProperty().set(((DataTreeItem) newValue).isSelected());
                    if (((DataTreeItem) newValue).getValue().getValue() == null) {
                        vbSelect.setDisable(true);
                    } else {
                        vbSelect.setDisable(!((DataTreeItem) newValue).isSelected());
                    }
                    txtTableName.setText(((DataTreeItem) newValue).getValue().getTableName());
                    txtColumnName.setText(((DataTreeItem) newValue).getValue().getColumnName());
                    cmbDataTypes.getSelectionModel().select(((DataTreeItem) newValue).getValue().getType());
                }
            }
        });

        chbSelect.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    ((DataTreeItem) xhtmlView.getSelectionModel().getSelectedItem()).setSelected(newValue);
                    vbSelect.setDisable(!newValue);
                }
            }
        });

        txtTableName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    ((DataTreeItem) xhtmlView.getSelectionModel().getSelectedItem()).getValue().setTableName(newValue);
                }
            }
        });

        txtColumnName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    ((DataTreeItem) xhtmlView.getSelectionModel().getSelectedItem()).getValue().setColumnName(newValue);
                }
            }
        });

        cmbDataTypes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    ((DataTreeItem) xhtmlView.getSelectionModel().getSelectedItem()).getValue().setType(newValue);
                }
            }
        });
    }
}
