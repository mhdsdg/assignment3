package model.enums;

import view.*;

import java.util.Scanner;

public enum Menu {
    MainMenu(new MainMenu()),
    LoginMenu(new LoginMenu()),
    ProductMenu(new ProductMenu()),
    UserMenu(new UserMenu()),
    StoreMenu(new StoreMenu()),
    ExitMenu(new ExitMenu());
    private final AppMenu menu;
    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }

}
