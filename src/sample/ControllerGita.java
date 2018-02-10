package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Repositories.Gita;

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

    public void CreateGitaObject() throws SQLException, ClassNotFoundException {
        addGitaDB(new Gita(destinazione.getText(), Double.parseDouble(costo.getText()), mese.getText()));
    }

   public void injectMainController(Controller mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
