package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller extends DBConnection implements Initializable {

    @FXML
    public JFXTreeTableView<Alunno> myTable;
    @FXML
    public JFXTextField AScolastico;
    public JFXComboBox ClasseCombo;

    ObservableList<String> elencoClassi;

    public Controller() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Alunno, String> nomeAlunno = new JFXTreeTableColumn<>("Nome");
        nomeAlunno.setPrefWidth(150);
        nomeAlunno.setCellValueFactory(param -> param.getValue().getValue().Nome);

        JFXTreeTableColumn<Alunno, String> cognomeAlunno = new JFXTreeTableColumn<>("Cognome");
        nomeAlunno.setPrefWidth(150);
        nomeAlunno.setCellValueFactory(param -> param.getValue().getValue().Cognome);

        JFXTreeTableColumn<Alunno, String> Classe = new JFXTreeTableColumn<>("Classe");
        nomeAlunno.setPrefWidth(150);
        nomeAlunno.setCellValueFactory(param -> param.getValue().getValue().Classe);

        JFXTreeTableColumn<Alunno, String> Gita = new JFXTreeTableColumn<>("Gita");
        nomeAlunno.setPrefWidth(150);
        nomeAlunno.setCellValueFactory(param -> param.getValue().getValue().Gita);

        JFXTreeTableColumn<Alunno, String> Importo = new JFXTreeTableColumn<>("Importo");
        nomeAlunno.setPrefWidth(150);
        nomeAlunno.setCellValueFactory(param -> param.getValue().getValue().Importo);

        ObservableList<Alunno> alunni = FXCollections.observableArrayList();
        /*
        try {
            alunni = GetAllDataFromAS(AScolastico.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        */
        final TreeItem<Alunno> root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.getColumns().setAll(nomeAlunno, cognomeAlunno, Classe, Gita, Importo);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        //end table


        try {
            elencoClassi=getClassList();
            ClasseCombo = new JFXComboBox();
            System.out.println(elencoClassi);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
