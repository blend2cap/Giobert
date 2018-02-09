package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import sample.Repositories.LocationCost;
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

    public ObservableList<LocationCost> getLocationCostList() throws SQLException, ClassNotFoundException {
        ObservableList<LocationCost> locationCostArrayList = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Gite");
        while ( resultSet.next() )
            locationCostArrayList.add(new LocationCost(resultSet.getString("location"), resultSet.getDouble("cost")));
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

    public  HashMap<String, LocationCost> GitaMap () throws SQLException, ClassNotFoundException {
        HashMap<String, LocationCost> map = new HashMap<>();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Gite");
        while ( resultSet.next() )  map.put(resultSet.getString("id"), new LocationCost(resultSet.getString("location"), resultSet.getDouble("cost")));
        return map;
    }

    public ObservableList<Alunno> getVisualizzaAlunni (String classe, String annoScolastico) throws SQLException, ClassNotFoundException {
        ObservableList<Alunno> alunnoObservableList = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Classi WHERE classe='"+classe+"'");
        String classeId=resultSet.getString("id"); //stores the id of the selected class
        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Anno WHERE anno='"+annoScolastico+"'");
        String annoId=resultSet.getString("id"); //stores the id of the selected school year
        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Final WHERE classeID='"+classeId+"' AND annoID='"+annoId+"'");
        while ( resultSet2.next() )
            alunnoObservableList.add(new Alunno(AlunnoMap().get("id").getName(), AlunnoMap().get("id").getSurname(), classe, GitaMap().get("id").getLocation(), GitaMap().get("id").getCost() ));
        return alunnoObservableList;
    }
}
