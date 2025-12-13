package com.pluralsight.demo.controllers;

import com.pluralsight.demo.daos.CategoryDao;
import com.pluralsight.demo.models.Category;
import com.pluralsight.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CategoryController {

    CategoryDao dao;

    @Autowired
    public CategoryController(CategoryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value="/categories", method=RequestMethod.GET)
    public ArrayList<Category> getAllCategories() {
        return this.dao.getAllCategories();
    }

    @RequestMapping(path="/categories/{id}", method=RequestMethod.GET)
    public Category getCategoryId(@PathVariable int id) {
        return this.dao.getCategoryById(id);
    }

    @RequestMapping(path="/categories", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category createNewCategory(@RequestBody Category category) {
        return this.dao.createNewCategory(category);
    }

}
