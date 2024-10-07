package opdracht;


import opdracht.DAOHibernate.AdresDAOHibernate;
import opdracht.DAOHibernate.OvchipkaartDAOHibernate;
import opdracht.DAOHibernate.ProductDAOHibernate;
import opdracht.DAOHibernate.ReizigerDAOHibernate;
import opdracht.dao.AdresDAO;
import opdracht.dao.OvchipkaartDAO;
import opdracht.dao.ProductDAO;
import opdracht.dao.ReizigerDAO;
import opdracht.domain.Ovchipkaart;
import opdracht.domain.Product;
import opdracht.domain.Reiziger;
import project.domain.Adres;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;
import java.util.List;

public class Main {
    // Create an EntityManagerFactory when the application is started
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ovchipkaartPU");

    public static void main(String[] args) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // add DAOS
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(entityManager);
        AdresDAO adresDAO = new AdresDAOHibernate(entityManager);
        OvchipkaartDAO ovchipkaartDAO = new OvchipkaartDAOHibernate(entityManager);
        ProductDAO productDAO = new ProductDAOHibernate(entityManager);
        // Test DAO's
        testReizigerDAO(reizigerDAO, adresDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        testOvchipkaartDAO(ovchipkaartDAO, reizigerDAO);
        testProductDAO(productDAO, ovchipkaartDAO);

        entityManager.close();
        entityManagerFactory.close();
    }

    // Test the ReizigerDAO
    private static void testReizigerDAO(ReizigerDAO rdao, AdresDAO adresDAO) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        Adres adres1 = adresDAO.findByReiziger(rdao.findById(1));

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.print(r);
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum), adres1, null);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");
        System.out.println("[Test] ReizigerDAO.save() heeft deze reiziger toegevoegd:");
        System.out.println(sietske);

        // Update reiziger
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.update() heeft de achternaam van de reiziger aangepast:");
        System.out.println(reizigers.get(reizigers.size() - 1));

        // Delete reiziger
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    private static void testAdresDAO(AdresDAO rdao, ReizigerDAO reizigerDAO) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        Adres adres1 = rdao.findByReiziger(reizigerDAO.findById(1));

        // Haal alle reizigers op uit de database
        List<Adres> adressen = rdao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.print(a);
        }

        // Maak een nieuwe adres aan en persisteer deze in de database
        Reiziger reiziger = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf("1981-03-14"), adres1, null);
        reizigerDAO.save(reiziger);

        Adres adres = new Adres(77, "1234AB", "12", "Straat", "Plaats", reiziger);
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        rdao.save(adres);
        adressen = rdao.findAll();
        System.out.println(adressen.size() + " adressen");
        System.out.println("[Test] AdresDAO.save() heeft deze adres toegevoegd:");
        System.out.println(adres);

        // Update adres
        adres.setWoonplaats("Hilversum");
        rdao.update(adres);
        adressen = rdao.findAll();
        System.out.println("[Test] AdresDAO.update() heeft de plaats van de adres aangepast:");
        System.out.println(adressen.get(adressen.size() - 1));

        // Delete adres
        System.out.print("[Test] Eerst " + adressen.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(adres);
        adressen = rdao.findAll();
        System.out.println(adressen.size() + " reizigers\n");

        // get adres by reiziger
        System.out.println("[Test] AdresDAO.findByReiziger() geeft de volgende adres:");
        Reiziger reiziger1 = reizigerDAO.findById(1);
        Adres adresByReiziger = rdao.findByReiziger(reiziger1);
        if(adresByReiziger != null) {
            System.out.println(adresByReiziger);
        } else {
            System.out.println("Geen adres gevonden");
        }

        reizigerDAO.delete(reiziger);
    }

    private static void testOvchipkaartDAO(OvchipkaartDAO rdao, ReizigerDAO reizigerDAO) throws SQLException {
        System.out.println("---------- Test OvchipkaartDAO -------------");

        System.out.println("[Test] Ovchipkaart.findByReiziger() "+ reizigerDAO.findById(2).getId() +" geeft de volgende ovchipkaarten:");
        Reiziger reiziger2 = reizigerDAO.findById(2);
        List<Ovchipkaart> ovchipkaartenByReiziger = rdao.findByReiziger(reiziger2);
        if(ovchipkaartenByReiziger != null) {
            for (Ovchipkaart ov : ovchipkaartenByReiziger) {
                System.out.print(ov);
            }
        } else {
            System.out.println("Geen ovchipkaarten gevonden");
        }
    }

    private static void testProductDAO(ProductDAO rdao, OvchipkaartDAO ovchipkaartDAO) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");
        List<Product> products = rdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : products) {
            System.out.println(p);
        }

        // Maak een nieuwe product aan en persisteer deze in de database
        Product product = new Product(78, "Test", "Test", 10.0);
        System.out.print("[Test] Eerst " + products.size() + " producten, na ProductDAO.save() ");
        rdao.save(product);
        products = rdao.findAll();
        System.out.println(products.size() + " producten");
        System.out.println("[Test] ProductDAO.save() heeft deze product toegevoegd:");
        System.out.println(product);

        // Update product
        product.setNaam("Test2");
        rdao.update(product);
        products = rdao.findAll();
        System.out.println("[Test] ProductDAO.update() heeft de naam van de product aangepast:");
        System.out.println(products.get(products.size() - 1));

        // Delete product
        System.out.print("[Test] Eerst " + products.size() + " producten, na ProductDAO.delete() ");
        rdao.delete(product);
        products = rdao.findAll();
        System.out.println(products.size() + " producten");

        //add ovchipkaart to product
        Ovchipkaart ovchipkaart = ovchipkaartDAO.findById(35283);
        System.out.println("[Test] ProductDAO.addOVChipkaart() geeft de volgende producten:");
        if(rdao.addOVChipkaart(product, ovchipkaart)) {
            System.out.println("Ovchipkaart toegevoegd aan product");
        } else {
            System.out.println("Ovchipkaart niet toegevoegd aan product");
        }

//        get producten by ovchipkaarten
        System.out.println("[Test] ProductDAO.findByOvchipkaart() geeft de volgende producten:");
        List<Product> productenByOvchipkaart = rdao.findByOvchipkaart(ovchipkaart);
        if(productenByOvchipkaart != null) {
            for (Product p : productenByOvchipkaart) {
                System.out.println(p);
            }
        } else {
            System.out.println("Geen producten gevonden");
        }
    }
}












