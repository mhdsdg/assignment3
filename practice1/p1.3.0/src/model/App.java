package model;

import model.enums.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class App {
    private static Menu currentMenu = Menu.MainMenu;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Store> stores = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Page> pages = new ArrayList<>();
    public static int pageIndex = 0;
    public static Page currentPage;
    public static int pageCount;
    private static User loggedUser = null;
    private static Store loggedStore = null;



    public static Menu getCurrentMenu() {return currentMenu;}

    public static String setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
        return "Redirecting to the " + currentMenu + " ...";
    }
    public static void setCurrentMenu(Menu currentMenu , String message) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        App.loggedUser = loggedUser;
    }

    public static Store getLoggedStore() {
        return loggedStore;
    }

    public static void setLoggedStore(Store loggedStore) {
        App.loggedStore = loggedStore;
    }

    public static void formPages(String sortBy){
        pages.clear();
        pageCount = (int)Math.ceil((double) products.size() /10);
        switch(sortBy){
            case "rate":
                products.sort(Comparator.comparingDouble(Product::getRating).reversed().thenComparingInt(Product::getId));
                break;
            case "higher price to lower":
                products.sort(Comparator.comparingDouble(Product::getPrice).reversed().thenComparingInt(Product::getId));
                break;
            case "lower price to higher":
                products.sort(Comparator.comparingDouble(Product::getPrice).thenComparingInt(Product::getId));
                break;
            case "number of sold":
                products.sort(Comparator.comparingInt(Product::getNumberOfSoldProducts).reversed().thenComparingInt(Product::getId));
        }
        char firstChar = sortBy.charAt(0);
        sortBy = Character.toUpperCase(firstChar) + sortBy.substring(1);
        for (int i = 0; i < pageCount; i++) {
            currentPage = formPage(10 * i , sortBy);
            pages.add(new Page(currentPage.information));
        }
    }
    public static Page formPage(int index, String sortBy){
        Page page = new Page("Product List (Sorted by: " + sortBy + ")\n");
        page.information += "------------------------------------------------\n";
        for(int i = index ; i < Math.min(products.size() , index+10); i++){
            page.information += "ID: " + products.get(i).getId() + "  ";
            if(products.get(i).getOnSaleQuantity() > 0){
                page.information += "**(On Sale! "+products.get(i).getOnSaleQuantity()+" units discounted)**";
            }
            if(products.get(i).getOnSaleQuantity() == 0 && products.get(i).getRegularQuantity() == 0){
                page.information += "**(Sold out!)**";
            }
            page.information += "\n";
            page.information += "Name: "+products.get(i).getName() + "\nRate: " + products.get(i).getRatingString() + "/5\n";
            page.information += "Price: " ;
            if(products.get(i).getOnSaleQuantity() > 0){
                page.information += "~";
            }
            page.information += String.format("$%.1f", products.get(i).price);
            if(products.get(i).getOnSaleQuantity() > 0){
                page.information += "~ → $" + String.format("%.1f", products.get(i).getPrice()) + " (-" + (int)products.get(i).getDiscount() + "%)";
            }
            page.information += "\n";
            page.information += "Brand: " + products.get(i).getBrand() + "\n" + "Stock: " + (products.get(i).getOnSaleQuantity() + products.get(i).getRegularQuantity());
            page.information += "\n------------------------------------------------\n";
        }
        page.information += "(Showing " + (index+1) + "-" + (index + 10) + " out of " + products.size() + ")";
        if(products.size() >= index + 10){
            page.information += "\nUse `show next 10 products' to see more.";
        }
        return page;
    }
}
