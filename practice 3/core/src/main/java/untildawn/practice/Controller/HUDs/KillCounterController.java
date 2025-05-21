package untildawn.practice.Controller.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class KillCounterController {
    private BitmapFont killFont;
    private int killCount;
    private float padding = 20f;
    private float fontSize = 32f; // Slightly smaller than time/ammo counters

    public KillCounterController(Skin skin) {
        initFont(skin);
    }

    private void initFont(Skin skin) {
        this.killFont = skin.getFont("ChevyRay_-_Express");
    }

    public void update(int kills) {
        this.killCount = kills;
    }

    public void render(SpriteBatch batch) {
        // Position in lower left corner
        float x = padding;
        float y = padding + 50f; // Above bottom edge

        killFont.draw(batch, String.format("%d KILLS", killCount), x, y);
    }

    public void dispose() {
        killFont.dispose();
    }
}
