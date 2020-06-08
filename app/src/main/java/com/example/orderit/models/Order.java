package com.example.orderit.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {

    @PrimaryKey
    @NonNull
    private String ProductID;
    private String ProductName;
    private int Quantity;
    private Double Price;

    public Order() {
    }

    public Order(String productID, String productName, int quantity, Double price) {
        ProductID = productID;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
