package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;

public class LoginMenuController {
    public Result Login(String userName, String password) {
        User user = getByUsername(userName);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedInUser(user);
        App.setCurrentMenu(Menu.DASHBOARD);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }
    public Result forgotPassword(String userName , String email) {
        User user = getByUsername(userName);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        String message = String.format("password : %s", user.getPassword());
        return new Result(true, message);
    }
    public String goToSignUp(){
        App.setCurrentMenu(Menu.SIGNUPMENU);
        return "you are now in signup menu!";
    }
    public void exit(){ App.setCurrentMenu(Menu.EXIT);}


    public User getByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) { return user; }
        }
        return null;
    }
}
