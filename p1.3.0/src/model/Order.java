package model;

import java.util.ArrayList;

public class Order {
    private final int id ;
    private final Address shippingAddress ;
    public ArrayList<OrderItem> orderItems = new ArrayList<>();

    public Order(Address shippingAddress , ArrayList<OrderItem> products , int id) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.orderItems = products;
    }

    public int getId() {
        return id;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
