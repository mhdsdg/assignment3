package controllers;

import models.App;
import models.User;
import models.enums.Menu;
import models.Result;

public class LoginMenuController {
    public Result Login(String username, String password) {
        User user = getUser(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedUser(user);
        App.setCurrentMenu(Menu.MAINMENU);
        return new Result(true, "user logged in successfully");
    }
    public Result forgetPassword(String username , String email) {
        User user = getUser(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        }
        if(!user.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password: "+user.getPassword());
    }
    public void back(){
        App.setCurrentMenu(Menu.SIGNUPMENU);
    }
    public void exit(){
        App.setCurrentMenu(Menu.EXITMENU);
    }
    public void showCurrentMenu(){
        System.out.println(App.getCurrentMenu().menu.name);
    }

    public User getUser(String username){
        for (User user : App.getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
