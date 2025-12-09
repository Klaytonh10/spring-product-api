package com.pluralsight.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserInterface {

    ProductDao productDao;

    @Autowired
    public UserInterface(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void printAllProductNames() {

        ArrayList<Product> products = this.productDao.getProducts();

        for(Product product : products) {
            System.out.println(product.getProductName());
        }

    }

}
