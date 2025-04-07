package views;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.LoginMenuController;
import models.App;
import models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if ((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            System.out.println(controller.Login(matcher.group("username"),matcher.group("password")).message());
        }
        else if ((matcher = LoginMenuCommands.ForgotPassword.getMatcher(input)) != null) {
            System.out.println(controller.forgotPassword(matcher.group("username"),matcher.group("email")).message());
        }
        else if ((matcher = LoginMenuCommands.GoToSignUp.getMatcher(input)) != null) {
            System.out.println(controller.goToSignUp());
        }
        else if ((matcher = LoginMenuCommands.Exit.getMatcher(input)) != null) {
            controller.exit();
        }
        else System.out.println("invalid command!");
    }
}
