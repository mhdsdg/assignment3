package controllers;

import model.App;
import model.Result;
import model.enums.Menu;

public class MainMenuController {
    public Result goToSubMenu(String menuString) {
        Menu menu = Menu.valueOf(menuString);
        if(menu == Menu.UserMenu && App.getLoggedUser() == null) {
            return new Result(false, "You need to login as a user before accessing the user menu.");
        }
        if(menu == Menu.StoreMenu && App.getLoggedStore() == null) {
            return new Result(false , "You should login as store before accessing the store menu.");
        }
        return new Result (true , App.setCurrentMenu(menu));
    }
    public void exit (){
        App.setCurrentMenu(Menu.ExitMenu);
    }
}
