package controllers;

import models.App;
import models.User;
import models.enums.Menu;

import java.util.Comparator;

public class LeaderBoardMenuController {
    public void showRankings(){
        App.getUsers().sort(Comparator.comparingInt(User::getPoints).reversed().thenComparing(user -> user.getUsername().toLowerCase()));
        System.out.println("Leaderboard:");
        for (User user : App.getUsers()) {
            System.out.println(user.getUsername() + " " + user.getPoints());
        }
    }
    public void showHistory(){
        System.out.println("History:");
        StringBuilder message = new StringBuilder();
        for (int i = App.getLoggedUser().getGames().size() - 1; i >= Math.max(App.getLoggedUser().getGames().size() - 5 , 0); i--) {
            for (User user : App.getLoggedUser().getGames().get(i).getUsers()) {
                message.append(user.getUsername()).append(" ").append(App.getLoggedUser().getGames().get(i).getPlayers().get(user).name).append(" ").append(App.getLoggedUser().getGames().get(i).getPlayers().get(user).score).append("\n");
            }
            message.append("----\n");
        }
        if(!message.isEmpty()){
            message = new StringBuilder(message.substring(0, message.length() - 5));
        }
        System.out.print(message);
    }
    public void exit(){
        App.setCurrentMenu(Menu.EXITMENU);
    }
    public void showCurrentMenu(){
        System.out.println(App.getCurrentMenu().menu.name);
    }
    public void back(){
        App.setCurrentMenu(Menu.MAINMENU);
    }
}
