package untildawn.practice.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import untildawn.practice.Main;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.View.MainMenu;
import untildawn.practice.View.ScoreboardMenu;

public class ScoreboardMenuController {
    private ScoreboardMenu view;

    public void setView(ScoreboardMenu view) {
        this.view = view;
    }

    public ClickListener getBackListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }
}
