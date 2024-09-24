package project.daopsql;

import project.dao.ReizigerDAO;
import project.domain.Adres;
import project.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    // Fields
    private Connection connection;

    // Constructor
    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    // Methods

    // Find all reizigers
    @Override
    public List<Reiziger> findAll() {
        String query = "SELECT * FROM reiziger";
        List<Reiziger> reizigers;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            reizigers = new ArrayList<>();

            while (set != null && set.next()) {
                Reiziger reiziger = new Reiziger(
                        set.getInt("reiziger_id"),
                        set.getString("voorletters"),
                        set.getString("tussenvoegsel"),
                        set.getString("achternaam"),
                        set.getDate("geboortedatum")
                );
                reizigers.add(reiziger);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reizigers;
    }

    // Find reizigers by id
    @Override
    public Reiziger findById(int id) {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set != null && set.next()) {
                return new Reiziger(
                        set.getInt("reiziger_id"),
                        set.getString("voorletters"),
                        set.getString("tussenvoegsel"),
                        set.getString("achternaam"),
                        set.getDate("geboortedatum")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    // Save an reizger
    @Override
    public boolean save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, (Date) reiziger.getGeboortedatum());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an adres
    @Override
    public boolean update(Reiziger reiziger) {
        String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, (Date) reiziger.getGeboortedatum());
            statement.setInt(5, reiziger.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an reiziger
    @Override
    public boolean delete(Reiziger reiziger) {
        String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

