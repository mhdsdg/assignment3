package controllers;

import model.*;
import model.enums.*;

public class LoginMenuController {
    public Result createUser(String firstname, String lastname, String password , String password2 , String email) {
        if(firstname.length() < 3 || lastname.length() < 3) {
            return new Result(false , "Name is too short.");
        }
        if(Regexes.Name.getMather(firstname) == null || Regexes.Name.getMather(lastname) == null || Regexes.Name.getMather(lastname) == null) {
            return new Result(false , "Incorrect name format.");
        }
        if(Regexes.Password.getMather(password) == null) {
            return new Result(false , "Incorrect password format.");
        }
        if(!password.equals(password2)) {
            return new Result(false , "Re-entered password is incorrect.");
        }
        if(Regexes.Email.getMather(email) == null) {
            return new Result(false , "Incorrect email format.");
        }
        if(isEmailUsed(email)) {
            return new Result(false , "Email already exists.");
        }
        App.users.add(new User(firstname, lastname, email , password));
        return new Result(true , "User account for " + firstname + " " + lastname + " created successfully.");
    }
    public Result createStore(String brand , String password , String password2 , String email) {
        if(brand.length() < 3){
            return new Result(false , "Brand name is too short.");
        }
        if(Regexes.Password.getMather(password) == null) {
            return new Result(false , "Incorrect password format.");
        }
        if(!password.equals(password2)) {
            return new Result(false , "Re-entered password is incorrect.");
        }
        if(Regexes.Email.getMather(email) == null) {
            return new Result(false , "Incorrect email format.");
        }
        if(isEmailUsed(email)) {
            return new Result(false , "Email already exists.");
        }
        App.stores.add(new Store(brand, password, email));
        return new Result(true , "Store account for \"" + brand + "\" created successfully.");
    }
    public Result loginUser(String email, String password) {
        User user = getUserByEmail(email);
        if(user == null) {
            return new Result(false , "No user account found with the provided email.");
        }
        if(!user.getPassword().equals(password)) {
            return new Result(false , "Password is incorrect.");
        }
        App.setCurrentMenu(Menu.MainMenu , "User logged in successfully. Redirecting to the MainMenu ...");
        App.setLoggedUser(user);
        App.setLoggedStore(null);
        return new Result(true , "User logged in successfully. Redirecting to the MainMenu ...");
    }
    public Result loginStore(String email, String password) {
        Store store = getStoreByEmail(email);
        if(store == null) {
            return new Result(false , "No store account found with the provided email.");
        }
        if(!store.getPassword().equals(password)) {
            return new Result(false , "Password is incorrect.");
        }
        App.setCurrentMenu(Menu.MainMenu , "Store logged in successfully. Redirecting to the MainMenu ...");
        App.setLoggedStore(store);
        App.setLoggedUser(null);
        return new Result(true , "Store logged in successfully. Redirecting to the MainMenu ...");
    }
    public Result logout() {
        if(App.getLoggedUser() == null && App.getLoggedStore() == null) {
            return new Result(false , "You should login first.");
        }
        App.setLoggedUser(null);
        App.setLoggedStore(null);
        App.setCurrentMenu(Menu.MainMenu , "Logged out successfully. Redirecting to the MainMenu ...");
        return new Result(true , "Logged out successfully. Redirecting to the MainMenu ...");
    }
    public Result deleteAccount(String password , String password2) {
        if(App.getLoggedUser() == null && App.getLoggedStore() == null) {
            return new Result(false , "You should login first.");
        }
        if(!password.equals(password2)) {
            return new Result(false , "Re-entered password is incorrect.");
        }
        if(App.getLoggedUser() != null) {
            if(!App.getLoggedUser().getPassword().equals(password)) {
                return new Result(false , "Password is incorrect.");
            }
            UserMenuController controller = new UserMenuController();
            for (CartItem item : App.getLoggedUser().cart.items) {
                controller.removeFromCart(String.format("%d",item.getProduct().getId()) , String.format("%d",item.getQuantity()));
            }
            App.users.remove(App.getLoggedUser());
        }
        if(App.getLoggedStore() != null) {
            if(!App.getLoggedStore().getPassword().equals(password)) {
                return new Result(false , "Password is incorrect.");
            }
            for (Product product : App.getLoggedStore().products) {
                App.products.remove(product);
                for (User user : App.users) {
                    user.cart.items.removeIf(item -> item.getProduct().getId() == product.getId());
                }
            }
            App.stores.remove(App.getLoggedStore());
        }
        App.setLoggedUser(null);
        App.setLoggedStore(null);
        App.setCurrentMenu(Menu.MainMenu , "Account deleted successfully. Redirecting to the MainMenu ...");
        return new Result(true , "Account deleted successfully. Redirecting to the MainMenu ...");
    }
    public String goBack() {
        return App.setCurrentMenu(Menu.MainMenu);
    }

    public static boolean isEmailUsed(String email) {
        for (User user : App.users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        for (Store store : App.stores) {
            if (store.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public User getUserByEmail (String email) {
        for (User user : App.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public Store getStoreByEmail (String email) {
        for (Store store : App.stores) {
            if (store.getEmail().equals(email)) {
                return store;
            }
        }
        return null;
    }
}
