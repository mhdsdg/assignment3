package untildawn.practice.Controller.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Model.Player;

public class XPLevelController {
    private BitmapFont levelFont;
    private ShapeRenderer shapeRenderer;
    private float padding = 50f;
    private float barWidth = 200f;
    private float barHeight = 20f;

    // Colors
    private Color bgColor = new Color(0.2f, 0.2f, 0.2f, 0.7f);
    private Color xpColor = new Color(0.1f, 0.7f, 0.3f, 0.9f); // Green XP bar
    private Color borderColor = new Color(0.8f, 0.8f, 0.8f, 1f);

    public XPLevelController(Skin skin) {
        initFont(skin);
        this.shapeRenderer = new ShapeRenderer();
    }

    private void initFont(Skin skin) {
        levelFont = skin.getFont("ChevyRay_-_Express");
    }

    public void render(SpriteBatch batch, Player player) {
        float progress = (float)player.getThisLevelXp() / player.getXpNeeded();

        // Position in lower right corner
        float barX = Gdx.graphics.getWidth() - barWidth - padding - 90f; // Space for level text
        float barY = padding;

        // End batch before drawing shapes
        batch.end();

        // Draw XP bar background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bgColor);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        // Draw XP progress
        shapeRenderer.setColor(xpColor);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);

        // Draw border
        shapeRenderer.setColor(borderColor);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.end();

        // Restart batch for text
        batch.begin();

        // Draw level text
        float textX = Gdx.graphics.getWidth() - padding - 80f;
        float textY = barY + barHeight/2 + levelFont.getCapHeight()/2;
        levelFont.draw(batch, "Lv." + player.getLevel(), textX, textY);
    }

    public void dispose() {
        levelFont.dispose();
        shapeRenderer.dispose();
    }
}
