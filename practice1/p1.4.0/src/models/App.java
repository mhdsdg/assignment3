package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    private static User loggedUser = null;
    private static Game currentGame = null;
    private static ArrayList<User> users = new ArrayList<User>();
    private static ArrayList<User> guests = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Tile> tiles = new ArrayList<>();

    private static Menu currentMenu = Menu.SIGNUPMENU;

    public static void guestLogin() {
        for (int i = 1; i < 5; i++) {
            String username = "guest" + i;
            String password = "guest" + i;
            String email = "guest" + i + "@gmail.com";
            guests.add(new User(username, password, email));
        }
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        App.loggedUser = loggedUser;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGames(Game game) {
        App.games.add(game);
    }

    public static ArrayList<User> getGuests() {
        return guests;
    }

    public static void setGuests(ArrayList<User> guests) {
        App.guests = guests;
    }

    public static ArrayList<Tile> getTiles() {
        return tiles;
    }

    public static void setTiles(ArrayList<Tile> tiles) {
        App.tiles = tiles;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        App.currentGame = currentGame;
    }
}
