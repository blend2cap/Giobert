package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<AlunnoForTable> inserisciTable;

    @FXML
    private JFXTextField inserisciNome;

    @FXML
    private JFXTextField inserisciCognome;


    @FXML
    private JFXComboBox<String> gitaCombo;

    @FXML
    private JFXComboBox<String> classeCombo;

    @FXML
    private JFXComboBox<String> annoCombo;

    @FXML
    JFXButton aggiungiButton;

    ObservableList<String> elencoClassi;
    ObservableList<String> elencoGite;
    ObservableList<String> elencoAnni;
    public ObservableList<AlunnoForTable> observableListForTable;

    public ControllerInserisci() throws SQLException, ClassNotFoundException {
        elencoClassi=getClassListForComboBox();
        elencoGite=getGiteForCombo();
        elencoAnni=getAnniForComboBox();
    }


    public void AggiungiButton() throws SQLException, ClassNotFoundException {
        HashMap<String, Gita> map=GitaMap();
        String id = null;
        //find id
        for ( String o:map.keySet() )
            if (Objects.equals(map.get(o).getLocation(), gitaCombo.getValue())) {
                id=o;
                break;
            }
        Alunno alunno = new Alunno(new NameSurname(inserisciNome.getText(), inserisciCognome.getText()), classeCombo.getValue(),
                new Gita(map.get(id).getLocation(), map.get(id).getCost(), map.get(id).getMonth()), annoCombo.getValue());
        addStudenteDB(alunno.nomeCognome);
        addAnnoDB(annoCombo.getValue());
        //ReviewInsertTable(alunno);
        addFinalDB(alunno);
        //add alunno to Controller.alunni ObservableList<AlunnoFortable>
        AlunnoForTable alunnoForTable = new AlunnoForTable(alunno.nomeCognome.getName(), alunno.nomeCognome.getSurname(),
                alunno.classe, alunno.gita.getLocation(), alunno.gita.getCost(), alunno.gita.getMonth());
        Controller.alunni.add(alunnoForTable);
        observableListForTable.add(alunnoForTable);

        inserisciTable.setItems(observableListForTable);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo vuoto");
        inserisciNome.getValidators().add(validator);
        inserisciCognome.getValidators().add(validator);

        inserisciNome.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  inserisciNome.validate();
        });
        inserisciCognome.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  inserisciCognome.validate();
        });

        //Initialize ComboBox
        gitaCombo.setItems(elencoGite);
        classeCombo.setItems(elencoClassi);
        annoCombo.setItems(elencoAnni);

        //start TableView
        TableColumn<AlunnoForTable, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));

        TableColumn<AlunnoForTable, String> surnameColumn = new TableColumn<>("Cognome");
        surnameColumn.setMinWidth(150);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("Cognome"));

        TableColumn<AlunnoForTable, String> classColumn = new TableColumn<>("Classe");
        classColumn.setMinWidth(150);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("Classe"));

        TableColumn<AlunnoForTable, String> tripColumn = new TableColumn<>("Uscita");
        tripColumn.setMinWidth(150);
        tripColumn.setCellValueFactory(new PropertyValueFactory<>("Gita"));

        TableColumn<AlunnoForTable, String> importColumn = new TableColumn<>("Importo");
        importColumn.setMinWidth(150);
        importColumn.setCellValueFactory(new PropertyValueFactory<>("Importo"));

        TableColumn<AlunnoForTable, String> monthColumn = new TableColumn<>("Mese");
        monthColumn.setMinWidth(150);
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("Mese"));

        inserisciTable = new TableView<>();
        inserisciTable.setItems(observableListForTable);
        inserisciTable.getColumns().addAll(nameColumn, surnameColumn, classColumn, tripColumn, importColumn, monthColumn);
        //end table
    }
}
