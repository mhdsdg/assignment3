package controllers;

import model.*;
import model.enums.Menu;
import model.enums.Regexes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class UserMenuController {
    public Result listMyOrders() {
        if(App.getLoggedUser().orders.isEmpty()) {
            return new Result(false, "No orders found.");
        }
        System.out.println("order History");
        ArrayList<Order> orders = new ArrayList<>(App.getLoggedUser().orders);
        orders.sort(Comparator.comparingInt(Order::getId));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Order order : orders) {
            int totalItems = 0;
            order.orderItems.sort(Comparator.comparing(orderItem -> orderItem.getProduct().getName()));
            for (OrderItem orderItem : order.orderItems) {
                totalItems += orderItem.getQuantity();
            }
            System.out.println("\nOrder ID: " + order.getId());
            System.out.println("Shipping Address: " + order.getShippingAddress().getStreet() + ", " + order.getShippingAddress().getCity() + ", " + order.getShippingAddress().getCountry());
            System.out.println("Total Items Ordered: " + totalItems + "\n");
            System.out.println("Products (Sorted by Name): ");
            for (int i = 0; i < order.orderItems.size(); i++) {
                System.out.println("  " + (i+1) + "- " + order.orderItems.get(i).getProduct().getName());
            }
            System.out.println();
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result (true , "orders listed successfully");
    }
    public Result showOrderDetails(String idString) {
        if(Regexes.Integer.getMather(idString) == null) {
            return new Result(false, "invalid command");
        }
        int id = Integer.parseInt(idString);

        if(getOrder(id) == null) {
            return new Result(false, "Order not found.");
        }
        ArrayList<OrderItem> products = new ArrayList<>(getOrder(id).orderItems);
        products.sort(Comparator.comparingInt(item -> item.getProduct().getId()));
        double totalPrice = 0;
        System.out.println("Products in this order:\n");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i+1) + "- Product Name: " + products.get(i).getProduct().getName());
            System.out.println("    ID: " + products.get(i).getId());
            System.out.println("    Brand: " + products.get(i).getProduct().getBrand());
            System.out.println("    Rating: " + products.get(i).getProduct().getRatingString() + "/5");
            System.out.println("    Quantity: " + products.get(i).getQuantity());
            System.out.printf("    Price: $%.1f" , products.get(i).getPriceAtPurchase());
            if (products.get(i).getQuantity() > 1) System.out.print(" each");
            System.out.println();
            if(i != products.size() - 1) {
                System.out.println();
            }
            totalPrice += products.get(i).getPriceAtPurchase() * products.get(i).getQuantity();
        }
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("**Total Cost: $" + String.format("%.1f",totalPrice) + "**");
        return new Result(true, "Order details shown successfully");
    }
    public Result editName(String firstName, String lastName , String password) {
        if(!App.getLoggedUser().getPassword().equals(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        }
        if(App.getLoggedUser().getFirstName().equals(firstName) || App.getLoggedUser().getLastName().equals(lastName)) {
            return new Result(false, "The new name must be different from the current name.");
        }
        if(firstName.length() < 3 || lastName.length() < 3) {
            return new Result(false , "Name is too short.");
        }
        if(Regexes.Name.getMather(firstName) == null || Regexes.Name.getMather(lastName) == null || Regexes.Name.getMather(lastName) == null) {
            return new Result(false , "Incorrect name format.");
        }
        App.getLoggedUser().setFirstName(firstName);
        App.getLoggedUser().setLastName(lastName);
        return new Result(true, "Name updated successfully.");
    }
    public Result editPassword(String newPassword , String oldPassword) {
        if(!App.getLoggedUser().getPassword().equals(oldPassword)) {
            return new Result(false, "Incorrect password. Please try again.");
        }
        if(oldPassword.equals(newPassword)) {
            return new Result(false, "The new password must be different from the old password.");
        }
        if(Regexes.Password.getMather(newPassword) == null) {
            return new Result(false , "The new password is weak.");
        }
        App.getLoggedUser().setPassword(newPassword);
        return new Result(true, "Password updated successfully.");
    }
    public Result editEmail(String email , String password) {
        if(!App.getLoggedUser().getPassword().equals(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        }
        if(App.getLoggedUser().getEmail().equals(email)) {
            return new Result(false, "The new email must be different from the current email.");
        }
        if(Regexes.Email.getMather(email) == null) {
            return new Result(false , "Incorrect email format.");
        }
        if(LoginMenuController.isEmailUsed(email)) {
            return new Result(false , "Email already exists.");
        }
        App.getLoggedUser().setEmail(email);
        return new Result(true, "Email updated successfully.");
    }
    public void showMyInfo() {
        System.out.println("Name: " + App.getLoggedUser().getFirstName() + " " + App.getLoggedUser().getLastName());
        System.out.println("Email: " + App.getLoggedUser().getEmail());
    }
    public Result addAddress(String country , String city , String street , String postal) {
        if(Regexes.PostalCode.getMather(postal) == null) {
            return new Result(false , "Invalid postal code. It should be a 10-digit number.");
        }
        if(getAddress(postal) != null) {
            return new Result(false , "This postal code is already associated with an existing address.");
        }
        App.getLoggedUser().addresses.add(new Address(country, city , street , postal , App.getLoggedUser().addressId));
        return new Result(true, "Address successfully added with id " + App.getLoggedUser().addressId++ + ".");
    }
    public Result deleteAddress(String addressId) {
        if(Regexes.Integer.getMather(addressId) == null) {
            return new Result(false , "invalid command");
        }
        Address address = getAddressById(Integer.parseInt(addressId));
        if(address == null) {
            return new Result(false , "No address found.");
        }
        App.getLoggedUser().addresses.remove(address);
        return new Result(true, "Address with id " + addressId + " deleted successfully.");
    }
    public void listAddresses() {
        if(App.getLoggedUser().addresses.isEmpty()) {
            System.out.println("No addresses found. Please add an address first.");
            return;
        }
        System.out.println("Saved Addresses\n━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Address address : App.getLoggedUser().addresses) {
            System.out.println();
            System.out.println("Address " + address.getId() + ": " );
            System.out.println("Postal Code: " + address.getPostalCode());
            System.out.println("Country: " + address.getCountry());
            System.out.println("City: " + address.getCity());
            System.out.println("Street: " + address.getStreet());
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
    }
    public Result addCreditCard(String cardNumber , String eDate , String cvv , String balanceString) {
        if(Regexes.Double.getMather(balanceString) == null) {
            return new Result(false , "invalid command");
        }
        if(Regexes.CardNumber.getMather(cardNumber) == null) {
            return new Result(false , "The card number must be exactly 16 digits.");
        }
        Matcher matcher = Regexes.Date.getMather(eDate);
        int month = 1;
        int year = 2000;
        if(matcher != null) {
            month = Integer.parseInt(matcher.group("month"));
            year = Integer.parseInt(matcher.group("year"));
        }
        if(matcher == null || month <= 0 || month > 12) {
            return new Result(false , "Invalid expiration date. Please enter a valid date in MM/YY format.");
        }
        if(Regexes.CVV.getMather(cvv) == null) {
            return new Result(false , "The CVV code must be 3 or 4 digits.");
        }
        double balance = Double.parseDouble(balanceString);
        if(balance < 0) {
            return new Result(false , "The initial value cannot be negative.");
        }
        if(getCreditCardByCardNumber(cardNumber) != null) {
            return new Result(false , "This card is already saved in your account.");
        }
        App.getLoggedUser().creditCards.add(new CreditCard(cardNumber, month , year , cvv, balance , App.getLoggedUser().creditCardId));
        return new Result(true, "Credit card with Id " + App.getLoggedUser().creditCardId++ + " has been added successfully.");
    }
    public Result chargeCreditCard(String idString , String amountString) {
        if(Regexes.Integer.getMather(idString) == null) {
            return new Result(false , "invalid command");
        }
        int id = Integer.parseInt(idString);
        double amount = Double.parseDouble(amountString);
        CreditCard creditCard = getCreditCardById(id);
        if(amount <= 0) {
            return new Result(false , "The amount must be greater than zero.");
        }
        if(creditCard == null) {
            return new Result(false , "No credit card found.");
        }
        creditCard.AddBalance(amount);
        return new Result(true , "$" + amount + " has been added to the credit card "+id+". New balance: $" + String.format("%.1f",creditCard.getBalance()) + ".");
    }
    public Result balanceOfCreditCard(String idString) {
        if(Regexes.Integer.getMather(idString) == null) {
            return new Result(false , "invalid command");
        }
        int id = Integer.parseInt(idString);
        CreditCard creditCard = getCreditCardById(id);
        if(creditCard == null) {
            return new Result(false , "No credit card found.");
        }
        return new Result(true , "Current balance : $" + String.format("%.1f",creditCard.getBalance()));
    }
    public Result showCart() {
        if(App.getLoggedUser().cart.items.isEmpty()) {
            return new Result(false , "Your shopping cart is empty.");
        }
        App.getLoggedUser().cart.items.sort(Comparator.comparing(item -> item.getProduct().getName()));
        System.out.println("Your Shopping Cart:");
        System.out.println("------------------------------------");
        for (CartItem item : App.getLoggedUser().cart.items) {
            double price = item.getDiscountAtPurchase() < 100 ? item.getProduct().getPrice() : item.getProduct().getNoDiscount();
            double totalPrice = price * item.getQuantity();
            System.out.println("Product ID  : " + item.getProduct().getId());
            System.out.println("Name        : " + item.getProduct().getName());
            System.out.println("Quantity    : " + item.getQuantity());
            System.out.println("Price       : $" + String.format("%.1f",price) + " (each)");
            System.out.println("Total Price : $" + String.format("%.1f",totalPrice));
            System.out.println("Brand       : " + item.getProduct().getBrand());
            System.out.println("Rating      : " + item.getProduct().getRatingString() + "/5");
            System.out.println("------------------------------------");
        }
        return new Result(true, "products shown");
    }
    public Result checkout(String cardIdString , String addressIdString ) {
        if(App.getLoggedUser().cart.items.isEmpty()) {
            return new Result(false , "Your shopping cart is empty.");
        }
        if(Regexes.Integer.getMather(cardIdString) == null || Regexes.Integer.getMather(addressIdString) == null) {
            return new Result(false , "invalid command");
        }
        CreditCard creditCard = getCreditCardById(Integer.parseInt(cardIdString));
        Address address = getAddressById(Integer.parseInt(addressIdString));
        if(address == null) {
            return new Result(false , "The provided address ID is invalid.");
        }
        if(creditCard == null) {
            return new Result(false , "The provided card ID is invalid.");
        }
        double totalPrice = App.getLoggedUser().cart.getTotalPrice();
        if(creditCard.getBalance() < totalPrice) {
            return new Result(false , "Insufficient balance in the selected card.");
        }
        creditCard.AddBalance(-totalPrice);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : App.getLoggedUser().cart.items) {
            double price = item.getDiscountAtPurchase() < 100 ? item.getProduct().getPrice() : item.getProduct().getNoDiscount();
            item.getProduct().store.addRevenue(price * item.getQuantity());
            item.getProduct().setNumberOfSoldProducts(item.getProduct().getNumberOfSoldProducts() + item.getQuantity());
            orderItems.add(new OrderItem(item.getProduct() , item.getQuantity() , price));
        }
        Order order = new Order(address , orderItems , App.getLoggedUser().orderId++);
        App.getLoggedUser().orders.add(order);
        App.getLoggedUser().cart.items.clear();
        return new Result(true, "Order has been placed successfully!\n" +
                "Order ID: "+ order.getId() +"\n" +
                "Total Paid: $"+String.format("%.1f",totalPrice)+"\n" +
                "Shipping to: "+ address.getStreet() + ", " + address.getCity() + ", " + address.getCountry());
    }
    public Result removeFromCart(String idString , String amountString) {
        if(Regexes.Integer.getMather(idString) == null || Regexes.Integer.getMather(amountString) == null) {
            return new Result(false , "invalid command");
        }
        if(App.getLoggedUser().cart.items.isEmpty()) {
            return new Result(false , "Your shopping cart is empty.");
        }
        int amount = Integer.parseInt(amountString);
        CartItem item = getCartItemById(Integer.parseInt(idString));
        if(item == null) {
            return new Result(false , "The product with ID "+Integer.parseInt(idString)+" is not in your cart.");
        }
        if(amount <= 0) {
            return new Result(false , "Quantity must be a positive number.");
        }
        if(amount > item.getQuantity()) {
            return new Result(false , "You only have "+ item.getQuantity() + " of \"" + item.getProduct().getName() + "\" in your cart.");
        }
        else if(amount < item.getQuantity()) {
            item.setQuantity(item.getQuantity() - amount);
            if(item.getDiscountAtPurchase() > 0){
                item.getProduct().setOnSaleQuantity(item.getProduct().getOnSaleQuantity() + amount);
            }
            else{
                item.getProduct().setRegularQuantity(item.getProduct().getRegularQuantity() + amount);
            }
            return new Result(true , "Successfully removed " +amount + " of \"" + item.getProduct().getName()+"\" from your cart.");
        }
        else {
            App.getLoggedUser().cart.items.remove(item);
            if(item.getDiscountAtPurchase() > 0){
                item.getProduct().setOnSaleQuantity(item.getProduct().getOnSaleQuantity() + amount);
            }
            else{
                item.getProduct().setRegularQuantity(item.getProduct().getRegularQuantity() + amount);
            }
            return new Result(true , "\""+item.getProduct().getName() + "\" has been completely removed from your cart.");
        }
    }
    public String goBack() {
        return App.setCurrentMenu(Menu.MainMenu);
    }



    public Order getOrder(int id) {
        for (Order order : App.getLoggedUser().orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }
    public Address getAddress(String postal) {
        for (Address address : App.getLoggedUser().addresses) {
            if (address.getPostalCode().equals(postal)) {
                return address;
            }
        }
        return null;
    }
    public Address getAddressById(int id) {
        for (Address address : App.getLoggedUser().addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null;
    }
    public CreditCard getCreditCardById(int id) {
        for (CreditCard creditCard : App.getLoggedUser().creditCards) {
            if (creditCard.getId() == id) {
                return creditCard;
            }
        }
        return null;
    }
    public CreditCard getCreditCardByCardNumber(String id) {
        for (CreditCard creditCard : App.getLoggedUser().creditCards) {
            if (creditCard.getCardNumber().equals(id)) {
                return creditCard;
            }
        }
        return null;
    }
    public CartItem getCartItemById(int id) {
        for (CartItem item : App.getLoggedUser().cart.items) {
            if(item.getProduct().getId() == id) {
                return item;
            }
        }
        return null;
    }
}
