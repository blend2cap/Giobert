package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
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
    private JFXTreeTableView<AlunnoForTable> inserisciTable;

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
    ObservableList<AlunnoForTable> observableListForTable;

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
        ReviewInsertTable(alunno);
        addFinalDB(alunno);
    }

    public void ReviewInsertTable (Alunno alunno) {

        AlunnoForTable alunnoForTable = new AlunnoForTable(alunno.nomeCognome.getName(), alunno.nomeCognome.getSurname(),
                alunno.classe, alunno.gita.getLocation(), alunno.gita.getCost(), alunno.gita.getMonth());
        inserisciTable.setRoot(new TreeItem<>(alunnoForTable));
        inserisciTable.setShowRoot(false);
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
        inserisciTable.setRoot(root);
        inserisciTable.setShowRoot(false);
        inserisciTable.getColumns().setAll(colonnaCognome, colonnaNome, colonnaClasse, colonnaGita, colonnaImporto, colonnaMese);
        //end table
    }
}
