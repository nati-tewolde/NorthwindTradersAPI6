package com.pluralsight.NorthwindTradersAPI6.dao.impl;

import com.pluralsight.NorthwindTradersAPI6.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI6.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JBDCProductDAO implements IProductDAO {

    private DataSource dataSource;

    @Autowired
    public JBDCProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> Products = new ArrayList<>();
        String getAllQuery = "SELECT * FROM products";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getAllQuery);
             ResultSet resultSet = selectStatement.executeQuery()) {
            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                int categoryID = resultSet.getInt("CategoryID");
                double unitPrice = resultSet.getDouble("UnitPrice");

                Products.add(new Product(productID, name, categoryID, unitPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Products;
    }

    @Override
    public Product getById(int productId) {
        Product Product = null;
        String getByIdQuery = "SELECT * FROM products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByIdQuery)) {

            selectStatement.setInt(1, productId);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int productID = resultSet.getInt("ProductID");
                    String name = resultSet.getString("ProductName");
                    int categoryID = resultSet.getInt("CategoryID");
                    double unitPrice = resultSet.getDouble("UnitPrice");

                    Product = new Product(productID, name, categoryID, unitPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Product;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getUnitPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setProductId(generatedId);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void update(int productId, Product product) {
        String sql = "UPDATE Products SET ProductName = ?, CategoryID = ?, UnitPrice = ? WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getUnitPrice());
            statement.setInt(4, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int productId) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}