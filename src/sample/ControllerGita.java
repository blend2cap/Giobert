package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sample.Repositories.Gita;

import java.awt.geom.Arc2D;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerGita extends DBConnection implements Initializable  {

    private Controller mainController;

    @FXML
    private JFXTextField destinazione;

    @FXML
    private JFXTextField costo;

    @FXML
    private JFXTextField mese;

    public ControllerGita() throws SQLException {

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

        if (checkStringField(destinazione) && checkCostField(costo) && checkStringField(mese))
            addGitaDB(new Gita(destinazione.getText(), Double.parseDouble(costo.getText()), mese.getText()));
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Gita non inserita!");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            if (!newValue)  costo.validate();
        });
        mese.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)  mese.validate();
        });

    }
}
