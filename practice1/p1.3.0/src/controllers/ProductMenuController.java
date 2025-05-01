package controllers;

import model.*;
import model.enums.Menu;
import model.enums.Regexes;

public class ProductMenuController {

    public void showProducts(String sortBy){
        App.formPages(sortBy);
        App.pageIndex = 0;
        System.out.println(App.pages.get(App.pageIndex).information);
    }
    public void showNext10(){
        if(App.pageIndex == App.pages.size()-1){
            System.out.println("No more products available.");
        }
        else{
            App.pageIndex++;
            System.out.println(App.pages.get(App.pageIndex).information);
        }
    }
    public void showPrevious10(){
        if(App.pageIndex == 0){
            System.out.println("No more products available.");
        }
        else{
            App.pageIndex--;
            System.out.println(App.pages.get(App.pageIndex).information);
        }
    }

    public Result showProductDetails(String idString){
        if(Regexes.Integer.getMather(idString) == null){
            return new Result(false, "invalid command");
        }
        Product product = getProduct(idString);
        if(product == null){
            return new Result(false, "No product found.");
        }
        StringBuilder message = new StringBuilder("Product Details  \n------------------------------------------------\n");
        message.append("Name: ").append(product.getName());
        if(product.getOnSaleQuantity() > 0) {
            message.append("  **(On Sale! ").append(product.getOnSaleQuantity()).append(" units discounted)**");
        }
        if(product.getOnSaleQuantity() == 0 && product.getRegularQuantity() == 0){
            message.append("  **(Sold out!)**");
        }
        message.append("\n");
        message.append("ID: ").append(product.getId()).append("\n");
        message.append("Rating: ").append(product.getRatingString()).append("/5\n");
        message.append("Price: ");
        if(product.getOnSaleQuantity() > 0){
            message.append("~");
        }
        message.append("$").append(String.format("%.1f", product.price));
        if(product.getOnSaleQuantity() > 0){
            message.append("~ → $").append(String.format("%.1f", product.getPrice())).
                    append(" (-").append(product.getDiscount()).append("%)");
        }
        message.append("\nBrand: ").append(product.getBrand());
        message.append("\nNumber of Products Remaining: ").
                append(product.getRegularQuantity() + product.getOnSaleQuantity()).append("\n");
        message.append("About this item:  \n");
        message.append(product.getDescription()).append("\n\nCustomer Reviews:  \n").
                append("------------------------------------------------");
        for (Rating rating : product.ratings) {
            message.append("\n").append(rating.getUser().getFirstName()).append(" ").
                    append(rating.getUser().getLastName()).append(" (").append(rating.getRating()).append("/5)");
            if(rating.getComment() != null){
                message.append("\n").append(rating.getComment());
            }
            message.append("\n------------------------------------------------");
        }
        return new Result(true, message.toString());
    }
    public Result setARating(String ratingString, String message , String idString ){
        if(Regexes.Integer.getMather(idString) == null || Regexes.Integer.getMather(ratingString) == null){
            return new Result(false, "invalid command");
        }
        if(getProduct(idString) == null){
            return new Result(false, "No product found.");
        }
        int rating = Integer.parseInt(ratingString);
        if(rating < 1 || rating > 5){
            return new Result(false, "Rating must be between 1 and 5.");
        }
        Product product = getProduct(idString);
        if(App.getLoggedUser() == null){
            return new Result(false, "You must be logged in to rate a product.");
        }
        product.ratings.add(new Rating(rating , message , App.getLoggedUser()));
        return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
    }
    public Result addProduct(String idString , String amountString){
        if(Regexes.Integer.getMather(idString) == null || Regexes.Integer.getMather(amountString) == null){
            return new Result(false, "invalid command");
        }
        if(App.getLoggedUser() == null){
            return new Result(false, "You must be logged in to add items to your cart.");
        }
        Product product = getProduct(idString);
        int amount = Integer.parseInt(amountString);
        if(product == null){
            return new Result(false, "No product found.");
        }
        if(amount < 1) {
            return new Result(false, "Quantity must be a positive number.");
        }
        if((product.getOnSaleQuantity() + product.getRegularQuantity()) < amount){
            return new Result(false, "Only " +
                    (product.getOnSaleQuantity() + product.getRegularQuantity()) +
                    " units of \"" + product.getName() + "\" are available.");
        }
        CartItem item = new CartItem(product, amount , product.getDiscount());

        if(product.getOnSaleQuantity() > 0){
            product.setOnSaleQuantity(product.getOnSaleQuantity() - amount);
        }
        else{
            product.setRegularQuantity(product.getRegularQuantity() - amount);
        }
        boolean flag = false;
        for (CartItem cartItem : App.getLoggedUser().cart.items) {
            if(cartItem.getProduct().getId() == product.getId()){
                cartItem.setQuantity(cartItem.getQuantity() + amount);
                flag = true;
                break;
            }
        }
        if(!flag){
            App.getLoggedUser().cart.items.add(item);
        }
        return new Result(true, "\"" + product.getName() + "\" (x" + amount +
                ") has been added to your cart.");
    }
    public String goBack(){
        return App.setCurrentMenu(Menu.MainMenu);
    }



    public Product getProduct(String idString){
        for(Product product : App.products){
            if(product.getId() == Integer.parseInt(idString)){
                return product;
            }
        }
        return null;
    }
}
