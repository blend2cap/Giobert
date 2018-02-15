package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.Repositories.Alunno;
import sample.Repositories.Gita;
import sample.Repositories.NameSurname;

import java.sql.*;
import java.util.*;

public class DBConnection {
    private static Connection con;
    private static boolean hasData = false;

    public DBConnection() throws SQLException {
    }

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:DB.db");
    }

    public ObservableList<String> getCognome() throws SQLException, ClassNotFoundException {
        ObservableList<String> list = FXCollections.observableArrayList();
        if (con==null)  getConnection();
        Statement statement=con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Studenti");
        while ( resultSet.next() )  list.add(resultSet.getString("cognome"));
        return list;
    }

    public ObservableList<String> getClassListForComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> classi = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Classi");
        while ( resultSet.next() ) classi.add(resultSet.getString("classe"));
        return classi;
    }


    public ObservableList<String> getAnniForComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> anniList = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Anno");
        while ( resultSet.next() )
            anniList.add(resultSet.getString("anno"));
        return anniList;
    }

    public ObservableList<String> getGiteForCombo() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        if (con==null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Gite");
        while ( resultSet.next() )
            observableList.add(resultSet.getString("location"));

        return  observableList;
    }


    //map to use in getVisualizzaAlunni
    public HashMap<String, NameSurname> AlunnoMap () throws SQLException, ClassNotFoundException {
        HashMap<String, NameSurname> map = new HashMap<>();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Studenti");
        while ( resultSet.next() )  map.put(resultSet.getString("id"), new NameSurname(resultSet.getString("nome"), resultSet.getString("cognome")));
        return map;
    }

    public  HashMap<String, Gita> GitaMap () throws SQLException, ClassNotFoundException {
        HashMap<String, Gita> map = new HashMap<>();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Gite");
        while ( resultSet.next() )  map.put(resultSet.getString("id"), new Gita(resultSet.getString("location"), resultSet.getDouble("cost"), resultSet.getString("month")));
        return map;
    }

    public ObservableList<AlunnoForTable> getVisualizzaAlunni (String classe, String annoScolastico) throws SQLException, ClassNotFoundException {
        ObservableList<AlunnoForTable> alunnoForTableObservableList = FXCollections.observableArrayList();
        if (con == null) getConnection();

        Statement statement = con.createStatement();
        ResultSet classResultSet = statement.executeQuery("SELECT * FROM Classi WHERE classe='"+classe+"'");
        String classeId=classResultSet.getString("id"); //stores the id of the selected class

        ResultSet annoResultSet = statement.executeQuery("SELECT * FROM Anno WHERE anno='"+annoScolastico+"'");
        String annoId=annoResultSet.getString("id"); //stores the id of the selected school year

        ResultSet finalResultSet = statement.executeQuery("SELECT * FROM Final WHERE classeID='"+classeId+"' AND annoID='"+annoId+"'");
        while ( finalResultSet.next() ) {
            String idAlunno= finalResultSet.getString("studenteID");
            String idGita = finalResultSet.getString("gitaID");
            alunnoForTableObservableList.add(new AlunnoForTable(AlunnoMap().get(idAlunno).getName(), AlunnoMap().get(idAlunno).getSurname(), classe, GitaMap().get(idGita).getLocation(), GitaMap().get(idGita).getCost(), GitaMap().get(idGita).getMonth()));
        }
        return alunnoForTableObservableList;
    }

    public void addGitaDB(Gita gita) throws SQLException, ClassNotFoundException {
        if (con==null)   getConnection();
        PreparedStatement check = con.prepareStatement("SELECT * FROM Gite WHERE location=?;");
        check.setString(1, gita.getLocation());
        ResultSet resultSet = check.executeQuery();
        if (!resultSet.next()){
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Gite values(?,?,?,?);");
            preparedStatement.setString(2, gita.getLocation());
            preparedStatement.setString(3, gita.getCost().toString());
            preparedStatement.setString(4, gita.getMonth());
            preparedStatement.execute();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Gita già inserita");
            alert.showAndWait();
        }
    }

    public void addClasseDB(String classe) throws SQLException, ClassNotFoundException {
        if (con==null) getConnection();
        PreparedStatement check = con.prepareStatement("SELECT * FROM Classi WHERE classe=?;");
        check.setString(1, classe);
        ResultSet resultSet = check.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Classi values(?,?);");
            preparedStatement.setString(2, classe);
            preparedStatement.execute();
        }
        else
         {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Classe già inserita");
            alert.showAndWait();
        }
    }

    public void addStudenteDB (NameSurname nameSurname) throws SQLException, ClassNotFoundException {
        if (con==null)  getConnection();
        PreparedStatement check = con.prepareStatement("SELECT id FROM Studenti WHERE nome=? AND cognome=?;");
        check.setString(1, nameSurname.getName());
        check.setString(2, nameSurname.getSurname());
        ResultSet resultSet =check.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO Studenti values(?,?,?);");
            preparedStatement.setString(2, nameSurname.getName());
            preparedStatement.setString(3, nameSurname.getSurname());
            preparedStatement.execute();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Alunno già inserito");
            alert.showAndWait();
        }
    }

    public void addAnnoDB(String anno) throws SQLException, ClassNotFoundException {
        if (con==null)   getConnection();
        PreparedStatement check = con.prepareStatement("SELECT * FROM Anno WHERE anno=?;");
        check.setString(1, anno);
        ResultSet resultSet=  check.executeQuery();
         if (!resultSet.next()){
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Anno values(?,?);");
            preparedStatement.setString(2, anno);
            preparedStatement.execute();
        }

    }

    public void addFinalDB(Alunno alunno) throws SQLException, ClassNotFoundException {
        if (con==null)  getConnection();

        PreparedStatement getIDStudente = con.prepareStatement("SELECT id FROM Studenti WHERE nome=? AND cognome=?;"); //this is the problem
        getIDStudente.setString(1, alunno.nomeCognome.getName());
        getIDStudente.setString(2, alunno.nomeCognome.getSurname());
        ResultSet resultSetStudente = getIDStudente.executeQuery();
        String idStudente=resultSetStudente.getString("id");

        PreparedStatement getIDClasse = con.prepareStatement("SELECT id FROM Classi WHERE classe=?;");
        getIDClasse.setString(1, alunno.classe);
        ResultSet resultSetClasse = getIDClasse.executeQuery();
        String idClasse = resultSetClasse.getString("id");

        PreparedStatement getIDGita = con.prepareStatement("SELECT id FROM Gite WHERE location=?;");
        getIDGita.setString(1, alunno.gita.getLocation());
        ResultSet resultSetGita = getIDGita.executeQuery();
        String idGita=resultSetGita.getString("id");

        PreparedStatement getIDAnno = con.prepareStatement("SELECT id FROM Anno WHERE anno=?;");
        getIDAnno.setString(1, alunno.annoScolastico);
        ResultSet resultSetAnno = getIDAnno.executeQuery();
        String idAnno = resultSetAnno.getString("id");

        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Final values(?,?,?,?,?);");
        preparedStatement.setString(2, idStudente);
        preparedStatement.setString(3, idClasse);
        preparedStatement.setString(4, idGita);
        preparedStatement.setString(5,idAnno);
        preparedStatement.execute();
    }
}
