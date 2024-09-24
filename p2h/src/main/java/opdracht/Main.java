package opdracht;


import opdracht.domain.Reiziger;
import opdracht.dao.ReizigerDAO;
import opdracht.DAOHibernate.ReizigerDAOHibernate;

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
        // add DAO for Reiziger
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(entityManager);
        // Test the ReizigerDAO
        testReizigerDAO(reizigerDAO);
        entityManager.close();
        entityManagerFactory.close();
    }

    // Test the ReizigerDAO
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.print(r);
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
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
}

