package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    public Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public boolean existsById(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        boolean exists = resultSet.getInt(1) > 0;

        resultSet.close();
        statement.close();

        return exists;
    }

    public void save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        try {
            if (existsById(reiziger.getId())) {
                System.out.println("Reiziger with ID " + reiziger.getId() + " already exists.");
                return;
            }
            else{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());
            statement.executeUpdate();
            statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Reiziger reiziger) {
        try {
            if (existsById(reiziger.getId())) {
                String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, reiziger.getVoorletters());
                statement.setString(2, reiziger.getTussenvoegsel());
                statement.setString(3, reiziger.getAchternaam());
                statement.setDate(4, reiziger.getGeboortedatum());
                statement.setInt(5, reiziger.getId());
                statement.executeUpdate();
                statement.close();
                System.out.println("Reiziger updated");
            } else {
                System.out.println("Reiziger with ID " + reiziger.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Reiziger reiziger) {
        try {
            if (existsById(reiziger.getId())) {
                String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, reiziger.getId());
                statement.executeUpdate();
                statement.close();
                System.out.println("Reiziger deleted");
            } else {
                System.out.println("Reiziger with ID " + reiziger.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reiziger findById(int id) throws SQLException {
        Reiziger reiziger = null;

        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
        }

        resultSet.close();
        statement.close();

        return reiziger;
    }

    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE geboortedatum = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, Date.valueOf(datum));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
            reizigers.add(reiziger);
        }

        resultSet.close();
        statement.close();

        return reizigers;
    }

    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));
            reizigers.add(reiziger);
        }

        resultSet.close();
        statement.close();

        return reizigers;
    }
}
