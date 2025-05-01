package model;

public class CartItem {
    private Product product;
    private int quantity;
    private int discountAtPurchase;

    public CartItem(Product product, int quantity , int discountAtPurchase) {
        this.product = product;
        this.quantity = quantity;
        this.discountAtPurchase = discountAtPurchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountAtPurchase() {
        return discountAtPurchase;
    }

    public void setDiscountAtPurchase(int discountAtPurchase) {
        this.discountAtPurchase = discountAtPurchase;
    }

    public double getPriceWithDiscount() {
        return product.getNoDiscount() * (100 - discountAtPurchase) * quantity / 100;
    }
}
