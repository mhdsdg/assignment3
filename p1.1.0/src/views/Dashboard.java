package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.DashboardController;
import models.enums.DashboardCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dashboard implements AppMenu{
    DashboardController controller = new DashboardController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if((matcher = DashboardCommands.CreateGroup.getMatcher(input))!= null){
            System.out.println(controller.createGroup(matcher.group("name"), matcher.group("type")).message());
        }
        else if((matcher = DashboardCommands.ShowOwnGroups.getMatcher(input))!= null){
            controller.showOwnGroups();
        }
        else if((matcher = DashboardCommands.AddToGroup.getMatcher(input))!= null){
            System.out.println(controller.addToGroup(matcher.group("email"), matcher.group("username"), matcher.group("groupid")).message());
        }
        else if((matcher = DashboardCommands.AddExpense.getMatcher(input))!= null){
            boolean equal = matcher.group("type").equals("equally");
            String numString = matcher.group("numberofusers");
            int number = Integer.parseInt(numString);
            HashMap<String , Integer> userValue = new HashMap<>();
            ArrayList<String> users = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                input = scanner.nextLine();
                Matcher matcher2 ;
                int amount = 0;
                if(equal) {
                    matcher2 = DashboardCommands.EquallyUser.getMatcher(input);
                }
                else {
                    matcher2 = DashboardCommands.UnequallyUser.getMatcher(input);
                    amount = Integer.parseInt(matcher2.group("amount"));
                }
                users.add(matcher2.group("username"));
                userValue.put(matcher2.group("username"), amount);
            }
            System.out.println(controller.addExpense(matcher.group("groupid"), matcher.group("type"),
                    matcher.group("totalexpense"), matcher.group("numberofusers"), userValue, users).message());
        }
        else if((matcher = DashboardCommands.ShowBalance.getMatcher(input))!= null){
            System.out.println(controller.showBalance(matcher.group("username")).message());
        }
        else if((matcher = DashboardCommands.SettleUp.getMatcher(input))!= null){
            System.out.println(controller.settleUpWith(matcher.group("username"), matcher.group("inputmoney")).message());
        }
        else if((matcher = DashboardCommands.Logout.getMatcher(input))!= null){
            System.out.println(controller.logout());
        }
        else if((matcher = DashboardCommands.GoToProfile.getMatcher(input))!= null){
            System.out.println(controller.goToProfile());
        }
        else if ((matcher = DashboardCommands.Exit.getMatcher(input))!= null){
            controller.exit();
        }
        else {
            System.out.println("Invalid command");
        }
    }
}
