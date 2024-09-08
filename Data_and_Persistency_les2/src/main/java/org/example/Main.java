package org.example;

import org.example.Reiziger;
import org.example.ReizigerDAO;
import org.example.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        try {
            Connection conn = getConnection();

            // Instantiate ReizigerDAOPsql with the connection and call testReizigerDAO
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
            testReizigerDAO((ReizigerDAO) rdao);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=lupos";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    // Proper method signature for testReizigerDAO
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Fetch all travelers from the database
        List<Reiziger> reizigers = rdao.findAll();

        // Create
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Initially " + reizigers.size() + " travelers, after ReizigerDAO.save() ");
        rdao.save(sietske);

        Reiziger test =  new Reiziger(78, "T", "", "test", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Initially " + reizigers.size() + " travelers, after ReizigerDAO.save() ");
        rdao.save(test);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " travelers\n");

        //update traveler
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        System.out.println("[Test] ReizigerDAO.update() updated Sietske's last name to Jansen:\n");

//      delete
        rdao.delete(test);
        System.out.println("[Test] ReizigerDAO.delete() deleted Sietske from the database:\n");

        List<Reiziger> allReizigers = rdao.findAll();
        System.out.println("\n[Test] ReizigerDAO.findAll() gives the following travelers:");
        for (Reiziger r : allReizigers) {
            System.out.println(r);
        }

        // Find travelers by date
        String date = "2002-09-17";
        List<Reiziger> allReizigersFindByGbdatum = rdao.findByGbdatum(date);
        System.out.println("\n[Test] ReizigerDAO.findByGbdatum() gives the following travelers with the date " + date + ":");
        for (Reiziger r : allReizigersFindByGbdatum) {
            System.out.println(r);
        }

        // Find travelers by id
        int id = 2;
        Reiziger reizigersFindById = rdao.findById(id);
        System.out.println("\n[Test] ReizigerDAO.findById() gives the following traveler with the id " + id + ":");
        System.out.println(reizigersFindById);
    }
}
