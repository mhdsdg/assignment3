package untildawn.practice.Model;

import com.badlogic.gdx.graphics.Texture;

public class World {
    private Texture BackgroundTexture = GameAssetManager.getBackground();


    public Texture getBackgroundTexture() {
        return BackgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        BackgroundTexture = backgroundTexture;
    }
}
