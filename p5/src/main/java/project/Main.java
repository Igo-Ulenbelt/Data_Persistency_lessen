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
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        testOvchipkaartDAO(ovchipkaartDAO, reizigerDAO);
        testProductDAO(productDao, ovchipkaartDAO);

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

    // Test the ReizigerDAO
    public static void testReizigerDAO(ReizigerDAO reizigerDAO) {
        System.out.println("---- Test ReizigerDAO ----");

        // Test findAll method
        System.out.println("Testing findAll()");
        List<Reiziger> reizigers = reizigerDAO.findAll();
        for (Reiziger r : reizigers) {
            System.out.print(r);
        }

        // Test findById method
        System.out.println("\nTesting findById(1)");
        Reiziger reiziger = reizigerDAO.findById(1);
        if (reiziger != null) {
            System.out.print(reiziger);
        } else {
            System.out.println("Reiziger niet gevonden.");
        }

        // Test save method
        System.out.println("\nTesting save()");
        Reiziger newReiziger = new Reiziger(6, "J", null, "Janssen", Date.valueOf("1990-12-10"));
        boolean saved = reizigerDAO.save(newReiziger);
        System.out.println("Reiziger saved: " + saved);

        // Test update method
        System.out.println("\nTesting update()");
        newReiziger.setAchternaam("Jansen");
        boolean updated = reizigerDAO.update(newReiziger);
        System.out.println("Reiziger updated: " + updated);

        // Test delete method
        System.out.println("\nTesting delete()");
        boolean deleted = reizigerDAO.delete(newReiziger);
        System.out.println("Reiziger deleted: " + deleted + "\n");
    }

    public static void testAdresDAO(AdresDAO adresDAO, ReizigerDAO reizigerDAO) {
        System.out.println("---- Test AdresDAO ----");

        System.out.println("Testing findAll()");
        List<Adres> adressen = adresDAO.findAll();
        for (Adres adres : adressen) {
            System.out.print(adres);
        }

        // Test findByReiziger method
        System.out.println("\nTesting findByReiziger()");
        Reiziger reiziger = reizigerDAO.findById(1);
        if (reiziger != null) {
            Adres adres = adresDAO.findByReiziger(reiziger);
            System.out.print(adres);
        } else {
            System.out.print("Reiziger niet gevonden.");
        }

        // Test save method
        System.out.println("\nTesting save()");
        Reiziger newReiziger = new Reiziger(6, "J", null, "Janssen", Date.valueOf("1990-12-10"));
        reizigerDAO.save(newReiziger);
        Adres newAdres = new Adres(5, "1234AB", "10", "Hoofdstraat", "Utrecht", newReiziger);
        boolean saved = adresDAO.save(newAdres);
        System.out.println("Adres saved: " + saved);

        // Test update method
        System.out.println("\nTesting update()");
        newAdres.setWoonplaats("Amsterdam");
        boolean updated = adresDAO.update(newAdres);
        System.out.println("Adres updated: " + updated);

        // Test delete method
        System.out.println("\nTesting delete()");
        boolean deleted = adresDAO.delete(newAdres);
        System.out.println("Adres deleted: " + deleted);

        // delete reiziger
         reizigerDAO.delete(newReiziger);
    }

    // Test the OVChipkaartDAO
    public static void testOvchipkaartDAO(OvchipkaartDAO ovChipkaartDAO, ReizigerDAO reizigerDAO) {
        System.out.println("\n---- Test OVChipkaartDAO ----\n");

        // Find OVChipkaart by reiziger
        Reiziger reiziger = reizigerDAO.findById(2);
        System.out.println("Finding OVChipkaarten for reiziger with id 2: ");
        List<Ovchipkaart> ovchipkaarten = ovChipkaartDAO.findByReiziger(reiziger);
        if(ovchipkaarten.size() == 0) {
            System.out.println("No OVChipkaarten found for reiziger with id 2.");
        }else{
            for (Ovchipkaart ovchipkaart : ovchipkaarten) {
                System.out.println(ovchipkaart);
            }
        }
    }

    private static void testProductDAO(ProductDAO productDao, OvchipkaartDAO ovchipkaartDAO) {
        System.out.println("---- Test ProductDAO ----");

        // Test findByOVChipkaart method
        System.out.println("Testing findByOVChipkaart()");
        Ovchipkaart ovChipkaart = ovchipkaartDAO.findById(35283);
        List<Product> producten = productDao.findByOVChipkaart(ovChipkaart);
        if (producten.size() == 0) {
            System.out.println("No products found for ovchipkaart with id 35283.");
        } else {
            for (Product product : producten) {
                System.out.println(product);
            }
        }

        // Test save method
        System.out.println("Testing save()");
        Product newProduct = new Product(8, "Test", "Test product", 10.0);
        boolean saved = productDao.save(newProduct);
        System.out.println("Product saved: " + saved);

        // Test update method
        System.out.println("Testing update()");
        newProduct.setNaam("Test product");
        boolean updated = productDao.update(newProduct);
        System.out.println("Product updated: " + newProduct);

        // Test addOVChipkaart method
        System.out.println("Testing addOVChipkaart()");
        Ovchipkaart ovchipkaart = ovchipkaartDAO.findById(35283);
        boolean added = productDao.addOVChipkaart(newProduct, ovchipkaart, "actief");
        System.out.println("Product added to ovchipkaart: " + added);

        // Test deleteFromManyToMany method
        System.out.println("Testing deleteFromManyToMany()");
        boolean deletedFromManyToMany = productDao.deleteFromManyToMany(newProduct);
        System.out.println("Product deleted from many to many table: " + deletedFromManyToMany);

        // Test delete method
        System.out.println("Testing delete()");
        boolean deleted = productDao.delete(newProduct);
        System.out.println("Product deleted: " + deleted);

//        find all
        System.out.println("Testing findAll()");
        List<Product> producten1 = productDao.findAll();
        for (Product product : producten1) {
            System.out.println(product);
        }
    }
}
