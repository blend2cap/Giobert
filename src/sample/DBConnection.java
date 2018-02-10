package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Repositories.Gita;
import sample.Repositories.NameSurname;

import java.sql.*;
import java.util.*;

public class DBConnection {
    private static Connection con;
    private static boolean hasData = false;

    public DBConnection() throws SQLException {
    }

    public ResultSet displayData() throws ClassNotFoundException, SQLException {
        if (con == null)
            getConnection();
        Statement statement = con.createStatement();
        return statement.executeQuery("SELECT Nome, Cognome, Classe, Gita, Importo from AS");
    }

    public void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:DB.db");
    }

    public ObservableList<String> getClassListForComboBox() throws SQLException, ClassNotFoundException {
        ObservableList<String> classi = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Classi");
        while ( resultSet.next() ) classi.add(resultSet.getString("classe"));
        return classi;
    }

    public ObservableList<NameSurname> getNameSurnameList() throws SQLException, ClassNotFoundException {
        ObservableList<NameSurname> nameSurnames = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Studenti");
        while ( resultSet.next() )
            nameSurnames.add(new NameSurname(resultSet.getString("nome"), resultSet.getString("cognome")));
        return nameSurnames;
    }

    public ObservableList<Gita> getLocationCostList() throws SQLException, ClassNotFoundException {
        ObservableList<Gita> locationCostArrayList = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Gite");
        while ( resultSet.next() )
            locationCostArrayList.add(new Gita(resultSet.getString("location"), resultSet.getDouble("cost")));
        return locationCostArrayList;
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
        while ( resultSet.next() )  map.put(resultSet.getString("id"), new Gita(resultSet.getString("location"), resultSet.getDouble("cost")));
        return map;
    }

    public ObservableList<Alunno> getVisualizzaAlunni (String classe, String annoScolastico) throws SQLException, ClassNotFoundException {
        ObservableList<Alunno> alunnoObservableList = FXCollections.observableArrayList();
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
            alunnoObservableList.add(new Alunno(AlunnoMap().get(idAlunno).getName(), AlunnoMap().get(idAlunno).getSurname(), classe, GitaMap().get(idGita).getLocation(), GitaMap().get(idGita).getCost()));
        }
        return alunnoObservableList;
    }
}
