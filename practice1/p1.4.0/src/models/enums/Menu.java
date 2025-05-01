package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    SIGNUPMENU(new SignUpMenu()),
    LOGINMENU(new LoginMenu()),
    MAINMENU(new MainMenu()),
    LEADERBOARDMENU(new LeaderBoardMenu()),
    GAMEMENU(new GameMenu()),
    EXITMENU(new ExitMenu());
    public AppMenu menu;
    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
