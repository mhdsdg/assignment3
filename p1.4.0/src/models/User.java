package models;

import java.util.ArrayList;
import models.enums.*;

public class User {
    private String username;
    private String password;
    private String email;
    private int points = 0;
    private ArrayList<Game> games = new ArrayList<>();
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addGames(Game game) {
        this.games.add(game) ;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
