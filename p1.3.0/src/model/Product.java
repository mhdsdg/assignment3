package model;

import java.util.ArrayList;

public class Product {
    private String name ;
    private final int id;
    private String brand;
    private double rating = 2.5;
    private int onSaleQuantity;
    private int discount = 0;
    private int regularQuantity;
    public double price;
    private double producingPrice;
    public ArrayList<Rating> ratings = new ArrayList<>();
    private int numberOfSoldProducts = 0;
    private String description;
    public Store store;

    public Product(String name, int id, String brand, int quantity, double price , double producingPrice, String description , Store store) {
        this.name = name;
        this.id = id;
        this.brand = brand;
        this.regularQuantity = quantity;
        this.description = description;
        this.onSaleQuantity = 0;
        this.price = price;
        this.producingPrice = producingPrice;
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getRating() {
        if(ratings.isEmpty()){
            return 2.5 ;
        }
        if(!ratings.isEmpty()){
            int sum = 0;
            for(Rating r : ratings){
                sum += r.getRating();
            }
            return (double)sum / ratings.size();
        }
        return 2.5;
    }

    public String getRatingString() {
        return String.format("%.1f", getRating());
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRegularQuantity() {
        return regularQuantity;
    }

    public void setRegularQuantity(int regularQuantity) {
        this.regularQuantity = regularQuantity;
    }

    public double getPrice() {
        return price * (100 - discount) / 100;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProducingPrice() {
        return producingPrice;
    }

    public void setProducingPrice(double producingPrice) {
        this.producingPrice = producingPrice;
    }

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNumberOfSoldProducts() {
        return numberOfSoldProducts;
    }

    public void setNumberOfSoldProducts(int numberOfSoldProducts) {
        this.numberOfSoldProducts = numberOfSoldProducts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNoDiscount(){
        return price;
    }
}
