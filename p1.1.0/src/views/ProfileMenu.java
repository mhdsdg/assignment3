package views;
/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.ProfileMenuController;
import models.enums.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu{
    ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if((matcher = ProfileMenuCommands.ShowUserInfo.getMatcher(input)) != null){
            controller.showInfo();
        }
        else if((matcher = ProfileMenuCommands.ChangeCurrency.getMatcher(input)) != null){
            System.out.println(controller.changeCurrency(matcher.group("newcurrency")).message());
        }
        else if((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null){
            System.out.println(controller.changeUsername(matcher.group("newusername")).message());
        }
        else if((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null){
            System.out.println(controller.changePassword(matcher.group("newpassword"), matcher.group("oldpassword")).message());
        }
        else if((matcher = ProfileMenuCommands.Back.getMatcher(input)) != null){
            System.out.println(controller.back());
        }
        else if((matcher = ProfileMenuCommands.Exit.getMatcher(input)) != null){
            controller.exit();
        }
        else System.out.println("invalid command!");
    }
}
