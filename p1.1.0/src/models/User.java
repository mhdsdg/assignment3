package models;

/*
Explanation:
- User is definitely an object in our app.
- put the information that you need to store about the user here.
- you can put some functions here to manage the user data too.
 */

import models.enums.Currency;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    public ArrayList<Group> groups = new ArrayList<>();
    public HashMap<User , HashMap<Group , Integer>> balance = new HashMap<>();
    private Currency currency = Currency.GalacticCoin;

    public User(String username, String password, String email , String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name ;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}

    public String getEmail() { return email;}

    public void setEmail(String email) {this.email = email;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
