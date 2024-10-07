package opdracht.DAOHibernate;

import opdracht.dao.AdresDAO;
import opdracht.domain.Reiziger;
import project.domain.Adres;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    // Attributes
    private final EntityManager entityManager;

    // Constructor
    public AdresDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Get all entries
    @Override
    public List<Adres> findAll() {
        return entityManager.createQuery("SELECT adres FROM Adres adres", Adres.class).getResultList();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            List<Adres> resultList = entityManager.createQuery("SELECT adres FROM Adres adres WHERE adres.reiziger = :reiziger", Adres.class).setParameter("reiziger", reiziger).getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(Adres adres) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Modify a database entry
    @Override
    public boolean update(Adres adres) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(adres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Remove a database entry
    @Override
    public boolean delete(Adres adres) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Adres managedAdres = entityManager.contains(adres) ? adres : entityManager.merge(adres);
            entityManager.remove(managedAdres);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}

