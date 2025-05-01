package views;

import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu{
    public LoginMenuController controller = new LoginMenuController();
    public final String name = "login menu";
    public LoginMenu() {
        super.name = name;
    }
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            System.out.println(controller.Login(matcher.group("username"), matcher.group("password")).message());
        }
        else if(LoginMenuCommands.Back.getMatcher(input) != null) {
            controller.back();
        }
        else if(LoginMenuCommands.Exit.getMatcher(input) != null) {
            controller.exit();
        }
        else if((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
            System.out.println(controller.forgetPassword(matcher.group("username"), matcher.group("email")).message());
        }
        else if(LoginMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            controller.showCurrentMenu();
        }
        else{
            System.out.println("invalid command");
        }
    }
}
