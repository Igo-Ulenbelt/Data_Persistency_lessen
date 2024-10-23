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
        testDAO(reizigerDAO, adresDAO, ovchipkaartDAO, productDAO);


        entityManager.close();
        entityManagerFactory.close();
    }

    // Test the ReizigerDAO
    private static void testDAO(ReizigerDAO reizigerDAO, AdresDAO adresDAO, OvchipkaartDAO ovchipkaartDAO, ProductDAO productdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        Reiziger reiziger = reizigerDAO.findById(1);
        Adres adres1 = adresDAO.findByReiziger(reizigerDAO.findById(1));

        Reiziger harry = new Reiziger(77, "H", "", "Boers", java.sql.Date.valueOf("1983-04-13"), adres1, null);
        Adres harrysAdres = new Adres(77, "8014EP", "10bis", "Tuinmeesterlaan", "Dalfsen", harry);

        Ovchipkaart harryOvkaart1 = new Ovchipkaart(77777, java.sql.Date.valueOf("2020-02-22"), 2, (float) 87.88, reiziger);
        Ovchipkaart harryOvkaart2 = new Ovchipkaart(77778, java.sql.Date.valueOf("2021-01-11"), 1, (float) 12.23, reiziger);

        Product dagkaartKind = new Product(103, "Dagkaart hond", "De hele dag op pad met je kleine monstertje", (float) 4.00);
        Product dagkaartBaby = new Product(104, "Dagkaart baby", "De hele dag op pad met je lieve baby", (float) 16.90);

        reizigerDAO.save(harry);
        adresDAO.save(harrysAdres);
        ovchipkaartDAO.save(harryOvkaart1);
        ovchipkaartDAO.save(harryOvkaart2);
        productdao.save(dagkaartKind);
        productdao.save(dagkaartBaby);

        reiziger.setOvchipkaarten(List.of(harryOvkaart1, harryOvkaart2));

        productdao.addOVChipkaart(dagkaartKind, harryOvkaart1);
        productdao.addOVChipkaart(dagkaartBaby, harryOvkaart1);
        productdao.addOVChipkaart(dagkaartBaby, harryOvkaart2);
        System.out.println("[Test] ReizigerDAO.save() & AdresDAO.save() & OvchipkaartDAO.save() & ProductDAO.save() werkt");

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
        ovchipkaartDAO.delete(harryOvkaart1);
        ovchipkaartDAO.delete(harryOvkaart2);
        productdao.delete(dagkaartKind);
        productdao.delete(dagkaartBaby);
        adresDAO.delete(harrysAdres);
        reizigerDAO.delete(harry);
        System.out.println("\n\n[Test] ReizigerDAO.delete() & AdresDAO.delete() & OvchipkaartDAO.delete() & ProductDAO.delete() werkt");
    }
}












