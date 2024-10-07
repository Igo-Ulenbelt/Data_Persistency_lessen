package opdracht.DAOHibernate;

import opdracht.dao.OvchipkaartDAO;
import opdracht.domain.Ovchipkaart;
import opdracht.domain.Product;
import opdracht.domain.Reiziger;

import javax.persistence.EntityManager;
import java.util.List;

public class OvchipkaartDAOHibernate implements OvchipkaartDAO {
    // Attributes
    private final EntityManager entityManager;

    // Constructor
    public OvchipkaartDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Ovchipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<Ovchipkaart> resultList = entityManager.createQuery("SELECT ovchipkaart FROM Ovchipkaart ovchipkaart WHERE ovchipkaart.reiziger = :reiziger", Ovchipkaart.class).setParameter("reiziger", reiziger).getResultList();
            if (resultList.isEmpty()) {
                System.out.println("No Ovchipkaart found for Reiziger: " + reiziger);
                return null;
            } else {
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    find by id
    public Ovchipkaart findById(int id) {
        try {
            Ovchipkaart ovchipkaart = entityManager.find(Ovchipkaart.class, id);
            if (ovchipkaart == null) {
                System.out.println("No Ovchipkaart found for id: " + id);
                return null;
            } else {
                return ovchipkaart;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all entries
    @Override
    public List<Ovchipkaart> findAll() {
        return entityManager.createQuery("SELECT ovchipkaart FROM Ovchipkaart ovchipkaart", Ovchipkaart.class).getResultList();
    }
}
