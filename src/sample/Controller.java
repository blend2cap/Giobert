package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller extends DBConnection implements Initializable, ChangeListener {


    @FXML
    private JFXTextField cercaAlunno;
    @FXML
    private JFXTreeTableView<AlunnoForTable> myTable;
    @FXML
    private JFXComboBox ClasseCombo;
    @FXML
    private JFXComboBox AnnoScolasticoCombo;
    @FXML
    private Label totale;
    @FXML
    MenuItem inserisciGita = new MenuItem();
    @FXML
    MenuItem inserisciClasse = new MenuItem();
    @FXML
    MenuItem inserisciAnno = new Menu();
    @FXML
    MenuItem inserisciAlunno = new MenuItem();
    @FXML
    MenuItem eliminaDati = new MenuItem();

    static ObservableList<String> elencoClassi;
    static ObservableList<String> elencoAnniScolastici;
    static ObservableList<AlunnoForTable> alunni = FXCollections.observableArrayList();
    private ObservableList<String> autoCompletionList;

    public Controller() throws SQLException, ClassNotFoundException {
        elencoClassi=getClassListForComboBox();
        elencoAnniScolastici=getAnniForComboBox();
        autoCompletionList=getCognome();
        autoCompletionList.addAll(getGiteForCombo());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Cerca textfield
        TextFields.bindAutoCompletion(cercaAlunno, autoCompletionList);

        cercaAlunno.textProperty().addListener((observable, oldValue, newValue) ->
                myTable.setPredicate(alunnoTreeItem -> alunnoTreeItem.getValue().Cognome.getValue().contains(newValue) ||
                                                       alunnoTreeItem.getValue().Gita.getValue().contains(newValue)));

        cercaAlunno.textProperty().addListener((observable, oldValue, newValue) -> {
            //calcolo totale spese
            Double tot=0.0;
            for (int i=0; i<myTable.getCurrentItemsCount(); i++){
                tot+=Double.parseDouble(myTable.getTreeItem(i).getValue().getImporto());
            }
            totale.setText("€"+tot.toString());
        });


        //Table
        JFXTreeTableColumn<AlunnoForTable, String> colonnaCognome = new JFXTreeTableColumn<>("Cognome");
        colonnaCognome.setPrefWidth(150);
        colonnaCognome.setCellValueFactory(param -> param.getValue().getValue().Cognome);

        JFXTreeTableColumn<AlunnoForTable, String> colonnaNome = new JFXTreeTableColumn<>("Nome");
        colonnaNome.setPrefWidth(150);
        colonnaNome.setCellValueFactory(param -> param.getValue().getValue().Nome);


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



        final RecursiveTreeItem root = new RecursiveTreeItem<>(alunni, RecursiveTreeObject::getChildren);
        myTable.setRoot(root);
        myTable.setShowRoot(false);
        myTable.getColumns().setAll(colonnaCognome, colonnaNome, colonnaClasse, colonnaGita, colonnaImporto, colonnaMese);
        //end table

        //initialize ComboBoxes
        ClasseCombo.setItems(elencoClassi);
        ClasseCombo.getSelectionModel().selectedItemProperty().addListener(this);
        AnnoScolasticoCombo.setItems(elencoAnniScolastici);
        AnnoScolasticoCombo.getSelectionModel().selectedItemProperty().addListener(this);

    }

    public void eliminaButton() throws SQLException, ClassNotFoundException {
        TreeItem<AlunnoForTable> selectedItem = myTable.getSelectionModel().getSelectedItem();
        deleteAlunnoFinal(selectedItem.getValue());
    }

    @FXML
    public void InserisciGita() throws IOException {
        Stage stage = new Stage();
        ControllerMenuInserisci.showInsClasse=false;
        ControllerMenuInserisci.showInsGita=true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciGita.fxml"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("InserisciGita.fxml"))));
        stage.setTitle("Inserisci Gita");
        stage.setResizable(false);
        stage.show();
        Parent root = loader.load();
        ControllerMenuInserisci controllerMenuInserisci = loader.getController();

    }

    public void InserisciClasse() throws IOException {
        Stage stage = new Stage();
        ControllerMenuInserisci.showInsGita=false;
        ControllerMenuInserisci.showInsAnno=false;
        ControllerMenuInserisci.showInsClasse=true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciClasse.fxml"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("InserisciClasse.fxml"))));
        stage.setTitle("Inserisci Classe");
        stage.setResizable(false);
        stage.show();
        Parent root = loader.load();
        ControllerMenuInserisci controllerMenuInserisci = loader.getController();
    }

    public void InserisciAnno() throws IOException {
        Stage stage = new Stage();
        ControllerMenuInserisci.showInsClasse=false;
        ControllerMenuInserisci.showInsGita=false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciAnno.fxml"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("InserisciAnno.fxml"))));
        stage.setTitle("Inserisci Anno");
        stage.setResizable(false);
        stage.show();
        Parent root = loader.load();
        ControllerMenuInserisci controllerMenuInserisci = loader.getController();
    }

    public void InserisciAlunno() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InserisciTab.fxml"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("InserisciTab.fxml"))));
        stage.setTitle("Inserisci Alunno");
        stage.setResizable(false);
        stage.show();
        Parent root = loader.load();
        ControllerInserisci controllerMenuInserisci = loader.getController();
    }

    public void EliminaDati() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("delOptions.fxml"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("delOptions.fxml"))));
        stage.setTitle("Elimina Dati");
        stage.setMaxWidth(820);
        stage.show();
        Parent root = loader.load();
        ControllerElimina controllerElimina= loader.getController();
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        try {
            if ((ClasseCombo.getValue() != null) && (AnnoScolasticoCombo.getValue() != null)) {
                ObservableList<AlunnoForTable> observableList = getVisualizzaAlunni(ClasseCombo.getValue().toString(), AnnoScolasticoCombo.getValue().toString());
                RecursiveTreeItem root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);
                myTable.setRoot(root);
                myTable.setShowRoot(false);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
