package model;

import java.util.ArrayList;

public class Cart {
    public ArrayList<CartItem> items = new ArrayList<>();

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            double price = item.getDiscountAtPurchase() < 100 ? item.getProduct().getPrice() : item.getProduct().getNoDiscount();
            total += price * item.getQuantity();
        }
        return total;
    }
}
