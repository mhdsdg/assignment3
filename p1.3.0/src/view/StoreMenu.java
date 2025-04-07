package view;

import controllers.StoreMenuController;
import model.enums.StoreMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StoreMenu implements AppMenu{
    StoreMenuController controller= new StoreMenuController();
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if((matcher = StoreMenuCommands.AddProduct.getMather(input)) != null) {
            System.out.println(controller.addProduct(matcher.group(1), matcher.group(2) , matcher.group(3) , matcher.group(4) , matcher.group(5)).message());
        }
        else if((matcher = StoreMenuCommands.ApplyDiscount.getMather(input)) != null) {
            System.out.println(controller.applyDiscount(matcher.group(1), matcher.group(2), matcher.group(3)).message());
        }
        else if(( StoreMenuCommands.ShowProfit.getMather(input)) != null) {
            System.out.println(controller.showProfit());
        }
        else if((StoreMenuCommands.ShowProductList.getMather(input)) != null) {
            System.out.print(controller.listStoreProducts().message());
        }
        else if((matcher = StoreMenuCommands.AddToStock.getMather(input)) != null) {
            System.out.println(controller.addStock(matcher.group(1), matcher.group(2)).message());
        }
        else if((matcher = StoreMenuCommands.UpdatePrice.getMather(input)) != null) {
            System.out.println(controller.updatePrice(matcher.group(1), matcher.group(2)).message());
        }
        else if(StoreMenuCommands.GoBack.getMather(input) != null) {
            System.out.println(controller.goBack());
        }
        else{
            System.out.println("invalid command");
        }
    }
}
