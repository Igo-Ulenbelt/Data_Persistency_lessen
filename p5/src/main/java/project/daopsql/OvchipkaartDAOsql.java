package project.daopsql;

import project.dao.ReizigerDAO;
import project.domain.Ovchipkaart;
import project.domain.Reiziger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OvchipkaartDAOsql implements project.dao.OvchipkaartDAO {
    // attributes
    private Connection connection;

    // Constructor
    public OvchipkaartDAOsql(Connection connection) {
        this.connection = connection;
    }

    // Methods
    // Find all ovchipkaarten by reiziger
    public List<Ovchipkaart> findByReiziger(Reiziger reiziger) {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        List<Ovchipkaart> ovchipkaarten;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reiziger.getId());
            ResultSet set = statement.executeQuery();
            ovchipkaarten = new ArrayList<>();

            while (set != null && set.next()) {
                Ovchipkaart ovchipkaart = new Ovchipkaart(
                        set.getInt("kaart_nummer"),
                        set.getDate("geldig_tot"),
                        set.getInt("klasse"),
                        set.getDouble("saldo"),
                        reiziger
                );
                ovchipkaarten.add(ovchipkaart);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ovchipkaarten;
    }

    // Find by id
    public Ovchipkaart findById(int id) {
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set != null && set.next()) {
                ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
                Reiziger reiziger = reizigerDAO.findById(set.getInt("reiziger_id"));
                return new Ovchipkaart(
                        set.getInt("kaart_nummer"),
                        set.getDate("geldig_tot"),
                        set.getInt("klasse"),
                        set.getDouble("saldo"),
                        reiziger
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
