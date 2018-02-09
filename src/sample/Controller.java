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
    private JFXTreeTableView<Alunno> myTable;
    @FXML
    private JFXComboBox ClasseCombo;
    @FXML
    private JFXComboBox AnnoScolasticoCombo;

    ObservableList<String> elencoClassi=getClassList();
    ObservableList<String> elencoAnniScolastici = getAnni();

    public Controller() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        ObservableList<Alunno> alunni = FXCollections.observableArrayList();
        alunni.add(new Alunno("Riccardo", "Capellino", "4AI", "Francoforte", "300"));
        alunni.add(new Alunno("Zoky", "Micevsky", "4AI", "Francoforte", "300"));
        final RecursiveTreeItem root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        myTable.getColumns().setAll(colonnaNome, colonnaCognome, colonnaClasse, colonnaGita, colonnaImporto);
        //end table
        //initialize ComboBoxes
        ClasseCombo.setItems(elencoClassi);
       // ClasseCombo.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> );
        AnnoScolasticoCombo.setItems(elencoAnniScolastici);

    }

    public void PopulateChangedTable (String newValue) {

    }
}
