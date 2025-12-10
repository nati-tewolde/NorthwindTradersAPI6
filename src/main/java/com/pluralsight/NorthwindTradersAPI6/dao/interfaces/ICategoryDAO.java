package com.pluralsight.NorthwindTradersAPI6.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI6.models.Category;

import java.util.List;

public interface ICategoryDAO {
    List<Category> getAll();

    Category getById(int categoryId);

    Category insert(Category category);

    void update(int categoryId, Category category);

    void delete(int categoryId);
}