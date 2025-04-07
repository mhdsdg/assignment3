package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.Menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenuController {
    public void showInfo() {
        User user = App.getLoggedInUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("currency : " + user.getCurrency().symbol);
        System.out.println("email : " + user.getEmail());
        System.out.println("name : " + user.getName());
    }
    public Result changeCurrency(String newCurrency) {
        if(!isCurrencyValid(newCurrency)) {
            return new Result(false, "currency format is invalid!");
        }

        if(newCurrency.equals("QTR"))App.getLoggedInUser().setCurrency(Currency.QuantumNote);
        else if(newCurrency.equals("SUD"))App.getLoggedInUser().setCurrency(Currency.StarDollar);
        else App.getLoggedInUser().setCurrency(Currency.GalacticCoin);

        return new Result(true, "your currency changed to " + newCurrency + " successfully!");
    }
    public Result changeUsername(String newUsername) {
        if(App.getLoggedInUser().getUsername().equals(newUsername)) {
            return new Result(false, "please enter a new username!");
        }
        if(getByUsername(newUsername) != null) {
            return new Result(false, "this username is already taken!");
        }
        if(!isUsernameValid(newUsername)) {
            return new Result(false, "new username format is invalid!");
        }
        App.getLoggedInUser().setName(newUsername);
        return new Result(true, "your username changed to " + newUsername + " successfully!");
    }
    public Result changePassword(String newPassword , String oldPassword) {
        if (!oldPassword.equals(App.getLoggedInUser().getPassword())) {
            return new Result(false, "password incorrect!");
        }
        if (newPassword.equals(App.getLoggedInUser().getPassword())) {
            return new Result(false, "please enter a new password!");
        }
        if (!isPasswordValid(newPassword)) {
            return new Result(false, "new password format is invalid!");
        }
        App.getLoggedInUser().setPassword(newPassword);
        return new Result(true, "your password changed successfully!");
    }
    public String back() {
        App.setCurrentMenu(Menu.DASHBOARD);
        return "you are now in dashboard!";
    }
    public void exit() {
        App.setCurrentMenu(Menu.EXIT);
    }


    public boolean isCurrencyValid (String currency) {
        String[] currencies = {"SUD", "QTR", "GTC"};
        for (String s : currencies) {
            if (s.equals(currency)) {
                return true;
            }
        }
        return false;
    }
    public User getByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) { return user; }
        }
        return null;
    }
    public boolean isUsernameValid(String username) {
        return username.length() <= 10 && username.length() >= 4 && username.matches("^[a-zA-Z][a-zA-Z0-9-_.]*$");

    }
    public boolean isPasswordValid(String password) {
        String[] regexes = {"[a-z]","[A-Z]","[0-9]","[!@#$%^&*]"};
        for (String regex : regexes) {
            Matcher matcher = Pattern.compile(regex).matcher(password);
            if (!matcher.find()) {
                return false;
            }
        }
        if(password.matches("^[a-zA-Z0-9!@#$%^&*]*$")) return true;
        return false;
    }
}
