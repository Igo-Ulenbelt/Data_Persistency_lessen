package project.dao;


import project.domain.Ovchipkaart;
import project.domain.Product;

import java.util.List;

public interface ProductDAO {
    // CRUD operations
    List<Product> findByOVChipkaart(Ovchipkaart ovChipkaart);
    List<Product> findAll();

    Product findById(int id);
    boolean save(Product product);
    boolean update(Product product);
    boolean addOVChipkaart(Product product, Ovchipkaart ovChipkaart, String status);
    boolean delete(Product product);

    boolean deleteFromManyToMany(Product product);
}

