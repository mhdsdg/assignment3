package untildawn.practice.Model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Random;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;
    private static boolean isGuest = false;
    public static User guestUser = new User("Guest", "Guest");
    private static Texture guestAvatar = GameAssetManager.getGuestAvatar();
    private static String language = "English";
    private static Random rand = new Random();

    public static void changeLanguage(){
        if(language.equals("English")){
            language = "Medieval";
        }
        else{
            language = "English";
        }
    }
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static boolean isIsGuest() {
        return isGuest;
    }

    public static void setIsGuest(boolean isGuest) {
        App.isGuest = isGuest;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        App.language = language;
    }

    public static Random getRand() {
        return rand;
    }

    public static void setRand(Random rand) {
        App.rand = rand;
    }

    public static Texture getGuestAvatar() {
        return guestAvatar;
    }

    public static void setGuestAvatar(Texture guestAvatar) {
        App.guestAvatar = guestAvatar;
    }
}
