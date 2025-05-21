package untildawn.practice.Controller.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Player;

public class HealthBarController {
    private TextureRegion heartFull = GameAssetManager.getHeartFull();
    private TextureRegion heartEmpty = GameAssetManager.getHeartEmpty();
    private Animation<TextureRegion> heartLossAnimation = GameAssetManager.getHeartLossAnimation();
    private Player player;
    private float heartSize = 60f;
    private float padding = 10f;

    // Track animation states for each heart
    private Array<HeartAnimationState> heartAnimations = new Array<>();
    private int lastHealth = 0;

    private class HeartAnimationState {
        int heartIndex;
        float stateTime = 0f;
        boolean active = false;

        public HeartAnimationState(int index) {
            this.heartIndex = index;
        }
    }

    public HealthBarController(Player player) {
        this.player = player;
        this.lastHealth = player.getHP();

        // Initialize animation states for all hearts
        for (int i = 0; i < player.getMaxHP(); i++) {
            heartAnimations.add(new HeartAnimationState(i));
        }
    }

    public void update(float delta) {
        // Check for health changes
        if (player.getHP() < lastHealth) {
            // Find which heart(s) were lost
            for (int i = lastHealth - 1; i >= player.getHP(); i--) {
                if (i >= 0 && i < heartAnimations.size) {
                    heartAnimations.get(i).active = true;
                    heartAnimations.get(i).stateTime = 0f;
                }
            }
        }
        lastHealth = player.getHP();

        // Update active animations
        for (HeartAnimationState state : heartAnimations) {
            if (state.active) {
                state.stateTime += delta;
                if (heartLossAnimation.isAnimationFinished(state.stateTime)) {
                    state.active = false;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        float startX = padding;
        float y = Gdx.graphics.getHeight() - heartSize - padding;

        for (int i = 0; i < player.getMaxHP(); i++) {
            // Check if this heart has an active animation
            HeartAnimationState state = heartAnimations.get(i);

            if (state.active) {
                // Draw the animation frame
                TextureRegion frame = heartLossAnimation.getKeyFrame(state.stateTime);
                batch.draw(frame, startX + i * (heartSize + padding), y, heartSize, heartSize);
            } else {
                // Draw normal heart state
                TextureRegion heart = (i < player.getHP()) ? heartFull : heartEmpty;
                batch.draw(heart, startX + i * (heartSize + padding), y, heartSize, heartSize);
            }
        }
    }
}
