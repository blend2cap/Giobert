package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerInserisci extends DBConnection implements Initializable {

    public ControllerInserisci() throws SQLException {
    }

    @FXML
    private JFXTextField InsAS;

    @FXML
    private JFXTextField inserisciNome;

    @FXML
    private JFXTextField inserisciCognome;

    @FXML
    private JFXTextField inserisciImporto;

    @FXML
    private JFXComboBox<String> inserisciGitaCombo;

    @FXML
    private JFXComboBox<String> inserisciClasseCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inserisciClasseCombo.setItems(getClassListForComboBox());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            inserisciGitaCombo.setItems(getGiteForCombo());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
