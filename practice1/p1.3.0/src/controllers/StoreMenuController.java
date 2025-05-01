package controllers;

import model.*;
import model.enums.Menu;
import model.enums.Regexes;

import java.util.ArrayList;
import java.util.Comparator;

public class StoreMenuController {
    public Result addProduct(String name, String producerCostString , String priceString ,
                             String description, String stockString) {
        if(Regexes.Integer.getMather(stockString) == null ||
                Regexes.Double.getMather(producerCostString) == null||
                Regexes.Double.getMather(priceString) == null){
            return new Result(false, "invalid command");
        }
        double producerCost = Double.parseDouble(producerCostString);
        double price = Double.parseDouble(priceString);
        int stock = Integer.parseInt(stockString);
        Store store = App.getLoggedStore();
        if(producerCost > price){
            return new Result(false, "Selling price must be greater than or equal to the producer cost.");
        }
        if(stock < 1){
            return new Result(false, "Number of products must be a positive number.");
        }
        Product product = new Product(name, Store.productIdCounter++ , store.getBrand(), stock , price , producerCost , description , App.getLoggedStore());
        store.products.add(product);
        App.products.add(product);
        store.addCosts(producerCost * stock);
        return new Result(true , "Product \"" + name + "\" has been added successfully with ID " + product.getId() +".");
    }
    public Result applyDiscount(String idString , String discountString , String quantityString) {
        if(Regexes.Integer.getMather(idString) == null ||
                Regexes.Integer.getMather(discountString) == null ||
                Regexes.Integer.getMather(quantityString) == null){
            return new Result(false, "invalid command");
        }
        int discount = Integer.parseInt(discountString);
        int quantity = Integer.parseInt(quantityString);
        int productId = Integer.parseInt(idString);
        Product product = getStoreProduct(productId);
        if(discount > 100 || discount <= 0){
            return new Result(false , "Discount percentage must be between 1 and 100.");
        }
        if(product == null){
            return new Result(false, "No product found.");
        }
        if(quantity > product.getRegularQuantity()) {
            return new Result(false, "Oops! Not enough stock to apply the discount to that many items.");
        }
        product.setRegularQuantity(product.getRegularQuantity() - quantity);
        product.setOnSaleQuantity(product.getOnSaleQuantity() + quantity);
        product.setDiscount(discount);
        return new Result(true , "A " + discount + "% discount has been applied to "+ quantity +" units of product ID "+productId+".");
    }
    public String showProfit() {
        double totalProfit;
        totalProfit = App.getLoggedStore().getRevenue() - App.getLoggedStore().getCosts();
        return String.format("Total Profit: $%.1f  \n(Revenue: $%.1f - Costs: $%.1f)" , totalProfit, App.getLoggedStore().getRevenue() , App.getLoggedStore().getCosts());
    }
    public Result listStoreProducts() {
        if (App.getLoggedStore().products.isEmpty()) {
            return new Result(false, "No products available in the store.\n");
        }
        ArrayList<Product> products = new ArrayList<>(App.getLoggedStore().products);
        products.sort(Comparator.comparingInt(Product::getId));
        Page page = formPage(products);
        return new Result (true , page.information);
    }
    public Result addStock(String idString , String quantityString) {
        if(Regexes.Integer.getMather(idString) == null || Regexes.Integer.getMather(quantityString) == null){
            return new Result(false, "invalid command");
        }
        int stock = Integer.parseInt(quantityString);
        int productId = Integer.parseInt(idString);
        Product product = getStoreProduct(productId);
        if(product == null){
            return new Result(false, "No product found.");
        }
        if(stock < 1) {
            return new Result(false, "Amount must be a positive number.");
        }
        product.setRegularQuantity(product.getRegularQuantity() + stock);
        App.getLoggedStore().addCosts(product.getNoDiscount() * stock);
        return new Result(true , stock + " units of \"" + product.getName() + "\" have been added to the stock.");
    }
    public Result updatePrice(String idString , String newPriceString) {
        if(Regexes.Integer.getMather(idString) == null || Regexes.Double.getMather(newPriceString) == null){
            return new Result(false, "invalid command");
        }
        int productId = Integer.parseInt(idString);
        Product product = getStoreProduct(productId);
        double newPrice = Double.parseDouble(newPriceString);
        if(product == null){
            return new Result(false, "No product found.");
        }
        if(newPrice <= 0){
            return new Result(false, "Price must be a positive number.");
        }
        product.setPrice(newPrice);
        return new Result(true , "Price of \"" + product.getName() + "\" has been updated to $" + String.format("%.1f", newPrice) + ".");
    }
    public String goBack() {
        return App.setCurrentMenu(Menu.MainMenu);
    }



    public Product getStoreProduct(int id) {
        for (Product product : App.getLoggedStore().products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
    public Page formPage(ArrayList<Product> products){
        Page page = new Page("Store Products (Sorted by date added)\n");
        page.information += "------------------------------------------------\n";
        for (Product product : products) {
            page.information += "ID: " + product.getId();
            if (product.getOnSaleQuantity() > 0) {
                page.information += "  **(On Sale! " + product.getOnSaleQuantity() + " units discounted)**";
            }
            if (product.getOnSaleQuantity() == 0 && product.getRegularQuantity() == 0) {
                page.information += "  (**Sold out!**)";
            }
            page.information += "\n";
            page.information += "Name: " + product.getName() + "\n";
            page.information += "Price: ";
            if (product.getOnSaleQuantity() > 0) {
                page.information += "~";
            }
            page.information += String.format("$%.1f", product.price);
            if (product.getOnSaleQuantity() > 0) {
                page.information += "~ → $" + String.format("%.1f", product.getPrice()) + " (-" + (int) product.getDiscount() + "%)";
            }
            page.information += "\n";
            page.information += "Stock: " + (product.getOnSaleQuantity() + product.getRegularQuantity());
            page.information += "\nSold: " + product.getNumberOfSoldProducts();
            page.information += "\n------------------------------------------------\n";
        }
        return page;
    }
}
