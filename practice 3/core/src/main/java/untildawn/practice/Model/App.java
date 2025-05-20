package untildawn.practice.Model;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser;
    private static boolean isGuest = false;
    private static String language = "English";


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
}
