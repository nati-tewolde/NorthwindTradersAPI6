package com.pluralsight.NorthwindTradersAPI6.controllers;

import com.pluralsight.NorthwindTradersAPI6.dao.interfaces.ICategoryDAO;
import com.pluralsight.NorthwindTradersAPI6.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoriesController {
    private ICategoryDAO categoryDAO;

    @Autowired
    public CategoriesController(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public List<Category> getAllCategories(@RequestParam(required = false) String name) {
        List<Category> categories = categoryDAO.getAll();

        if (name == null) {
            return categories;
        }

        List<Category> filteredCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getCategoryName().equalsIgnoreCase(name)) {
                filteredCategories.add(category);
            }
        }

        return filteredCategories;
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable int categoryId) {
        return categoryDAO.getById(categoryId);
    }

    @RequestMapping (path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDAO.insert(category);
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.PUT)
    public void updateCategory(@PathVariable int categoryId, @RequestBody Category category) {
        categoryDAO.update(categoryId, category);
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int categoryId) {
        categoryDAO.delete(categoryId);
    }
}