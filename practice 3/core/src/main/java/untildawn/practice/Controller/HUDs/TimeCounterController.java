package untildawn.practice.Controller.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TimeCounterController {
    private BitmapFont timeFont;
    private float totalTime;
    private float totalTimeMax;
    private float padding = 20f;
    private float fontSize = 36f;

    public TimeCounterController(Skin skin, float totalTimeMax) {
        this.totalTimeMax = totalTimeMax;
        initFont(skin);
    }

    private void initFont(Skin skin) {
        timeFont = skin.getFont("ChevyRay_-_Express");
    }

    public void update(float currentTime) {
        this.totalTime = currentTime;
    }

    public void render(SpriteBatch batch) {
        float remaining = Math.max(0, totalTimeMax - totalTime);
        int seconds = (int)remaining;

        // Position in upper right corner
        float x = Gdx.graphics.getWidth() - padding - 100f; // Allow space for 3 digits
        float y = Gdx.graphics.getHeight() - padding;

        // Format as 3-digit number (e.g., "060")
        String timeText = String.format("%03d", seconds);
        timeFont.draw(batch, timeText, x, y);
    }

    public void dispose() {
        timeFont.dispose();
    }
}
