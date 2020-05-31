package com.example.orderit.models;

public class Food {

    private String name;
    private double price;
    private String menuID;
    private String image;
    private String description;

    public Food() {}

    public Food(String name, double price, String menuID, String image, String description) {
        this.name = name;
        this.price = price;
        this.menuID = menuID;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}