package view;

import controllers.LoginMenuController;
import model.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    LoginMenuController controller = new LoginMenuController();
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher ;
        if((matcher = LoginMenuCommands.CreateUser.getMather(input)) != null) {
            System.out.println(controller.createUser(matcher.group(1) , matcher.group(2) , matcher.group(3) , matcher.group(4) , matcher.group(5)).message());
        }
        else if((matcher = LoginMenuCommands.CreateStore.getMather(input)) != null) {
            System.out.println(controller.createStore(matcher.group(1) , matcher.group(2) , matcher.group(3) , matcher.group(4)).message());
        }
        else if((matcher = LoginMenuCommands.LoginUser.getMather(input)) != null) {
            System.out.println(controller.loginUser(matcher.group(1) , matcher.group(2)).message());
        }
        else if((matcher = LoginMenuCommands.LoginStore.getMather(input)) != null) {
            System.out.println(controller.loginStore(matcher.group(1) , matcher.group(2)).message());
        }
        else if((LoginMenuCommands.Logout.getMather(input)) != null) {
            System.out.println(controller.logout().message());
        }
        else if((matcher = LoginMenuCommands.DeleteAccount.getMather(input)) != null) {
            System.out.println(controller.deleteAccount(matcher.group(1) , matcher.group(2)).message());
        }
        else if((LoginMenuCommands.GoBack.getMather(input)) != null) {
            System.out.println(controller.goBack());
        }
        else {
            System.out.println("invalid command");
        }
    }
}
