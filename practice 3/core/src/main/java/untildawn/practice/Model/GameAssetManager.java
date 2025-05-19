package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager manager;

    private final Skin skin = new Skin(Gdx.files.internal("skin/quantumSkinRed.json"));

    public static GameAssetManager getManager() {
        if(manager == null) {
             manager = new GameAssetManager();
        }
        return manager;
    }

    public Skin getSkin() {
        return skin;
    }
}
