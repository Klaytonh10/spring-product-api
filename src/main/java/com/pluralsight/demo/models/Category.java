package com.pluralsight.demo.models;

public class Category {

    private int categoryId;
    private String categoryName;

    public Category() {}

    public Category(int categoryId, String category) {
        this.categoryId = categoryId;
        this.categoryName = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
