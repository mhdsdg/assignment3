package view;

import controllers.UserMenuController;
import model.Result;
import model.enums.UserMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UserMenu implements AppMenu{
    UserMenuController controller = new UserMenuController();
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if((UserMenuCommands.ListMyOrders.getMather(input)) != null) {
            Result result = controller.listMyOrders();
            if(result.isSuccessful() == false) {
                System.out.println(result.message());
            }
        }
        else if((matcher = UserMenuCommands.ShowOrderDetails.getMather(input)) != null) {
            Result result = controller.showOrderDetails(matcher.group(1));
            if(result.isSuccessful() == false) {
                System.out.println(result.message());
            }
        }
        else if((matcher = UserMenuCommands.EditUsername.getMather(input)) != null) {
            System.out.println(controller.editName(matcher.group(1) , matcher.group(2) , matcher.group(3)).message());
        }
        else if((matcher = UserMenuCommands.EditPassword.getMather(input)) != null) {
            System.out.println(controller.editPassword(matcher.group(1) , matcher.group(2)).message());
        }
        else if((matcher = UserMenuCommands.EditEmail.getMather(input)) != null) {
            System.out.println(controller.editEmail(matcher.group(1) , matcher.group(2)).message());
        }
        else if(UserMenuCommands.ShowMyInfo.getMather(input) != null) {
            controller.showMyInfo();
        }
        else if(( matcher = UserMenuCommands.AddAddress.getMather(input)) != null) {
            System.out.println(controller.addAddress(matcher.group(1) , matcher.group(2) , matcher.group(3) , matcher.group(4)).message());
        }
        else if((matcher = UserMenuCommands.DeleteAddress.getMather(input)) != null) {
            System.out.println(controller.deleteAddress(matcher.group(1)).message());
        }
        else if((UserMenuCommands.ListMyAddresses.getMather(input)) != null) {
            controller.listAddresses();
        }
        else if((matcher = UserMenuCommands.AddCreditCard.getMather(input)) != null) {
            System.out.println(controller.addCreditCard(matcher.group(1) , matcher.group(2) , matcher.group(3) , matcher.group(4)).message());
        }
        else if((matcher = UserMenuCommands.ChargeCreditCard.getMather(input)) != null) {
            System.out.println(controller.chargeCreditCard(matcher.group(2) , matcher.group(1)).message());
        }
        else if((matcher = UserMenuCommands.CheckCardBalance.getMather(input)) != null) {
            System.out.println(controller.balanceOfCreditCard(matcher.group(1)).message());
        }
        else if((UserMenuCommands.ShowCart.getMather(input)) != null) {
            Result result = controller.showCart();
            if(result.isSuccessful() == false) {
                System.out.println(result.message());
            }
        }
        else if((matcher = UserMenuCommands.Checkout.getMather(input)) != null) {
            System.out.println(controller.checkout(matcher.group(1) , matcher.group(2)).message());
        }
        else if((matcher = UserMenuCommands.RemoveFromCart.getMather(input)) != null) {
            System.out.println(controller.removeFromCart(matcher.group(1) , matcher.group(2)).message());
        }
        else if(UserMenuCommands.GoBack.getMather(input) != null) {
            System.out.println(controller.goBack());
        }
        else{
            System.out.println("invalid command");
        }
    }
}
