package view;

import controllers.ProductMenuController;
import model.enums.ProductMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductMenu implements AppMenu {
    ProductMenuController controller = new ProductMenuController();
    String input = null;
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher ;
        if((matcher = ProductMenuCommands.ShowProducts.getMather(input)) != null) {
            controller.showProducts(matcher.group(1));
        }
        else if(ProductMenuCommands.NextPage.getMather(input) != null) {
            controller.showNext10();
        }
        else if(ProductMenuCommands.PreviousPage.getMather(input) != null) {
            controller.showPrevious10();
        }
        else if((matcher = ProductMenuCommands.ShowProductDetails.getMather(input)) != null) {
            System.out.println(controller.showProductDetails(matcher.group(1)).message());
        }
        else if((matcher = ProductMenuCommands.RateProduct.getMather(input)) != null) {
            System.out.println(controller.setARating(matcher.group(1) , matcher.group(3) , matcher.group(4)).message());
        }
        else if((matcher = ProductMenuCommands.AddToCart.getMather(input)) != null) {
            System.out.println(controller.addProduct(matcher.group(1) , matcher.group(2)).message());
        }
        else if((matcher = ProductMenuCommands.GoBack.getMather(input)) != null) {
            System.out.println(controller.goBack());
        }
        else{
            System.out.println("invalid command");
        }
    }
}
