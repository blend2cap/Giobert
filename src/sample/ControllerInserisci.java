package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    public ControllerInserisci() throws SQLException, ClassNotFoundException {
        elencoClassi=getClassListForComboBox();
        elencoGite=getGiteForCombo();
        elencoAnni=getAnniForComboBox();
    }


    public void AggiungiButton() throws SQLException, ClassNotFoundException {
        try {
            HashMap<String, Gita> map = GitaMap();
            String id = null;
            //find id
            for (String o : map.keySet())
                if (Objects.equals(map.get(o).getLocation(), gitaCombo.getValue())) {
                    id = o;
                    break;
                }
            Alunno alunno = new Alunno(new NameSurname(inserisciNome.getText(), inserisciCognome.getText()), classeCombo.getValue(),
                    new Gita(map.get(id).getLocation(), map.get(id).getCost(), map.get(id).getMonth()), annoCombo.getValue());
            addStudenteDB(alunno.nomeCognome);
            addAnnoDB(annoCombo.getValue());
            //ReviewInsertTable(alunno);
            addFinalDB(alunno);
            //add alunno to Controller.alunni ObservableList<AlunnoFortable>
            AlunnoForTable alunnoForTable = new AlunnoForTable(alunno.nomeCognome.getName().toString(), alunno.nomeCognome.getSurname().toString(),
                    alunno.classe, alunno.gita.getLocation(), alunno.gita.getCost(), alunno.gita.getMonth());
            Controller.alunni.add(alunnoForTable);
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Alunno non inserito!");
            alert.showAndWait();
        }
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
    }
}
