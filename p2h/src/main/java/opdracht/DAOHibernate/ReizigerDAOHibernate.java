package opdracht.DAOHibernate;

import opdracht.dao.ReizigerDAO;
import opdracht.domain.Reiziger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    // Attributes
    private final EntityManager entityManager;

    // Constructor
    public ReizigerDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Get all entries
    @Override
    public List<Reiziger> findAll() {
        return entityManager.createQuery("SELECT reiziger FROM Reiziger reiziger", Reiziger.class).getResultList();
    }

    // Get a database entry by id
    @Override
    public Reiziger findById(int reizigerId) {
        return entityManager.find(Reiziger.class, reizigerId);
    }

    // Get entries by birthdate
    @Override
    public List<Reiziger> findByGbdatum(Date birthDate) {
        return entityManager.createQuery("SELECT reiziger FROM Reiziger reiziger WHERE reiziger.geboortedatum = :birthDate", Reiziger.class).setParameter("birthDate", birthDate).getResultList();
    }

    // Add a database entry
    @Override
    public boolean save(Reiziger reiziger) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(reiziger);
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
    public boolean update(Reiziger reiziger) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(reiziger);
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
    public boolean delete(Reiziger reiziger) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Reiziger managedReiziger = entityManager.contains(reiziger) ? reiziger : entityManager.merge(reiziger);
            entityManager.remove(managedReiziger);
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

