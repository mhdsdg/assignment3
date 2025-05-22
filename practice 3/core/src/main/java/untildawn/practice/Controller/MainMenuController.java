package untildawn.practice.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import untildawn.practice.Main;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.View.LoginMenu;
import untildawn.practice.View.MainMenu;
import untildawn.practice.View.PreGameMenu;
import untildawn.practice.View.ProfileMenu;

public class MainMenuController {
    private MainMenu view;

    public void setView(MainMenu view) {
        this.view = view;
    }

    public ClickListener getPreGameListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenu(new PreGameMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }

    public ClickListener getProfileListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }
//    public ClickListener getScoreBoardListener() { /* ... */ }
//    public ClickListener getSettingsListener() { /* ... */ }
//    public ClickListener getHintMenuListener() { /* ... */ }
    public ClickListener getLogoutListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }
}
