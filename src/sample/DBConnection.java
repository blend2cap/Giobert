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

    public ObservableList<String> getClassList() throws SQLException, ClassNotFoundException {
        ObservableList<String> classi = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Classi");
        while ( resultSet.next() ) classi.add(new String(resultSet.getString("classe")));
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
            locationCostArrayList.add(new LocationCost(resultSet.getString("location"), resultSet.getDouble("costo")));
        return locationCostArrayList;
    }

    public ObservableList<String> getAnni() throws SQLException, ClassNotFoundException {
        ObservableList<String> anniList = FXCollections.observableArrayList();
        if (con == null) getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Anno");
        while ( resultSet.next() )
            anniList.add(resultSet.getString("anno"));
        return anniList;
    }
}
