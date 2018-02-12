package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Repositories.Alunno;
import sample.Repositories.Gita;
import sample.Repositories.NameSurname;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerInserisci extends DBConnection implements Initializable {


    @FXML
    private JFXTreeTableView<AlunnoForTable> myTable;

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

    ObservableList<String> elencoClassi;
    ObservableList<String> elencoGite;

    public ControllerInserisci() throws SQLException, ClassNotFoundException {
        elencoClassi=getClassListForComboBox();
        elencoGite=getGiteForCombo();
    }


    public void AggiungiButton() throws SQLException, ClassNotFoundException {
        HashMap<String, Gita> map=GitaMap();
        String id = null;
        //find id
        for ( String o:map.keySet() )
            if (Objects.equals(map.get(o).getLocation(), inserisciGitaCombo.getValue())) {
                id=o;
                break;
            }
        Alunno alunno = new Alunno(new NameSurname(inserisciNome.getText(), inserisciCognome.getText()), inserisciClasseCombo.getValue(),
                new Gita(map.get(id).getLocation(), map.get(id).getCost(), map.get(id).getMonth()), InsAS.getText());
        addStudenteDB(alunno.nomeCognome);
        addAnnoDB(InsAS.getText());
        addFinalDB(alunno);
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
       // inserisciGitaCombo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super String>) this);
        inserisciClasseCombo.setItems(elencoClassi);
        //inserisciClasseCombo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super String>) this);


        JFXTreeTableColumn<AlunnoForTable, String> colonnaNome = new JFXTreeTableColumn<>("Nome");
        colonnaNome.setPrefWidth(150);
        colonnaNome.setCellValueFactory(param -> param.getValue().getValue().Nome);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaCognome = new JFXTreeTableColumn<>("Cognome");
        colonnaCognome.setPrefWidth(150);
        colonnaCognome.setCellValueFactory(param -> param.getValue().getValue().Cognome);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaClasse = new JFXTreeTableColumn<>("Classe");
        colonnaClasse.setPrefWidth(150);
        colonnaClasse.setCellValueFactory(param -> param.getValue().getValue().Classe);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaGita = new JFXTreeTableColumn<>("Gita");
        colonnaGita.setPrefWidth(150);
        colonnaGita.setCellValueFactory(param -> param.getValue().getValue().Gita);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaImporto = new JFXTreeTableColumn<>("Importo");
        colonnaImporto.setPrefWidth(150);
        colonnaImporto.setCellValueFactory(param -> param.getValue().getValue().Importo);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaMese = new JFXTreeTableColumn<>("Mese");
        colonnaMese.setPrefWidth(150);
        colonnaMese.setCellValueFactory(param -> param.getValue().getValue().Mese);

        ObservableList<AlunnoForTable> alunni = FXCollections.observableArrayList();

        final RecursiveTreeItem root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        myTable.getColumns().setAll(colonnaCognome, colonnaNome, colonnaClasse, colonnaGita, colonnaImporto, colonnaMese);
        //end table
    }
}
