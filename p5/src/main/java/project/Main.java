package project;

import project.dao.ProductDAO;
import project.daopsql.ProductDAOsql;
import project.domain.Ovchipkaart;
import project.domain.Product;
import project.domain.Reiziger;
import project.domain.Adres;
import project.dao.AdresDAO;
import project.dao.ReizigerDAO;
import project.daopsql.AdresDAOPsql;
import project.daopsql.ReizigerDAOPsql;
import project.dao.OvchipkaartDAO;
import project.daopsql.OvchipkaartDAOsql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        // Create DAO's
        ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
        AdresDAO adresDAO = new AdresDAOPsql(connection, reizigerDAO);
        OvchipkaartDAO ovchipkaartDAO = new OvchipkaartDAOsql(connection);
        ProductDAO productDao = new ProductDAOsql(connection);

        // Test DAO's
        testCodeDAO(reizigerDAO, adresDAO, ovchipkaartDAO, productDao);

        closeConnection();
    }

    //    create connection
    public static void getConnection() throws SQLException {
        // connect with postgres
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=lupos";
        connection = DriverManager.getConnection(url);
    }

    // Close connection
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Test code
    public static void testCodeDAO(ReizigerDAO reizigerDAO, AdresDAO adresDAO, OvchipkaartDAO ovchipkaartDAO, ProductDAO productdao) {
        Reiziger harry = new Reiziger(77, "H", "", "Boers", java.sql.Date.valueOf("1983-04-13"));
        Adres harrysAdres = new Adres(77, "8014EP", "10bis", "Tuinmeesterlaan", "Dalfsen", harry);

        Ovchipkaart harryOvkaart1 = new Ovchipkaart(77777, java.sql.Date.valueOf("2020-02-22"), 2, (float) 87.88, harry);
        Ovchipkaart harryOvkaart2 = new Ovchipkaart(77778, java.sql.Date.valueOf("2021-01-11"), 1, (float) 12.23, harry);

        Product dagkaartKind = new Product(102, "Dagkaart hond", "De hele dag op pad met je kleine monstertje", (float) 4.00);
        Product dagkaartBaby = new Product(103, "Dagkaart baby", "De hele dag op pad met je lieve baby", (float) 16.90);

        reizigerDAO.save(harry);
        adresDAO.save(harrysAdres);
        ovchipkaartDAO.save(harryOvkaart1);
        ovchipkaartDAO.save(harryOvkaart2);
        productdao.save(dagkaartKind);
        productdao.save(dagkaartBaby);

        productdao.addOVChipkaart(dagkaartKind, harryOvkaart1, "status");
        productdao.addOVChipkaart(dagkaartBaby, harryOvkaart1, "status");
        productdao.addOVChipkaart(dagkaartBaby, harryOvkaart2, "status");
        System.out.println("[Test] ReizigerDAO.save() & AdresDAO.save() & OvchipkaartDAO.save() & ProductDAO.save() werkt");

        harry.setAchternaam("Boers2");
        harrysAdres.setWoonplaats("Zwolle");
        harryOvkaart1.setSaldo((float) 100.00);
        dagkaartKind.setPrijs((float) 5.00);

        reizigerDAO.update(harry);
        adresDAO.update(harrysAdres);
        ovchipkaartDAO.update(harryOvkaart1);
        productdao.update(dagkaartKind);
        System.out.println("[Test] ReizigerDAO.update() & AdresDAO.update() & OvchipkaartDAO.update() & ProductDAO.update() werkt");

        List<Reiziger> reizigers = reizigerDAO.findAll();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:" );

        for (Reiziger r : reizigers) {
            r.setAdres(adresDAO.findByReiziger(r));
            List<Ovchipkaart> ovchipkaart = ovchipkaartDAO.findByReiziger(r);
            r.setOvchipkaarten(ovchipkaart);

            for (Ovchipkaart ov : ovchipkaart) {
                ov.setProducten(productdao.findByOVChipkaart(ov));
            }

            System.out.println(r + " ");
        }
        System.out.println();
        List<Product> producten = productdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");

        for (Product p : producten) {
            System.out.print(p);
        }

        productdao.deleteFromManyToMany(dagkaartKind);
        productdao.deleteFromManyToMany(dagkaartBaby);
        productdao.delete(dagkaartKind);
        productdao.delete(dagkaartBaby);

        adresDAO.delete(harrysAdres);
        ovchipkaartDAO.delete(harryOvkaart1);
        ovchipkaartDAO.delete(harryOvkaart2);
        reizigerDAO.delete(harry);
        System.out.println("\n\n[Test] ReizigerDAO.delete() & AdresDAO.delete() & OvchipkaartDAO.delete() & ProductDAO.delete() werkt");
    }
}


