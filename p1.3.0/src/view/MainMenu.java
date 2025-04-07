package view;

import controllers.MainMenuController;
import model.enums.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu{
    MainMenuController controller = new MainMenuController();
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher ;
        if((matcher = MainMenuCommands.GoToSubMenu.getMather(input)) != null) {
            System.out.println(controller.goToSubMenu(matcher.group(1)).message());
        }
        else if((MainMenuCommands.Exit.getMather(input)) != null) {
            controller.exit();
        }
        else {
            System.out.println("invalid command");
        }
    }
}
