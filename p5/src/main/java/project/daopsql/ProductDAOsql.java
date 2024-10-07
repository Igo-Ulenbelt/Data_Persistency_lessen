package project.daopsql;

import project.dao.ProductDAO;
import project.domain.Ovchipkaart;
import project.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOsql implements ProductDAO {
    private Connection connection;

    // Constructor
    public ProductDAOsql(Connection connection) {
        this.connection = connection;
    }

    // Methods
    // Find all products by ovchipkaart

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM product";
        List<Product> products;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            products = new ArrayList<>();

            while (set != null && set.next()) {
                Product product = new Product(
                        set.getInt("product_nummer"),
                        set.getString("naam"),
                        set.getString("beschrijving"),
                        set.getDouble("prijs")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<Product> findByOVChipkaart(Ovchipkaart ovChipkaart) {
        String query = "SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs FROM product AS p INNER JOIN ov_chipkaart_product AS ov ON p.product_nummer = ov.product_nummer WHERE ov.kaart_nummer = ?";
        List<Product> products;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getId());
            ResultSet set = statement.executeQuery();
            products = new ArrayList<>();

            while (set != null && set.next()) {
                Product product = new Product(
                        set.getInt("product_nummer"),
                        set.getString("naam"),
                        set.getString("beschrijving"),
                        set.getDouble("prijs")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public boolean addOVChipkaart(Product product, Ovchipkaart ovChipkaart, String status) {
        String query = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getId());
            statement.setInt(2, product.getNummer());
            statement.setString(3, status);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Product product) {
        String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getNummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getNaam());
            statement.setString(2, product.getBeschrijving());
            statement.setDouble(3, product.getPrijs());
            statement.setInt(4, product.getNummer());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Product product) {
        String query = "DELETE FROM product WHERE product_nummer = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getNummer());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete from many-to-many table
    public boolean deleteFromManyToMany(Product product) {
        String query = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getNummer());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
