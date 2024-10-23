package opdracht.DAOHibernate;

import opdracht.dao.ProductDAO;
import opdracht.domain.Ovchipkaart;
import opdracht.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    // Attributes
    private final EntityManager entityManager;

    // Constructor
    public ProductDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Get all entries
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT product FROM Product product", Product.class).getResultList();
    }

    public List<Product> findByOvchipkaart(Ovchipkaart ovchipkaart) {
        try {
            List<Product> resultList = entityManager.createQuery("SELECT product FROM Product product JOIN product.ovChipkaarten ovchipkaart WHERE ovchipkaart.id = :kaartNummer", Product.class).setParameter("kaartNummer", ovchipkaart.getId()).getResultList();
            if (resultList.isEmpty()) {
                System.out.println("No Product found for Ovchipkaart: " + ovchipkaart);
                return null;
            } else {
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Find by id
    public Product findById(int id) {
        try {
            Product product = entityManager.find(Product.class, id);
            if (product == null) {
                System.out.println("No Product found for id: " + id);
                return null;
            } else {
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add a relationship between Product and OVChipkaart
    public Boolean addOVChipkaart(Product product, Ovchipkaart ovchipkaart) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            product.getOvChipkaarten().add(ovchipkaart);
            ovchipkaart.getProducten().add(product);

            entityManager.merge(product);
            entityManager.merge(ovchipkaart);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a database entry
    public Product save(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(product);
            entityManager.getTransaction().commit();
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Modify a database entry
    public Product update(Product product) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(product);
            entityManager.getTransaction().commit();
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a database entry
    public boolean delete(Product product) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            Product managedProduct = entityManager.merge(product);
            entityManager.remove(managedProduct);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    // Delete a relationship between Product and OVChipkaart
    public void deleteFromManyToMany(Product product) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            product.getOvChipkaarten().clear();
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

}
