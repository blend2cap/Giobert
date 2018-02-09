package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller extends DBConnection implements Initializable, javafx.beans.value.ChangeListener {

    @FXML
    private JFXTreeTableView<Alunno> myTable;
    @FXML
    private JFXComboBox ClasseCombo;
    @FXML
    private JFXComboBox AnnoScolasticoCombo;

    ObservableList<String> elencoClassi= getClassListForComboBox();
    ObservableList<String> elencoAnniScolastici = getAnniForComboBox();

    ObservableValue<String> newValueClasse;
    ObservableValue<String> newValueAnno;

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

        final RecursiveTreeItem root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        myTable.getColumns().setAll(colonnaNome, colonnaCognome, colonnaClasse, colonnaGita, colonnaImporto);
        //end table
        newValueClasse.addListener(this);
        newValueAnno.addListener(this);
        //initialize ComboBoxes
        ClasseCombo.setItems(elencoClassi);
        ClasseCombo.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> newValueClasse=newValue);
        AnnoScolasticoCombo.setItems(elencoAnniScolastici);
        AnnoScolasticoCombo.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> newValueAnno=newValue.toString());

    }

    public void PopulateChangedTable (String newValueClasse, String newValueAnno) throws SQLException, ClassNotFoundException {
        myTable.getColumns().setAll((TreeTableColumn<Alunno, ?>) getVisualizzaAlunni(newValueClasse, newValueAnno));
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            myTable.getColumns().setAll((TreeTableColumn<Alunno, ?>) getVisualizzaAlunni(newValueClasse.toString(), newValueAnno.toString()));
            System.out.println(newValueClasse);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
