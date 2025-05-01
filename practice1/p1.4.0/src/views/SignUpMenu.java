package views;

import controllers.SignupMenuController;
import models.enums.SignUpMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu extends AppMenu{
    public final String name = "signup menu";
    public SignupMenuController controller = new SignupMenuController();
    public SignUpMenu() {
        super.name = name;
    }
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = SignUpMenuCommands.Register.getMatcher(input)) != null) {
            System.out.println(controller.register(matcher.group("username"), matcher.group("password"), matcher.group("email")).message());
        }
        else if(SignUpMenuCommands.GoToLogin.getMatcher(input) != null) {
            controller.goToLogin();
        }
        else if(SignUpMenuCommands.Exit.getMatcher(input) != null) {
            controller.exit();
        }
        else if(SignUpMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            controller.showCurrentMenu();
        }
        else{
            System.out.println("invalid command");
        }
    }
}
