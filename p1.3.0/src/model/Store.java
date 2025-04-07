package model;

import java.util.ArrayList;

public class Store {
    private String brand;
    private String password;
    private String email;
    public ArrayList<Product> products = new ArrayList<>();
    public static int productIdCounter = 101;
    private double costs = 0;
    private double revenue = 0;

    public Store(String brand, String password, String email) {
        this.brand = brand;
        this.password = password;
        this.email = email;
    }

    public static int getProductIdCounter() {
        return productIdCounter;
    }

    public static void setProductIdCounter(int productIdCounter) {
        Store.productIdCounter = productIdCounter;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCosts() {
        return costs;
    }

    public void addCosts(double costs) {
        this.costs += costs;
    }

    public double getRevenue() {
        return revenue;
    }

    public void addRevenue(double revenue) {
        this.revenue += revenue;
    }
}
