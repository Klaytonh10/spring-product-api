package com.pluralsight.demo.controllers;

import com.pluralsight.demo.daos.ProductDao;
import com.pluralsight.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @RequestMapping(path="/products", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product product) {
        return this.dao.createNewProduct(product);
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void updateProductById(@PathVariable int id,@RequestBody Product product) {
        if(id!= product.getProductID()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        this.dao.updateProductById(id, product);
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProductByIf(@PathVariable int id) {
        this.dao.deleteProductById(id);
    }

}
