package com.pluralsight.demo.models;

public class Product {

    private int productID;
    private String productName;
    private int categoryId;
    private double unitPrice;

    public Product() {}

    public Product(int productID, String productName, int categoryId, double unitPrice) {
        this.productID = productID;
        this.productName = productName;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
