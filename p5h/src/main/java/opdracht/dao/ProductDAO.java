package opdracht.dao;

import opdracht.domain.Ovchipkaart;
import opdracht.domain.Product;

import java.util.List;

public interface ProductDAO {
    // CRUD methods
    List<Product> findAll();
    List<Product> findByOvchipkaart(Ovchipkaart ovchipkaart);
    Boolean addOVChipkaart(Product product, Ovchipkaart ovchipkaart);
    Product save(Product product);
    Product update(Product product);
    boolean delete(Product product);
}
