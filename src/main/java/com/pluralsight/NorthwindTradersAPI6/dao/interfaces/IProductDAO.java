package com.pluralsight.NorthwindTradersAPI6.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI6.models.Product;

import java.util.List;

public interface IProductDAO {
    List<Product> getAll();

    Product getById(int productId);

    Product insert(Product product);

    void update(int productId, Product product);

    void delete(int productId);
}