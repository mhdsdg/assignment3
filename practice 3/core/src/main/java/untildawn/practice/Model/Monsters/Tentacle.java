package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import untildawn.practice.Model.CollisionRect;
import untildawn.practice.Model.GameAssetManager;

public class Tentacle {
    private final TextureRegion texture = GameAssetManager.getTentacleTexture();
    private final Animation<TextureRegion> animation = GameAssetManager.getTentacleAnimation();
    private final Sprite sprite = new Sprite(texture);
    private float x;
    private float y;
    private CollisionRect rect;
    private float HP = 25;
    private float stateTime = 0;

    public Tentacle(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
        sprite.setSize(80, 80);
        rect = new CollisionRect(x , y , 80 , 80);

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float delta) { // Add this method
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getHP() {
        return HP;
    }
    public void setHP(float HP) {
        this.HP = HP;
    }
}
