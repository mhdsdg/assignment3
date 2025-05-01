package controllers;

import models.App;
import models.Game;
import models.Result;
import models.User;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainMenuController {
    public Result play(ArrayList<String> usersString){
        int userCount = 4 ;
        for (String user : new ArrayList<String>(usersString)) {
            if(user == null) {
                userCount--;
                usersString.remove(null);
            }
        }
        ArrayList<User> users = new ArrayList<>();
        boolean allFound = true;
        boolean userIsLogged = false;
        for (int i = 0; i < userCount; i++) {
            User user = getUser(usersString.get(i));
            if(user == null) allFound = false;
            if(usersString.get(i).equals(App.getLoggedUser().getUsername())) userIsLogged = true;
            users.add(user);
        }
        if(!allFound){
            return new Result(false, "select between available users");
        }
        Set<User> usersSet = new HashSet<>(users);
        boolean allUnique = true;
        if(usersSet.size() != userCount){
            allUnique = false;
        }
        if(!allUnique){
            return new Result(false, "users must be distinct");
        }
        if(userIsLogged){
            return new Result(false, "you can't choose urself");
        }
        users.add(0 , App.getLoggedUser());
        App.guestLogin();
        for (int i = 0; i < 4-userCount; i++) {
            users.add(App.getGuests().get(i));
        }
        Game game = new Game(Game.getIdCounter() , users);
        Game.setIdCounter(Game.getIdCounter() + 1);
        App.addGames(game);
        for (User user : users) {
            user.addGames(game);
        }
        App.setCurrentMenu(Menu.GAMEMENU);
        App.setCurrentGame(game);
        return new Result(true, "aghaaz faAliat");
    }

    public void leaderboard(){
        App.setCurrentMenu(Menu.LEADERBOARDMENU);
    }
    public void exit(){
        App.setCurrentMenu(Menu.EXITMENU);
    }
    public void logout(){
        App.setLoggedUser(null);
        App.setCurrentMenu(Menu.SIGNUPMENU);
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
