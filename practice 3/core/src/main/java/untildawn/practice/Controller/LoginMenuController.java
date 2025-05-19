package untildawn.practice.Controller;

import untildawn.practice.Main;
import untildawn.practice.View.LoginMenu;
import untildawn.practice.View.MainMenu;

public class LoginMenuController {
    public LoginMenu view;
    public void setView(LoginMenu loginMenu) {
        this.view = loginMenu;
    }

    public void handleLogin() {
        if(view != null && view.getAdvanceButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu());
        }
    }
}
