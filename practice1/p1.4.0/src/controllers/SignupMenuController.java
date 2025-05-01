package controllers;

import models.App;
import models.User;
import models.enums.Menu;
import models.enums.Regexes;
import models.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupMenuController {
    public Result register(String username, String password , String email) {
        if(Regexes.UserName.getMatcher(username) == null) {
            return new Result(false, "invalid username");
        }
        if(getUser(username) != null) {
            return new Result(false, "username is already taken");
        }
        if(password.length() < 8 || password.length() > 20) {
            return new Result(false, "invalid length");
        }
        if(password.contains(" ")){
            return new Result(false, "don't use whitespace in password");
        }
        Matcher matcher = Pattern.compile("^[a-zA-Z]").matcher(password);
        if(!matcher.find()) {
            return new Result(false, "password must start with English letters");
        }
        matcher = Pattern.compile("[%@#$^&!]").matcher(password);
        if(!matcher.find()) {
            return new Result(false, "password doesn't have special characters");
        }
        matcher = Regexes.Email.getMatcher(email);
        if(matcher == null || matcher.group("mail").chars().filter(ch -> ch == '.').count() > 1) {
            return new Result(false, "invalid email format");
        }
        App.getUsers().add(new User(username, password, email));
        App.setCurrentMenu(Menu.LOGINMENU);
        return new Result(true, "user registered successfully");
    }
    public void goToLogin(){
        App.setCurrentMenu(Menu.LOGINMENU);
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
