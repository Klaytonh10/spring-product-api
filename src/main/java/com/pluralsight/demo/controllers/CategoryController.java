package com.pluralsight.demo.controllers;

import com.pluralsight.demo.daos.CategoryDao;
import com.pluralsight.demo.daos.ProductDao;
import com.pluralsight.demo.models.Category;
import com.pluralsight.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController {

    CategoryDao dao;

    @Autowired
    public CategoryController(CategoryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value="/categories", method= RequestMethod.GET)
    public ArrayList<Category> getAllCategories() {
        return dao.getCategories();
    }

    @RequestMapping(value="/categories/{id}", method=RequestMethod.GET)
    public Category getCategoryId(@PathVariable int id) {
        return dao.getCategoryById(id);
    }

}
