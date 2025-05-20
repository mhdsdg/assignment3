package untildawn.practice.Controller;

import untildawn.practice.Controller.GameControllers.GameController;
import untildawn.practice.Main;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.View.GameView;
import untildawn.practice.View.PreGameMenu;

public class PreGameMenuController {
    PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleAdvanceButton() {
        if(view != null){
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getManager().getSkin()));
        }
    }
}
