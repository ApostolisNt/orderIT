package com.example.orderit;

public class Category {

    private String name;
    private String image;
    private String categoryID;

    public Category() {}

    public Category(String name, String image, String categoryID) {
        this.name = name;
        this.image = image;
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategoryID() {
        return categoryID;
    }

}