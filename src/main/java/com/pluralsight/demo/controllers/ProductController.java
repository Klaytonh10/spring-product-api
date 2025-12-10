package com.pluralsight.demo.controllers;

import com.pluralsight.demo.daos.ProductDao;
import com.pluralsight.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ProductController {

    ProductDao dao;

    @Autowired
    public ProductController(ProductDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public ArrayList<Product> getAllProducts() {
        return this.dao.getProducts();
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.GET)
    public Product getProductId(@PathVariable int id) {
        return this.dao.getProductById(id);
    }

    @RequestMapping(path="/products/add", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product product) {
        return this.dao.createNewProduct(product);
    }

}
