package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sample.Repositories.Gita;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerMenuInserisci extends DBConnection implements Initializable  {

//InserisciGita.fxml
    @FXML
    private JFXTextField destinazione;

    @FXML
    private JFXTextField costo;

    @FXML
    private JFXTextField mese;

    //InserisciClasse.fxml
    @FXML
    private JFXTextField classe;

    public static boolean showInsGita=false;
    public static boolean showInsClasse=false;

    public ControllerMenuInserisci() throws SQLException {

    }

    public boolean checkCostField(JFXTextField field){
        try {
            Double cost = Double.parseDouble(field.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public boolean checkStringField(JFXTextField field){
        try {
            Double number = Double.parseDouble(field.getText());
            return false;
        } catch (Exception e) {
            return  true;
        }
    }
    public void CreateGitaObject() throws SQLException, ClassNotFoundException {
        ObservableList<String> list = getGiteForCombo();
        if (checkStringField(destinazione) && checkCostField(costo) && checkStringField(mese) && !list.contains(destinazione.getText()))
            addGitaDB(new Gita(destinazione.getText(), Double.parseDouble(costo.getText()), mese.getText()));
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Gita non inserita!");
            alert.showAndWait();
        }
    }

    public void CreateClasseString() throws SQLException, ClassNotFoundException {
        ObservableList<String> list = getClassListForComboBox();
        if (checkStringField(classe) && !list.contains(classe.getText()))
            addClasseDB(classe.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Classe non inserita!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (showInsGita) {
            RequiredFieldValidator emptyValidator = new RequiredFieldValidator();
            NumberValidator numberValidator = new NumberValidator();

            destinazione.getValidators().add(emptyValidator);
            costo.getValidators().add(emptyValidator);
            costo.getValidators().add(numberValidator);
            mese.getValidators().add(emptyValidator);

            emptyValidator.setMessage("Campo vuoto!");
            numberValidator.setMessage("Inserire un Numero!");

            destinazione.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) destinazione.validate();

            });
            costo.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) costo.validate();
            });
            mese.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) mese.validate();
            });
        }

        //InserisciClasse.fxml
        if (showInsClasse) {
            RequiredFieldValidator emptyValidator = new RequiredFieldValidator();
            emptyValidator.setMessage("Campo vuoto!");
            classe.getValidators().add(emptyValidator);
            classe.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) classe.validate();
            });
        }
    }
}
