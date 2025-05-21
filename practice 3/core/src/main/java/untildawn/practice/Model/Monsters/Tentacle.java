package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.CollisionRect;
import untildawn.practice.Model.GameAssetManager;

public class Tentacle {
    private float x, y; // World coordinates
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private final CollisionRect rect;
    private float stateTime = 0;
    private float speed = 50f;
    private Vector2 direction = new Vector2();
    private float HP = 25;
    private boolean lookingRight;

    public Tentacle(float x, float y) {
        this.x = x;
        this.y = y;
        this.animation = GameAssetManager.getTentacleAnimation();
        this.sprite = new Sprite(GameAssetManager.getTentacleTexture());
        this.sprite.setSize(64, 64);
        this.sprite.setPosition(x, y);
        this.rect = new CollisionRect(x, y, 64, 64);

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float delta, float playerX, float playerY) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime));

        // Calculate direction to player
        direction.set(playerX - x, playerY - y).nor();

        // Update world position
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;

        // Update collision rect (world space)
        rect.move(x, y);
    }

    public void draw(float offsetX, float offsetY) {
        sprite.setPosition(x - offsetX, y - offsetY);
        rect.move(sprite.getX(), sprite.getY());
        sprite.draw(Main.getBatch());
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getHP() { return HP; }
    public void setHP(float HP) { this.HP = HP; }
}

