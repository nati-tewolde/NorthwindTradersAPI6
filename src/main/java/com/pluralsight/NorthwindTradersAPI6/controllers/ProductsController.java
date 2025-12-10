package com.pluralsight.NorthwindTradersAPI6.controllers;

import com.pluralsight.NorthwindTradersAPI6.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI6.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {
    private IProductDAO productDAO;

    @Autowired
    public ProductsController(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int productId) {
        return productDAO.getById(productId);
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productDAO.insert(product);
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int productId, @RequestBody Product product) {
        productDAO.update(productId, product);
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int productId) {
        productDAO.delete(productId);
    }
}
