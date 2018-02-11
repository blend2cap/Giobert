package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerInserisci extends DBConnection implements Initializable {

    public ControllerInserisci() throws SQLException, ClassNotFoundException {
    }

    @FXML
    private JFXTreeTableView<Alunno> myTable;

    @FXML
    private JFXTextField InsAS;

    @FXML
    private JFXTextField inserisciNome;

    @FXML
    private JFXTextField inserisciCognome;


    @FXML
    private JFXComboBox<String> inserisciGitaCombo;

    @FXML
    private JFXComboBox<String> inserisciClasseCombo;

    @FXML
    JFXButton aggiungiButton;

    ObservableList<String> elencoClassi = getClassListForComboBox();
    ObservableList<String> elencoGite = getGiteForCombo();

    public void AggiungiButton(){
            //conversione textfield in Aluuno
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo vuoto");
        InsAS.getValidators().add(validator);
        inserisciNome.getValidators().add(validator);
        inserisciCognome.getValidators().add(validator);

        InsAS.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  InsAS.validate();
        });
        inserisciNome.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  inserisciNome.validate();
        });
        inserisciCognome.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  inserisciCognome.validate();
        });

        //Initialize ComboBox
        inserisciGitaCombo.setItems(elencoGite);
        inserisciClasseCombo.setItems(elencoClassi);


        JFXTreeTableColumn<Alunno, String> colonnaNome = new JFXTreeTableColumn<>("Nome");
        colonnaNome.setPrefWidth(150);
        colonnaNome.setCellValueFactory(param -> param.getValue().getValue().Nome);

        JFXTreeTableColumn<Alunno, String> colonnaCognome = new JFXTreeTableColumn<>("Cognome");
        colonnaCognome.setPrefWidth(150);
        colonnaCognome.setCellValueFactory(param -> param.getValue().getValue().Cognome);

        JFXTreeTableColumn<Alunno, String> colonnaClasse = new JFXTreeTableColumn<>("Classe");
        colonnaClasse.setPrefWidth(150);
        colonnaClasse.setCellValueFactory(param -> param.getValue().getValue().Classe);

        JFXTreeTableColumn<Alunno, String> colonnaGita = new JFXTreeTableColumn<>("Gita");
        colonnaGita.setPrefWidth(150);
        colonnaGita.setCellValueFactory(param -> param.getValue().getValue().Gita);

        JFXTreeTableColumn<Alunno, String> colonnaImporto = new JFXTreeTableColumn<>("Importo");
        colonnaImporto.setPrefWidth(150);
        colonnaImporto.setCellValueFactory(param -> param.getValue().getValue().Importo);

        JFXTreeTableColumn<Alunno, String> colonnaMese = new JFXTreeTableColumn<>("Mese");
        colonnaMese.setPrefWidth(150);
        colonnaMese.setCellValueFactory(param -> param.getValue().getValue().Mese);

        ObservableList<Alunno> alunni = FXCollections.observableArrayList();

        final RecursiveTreeItem root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        myTable.getColumns().setAll(colonnaCognome, colonnaNome, colonnaClasse, colonnaGita, colonnaImporto, colonnaMese);
        //end table
    }
}
