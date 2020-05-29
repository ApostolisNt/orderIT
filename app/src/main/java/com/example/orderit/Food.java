package com.example.orderit;

public class Food {

    private String name;
    private double price;
    private String menuID;

    public Food() {}

    public Food(String name, double price, String menuID) {
        this.name = name;
        this.price = price;
        this.menuID = menuID;
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
}