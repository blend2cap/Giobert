package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.textfield.TextFields;
import sample.Repositories.NameSurname;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerElimina extends DBConnection implements Initializable, ChangeListener{

    @FXML
    private    JFXCheckBox classiCheck;
    @FXML
    private    JFXCheckBox anniCheck;
    @FXML
    private    JFXCheckBox alunniCheck;
    @FXML
    private    JFXCheckBox giteCheck;
    @FXML
    private    JFXTextField cercaTextField;
    @FXML
    private    JFXListView<String> classiList;
    @FXML
    private    JFXListView<String> anniList;
    @FXML
    private JFXTreeTableView<NameSurname> alunniTable;
    @FXML
    private JFXListView<String> giteList;

    private ObservableList<NameSurname> cognomi;
    private ObservableList<String> autoCompletionList;

    public ControllerElimina() throws SQLException, ClassNotFoundException {
        classiList=new JFXListView<>();
        anniList=new JFXListView<>();
        giteList=new JFXListView<>();
        cognomi= getNomeCognome();
        autoCompletionList=getCognome();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classiList.setItems(Controller.elencoClassi);
        classiList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        anniList.setItems(Controller.elencoAnniScolastici);
        anniList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            giteList.setItems(getGiteForCombo());
            giteList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(cercaTextField, autoCompletionList);
        cercaTextField.textProperty().addListener((observable, oldValue, newValue) ->
                alunniTable.setPredicate(alunnoTreeItem -> alunnoTreeItem.getValue().surname.getValue().contains(newValue)));
        //Initialize table
        JFXTreeTableColumn<NameSurname, String> colonnaCognome = new JFXTreeTableColumn<>("Cognome");
        colonnaCognome.setPrefWidth(150);
        colonnaCognome.setCellValueFactory(param -> param.getValue().getValue().getSurname());

        JFXTreeTableColumn<NameSurname, String> colonnaNome = new JFXTreeTableColumn<>("Nome");
        colonnaNome.setPrefWidth(150);
        colonnaNome.setCellValueFactory(param -> param.getValue().getValue().getName());

        final RecursiveTreeItem root = new RecursiveTreeItem<>(cognomi, RecursiveTreeObject::getChildren);
        alunniTable.setRoot(root);
        alunniTable.setShowRoot(false);
        alunniTable.getColumns().setAll(colonnaCognome, colonnaNome);
        alunniTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void advancedDeletion() {
        try {
            if (alunniCheck.isSelected()) {
                ObservableList<TreeItem<NameSurname>> list=alunniTable.getSelectionModel().getSelectedItems();
                for (TreeItem<NameSurname> item: list) deleteAlunni(item);
            }

            if (anniCheck.isSelected()) {
                ObservableList<String> list = anniList.getSelectionModel().getSelectedItems();
                for (String item : list) deleteAnno(item);
            }

            if (classiCheck.isSelected()) {
                ObservableList<String> list = classiList.getSelectionModel().getSelectedItems();
                for (String item : list) deleteClasse(item);
            }

            if (giteCheck.isSelected()) {
                ObservableList<String> list = giteList.getSelectionModel().getSelectedItems();
                for (String item : list) deleteGita(item);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Elementi non cancellati");
            alert.showAndWait();
        }
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

    }
}
