package com.pluralsight.demo.controllers;


import com.pluralsight.demo.daos.CategoryDao;
import com.pluralsight.demo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController {

    CategoryDao dao;

    // http://localhost:8080/
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public ArrayList<Category> getAllCategories() {
        return this.dao.getCategories();
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(name = "greetName") String name) {
        return "Hello " + name + "!";
    }

}
