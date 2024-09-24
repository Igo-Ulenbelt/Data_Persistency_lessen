package project.daopsql;

import project.dao.AdresDAO;
import project.dao.ReizigerDAO;
import project.domain.Adres;
import project.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    // attributes
    private final Connection connection;
    private final ReizigerDAO rdao;

    // Constructor
    public AdresDAOPsql(Connection connection, ReizigerDAO rdao) {
        this.connection = connection;
        this.rdao = rdao;
    }

    // Methods

    // Find all adressen
    @Override
    public List<Adres> findAll() {
        String query = "SELECT * FROM adres";
        List<Adres> adressen;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            adressen = new ArrayList<>();

            while (set != null && set.next()) {
                Reiziger reiziger = rdao.findById(set.getInt("reiziger_id"));
                Adres adres = new Adres(
                        set.getInt("adres_id"),
                        set.getString("postcode"),
                        set.getString("huisnummer"),
                        set.getString("straat"),
                        set.getString("woonplaats"),
                        reiziger
                );
                adressen.add(adres);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return adressen;
    }

    // Find an adres by reiziger
    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            ResultSet set = statement.executeQuery();
            if (set != null && set.next()) {
                return new Adres(
                        set.getInt("adres_id"),
                        set.getString("postcode"),
                        set.getString("huisnummer"),
                        set.getString("straat"),
                        set.getString("woonplaats"),
                        reiziger
                );
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    // Save an adres
    @Override
    public boolean save(Adres adres) {

        String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Update an adres
    @Override
    public boolean update(Adres adres) {
        String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, adres.getPostcode());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getReiziger().getId());
            statement.setInt(6, adres.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Delete an adres
    @Override
    public boolean delete(Adres adres) {
        String query = "DELETE FROM adres WHERE adres_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, adres.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

