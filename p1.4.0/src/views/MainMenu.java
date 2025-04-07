package views;

import controllers.MainMenuController;
import models.enums.MainMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends AppMenu{
    public MainMenuController controller = new MainMenuController();
    public final String name = "main menu";
    public MainMenu() {
        super.name = name;
    }
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = MainMenuCommands.Play.getMatcher(input)) != null) {
            ArrayList<String> usernames = new ArrayList<>();
            usernames.add(matcher.group("user1"));
            usernames.add(matcher.group("user2"));
            usernames.add(matcher.group("user3"));
            usernames.add(matcher.group("user4"));
            System.out.println(controller.play(usernames).message());

        }
        else if(MainMenuCommands.LeaderBoard.getMatcher(input) != null) {
            controller.leaderboard();
        }
        else if(MainMenuCommands.Logout.getMatcher(input) != null) {
            controller.logout();
        }
        else if(MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            controller.showCurrentMenu();
        }
        else if(MainMenuCommands.exit.getMatcher(input) != null) {
            controller.exit();
        }
        else{
            System.out.println("invalid command");
        }
    }
}
