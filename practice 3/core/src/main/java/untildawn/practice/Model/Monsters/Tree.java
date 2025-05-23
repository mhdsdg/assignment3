package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import untildawn.practice.Main;
import untildawn.practice.Model.CollisionRect;
import untildawn.practice.Model.GameAssetManager;

public class Tree extends Enemy {
    private final TextureRegion texture = GameAssetManager.getTreeTexture();
    private final Animation<TextureRegion> animation = GameAssetManager.getTreeAnimation();
    private final Sprite sprite = new Sprite(texture);
    private float x;
    private float y;
    private float HP = 40;
    private float stateTime = 0;

    public Tree(float x, float y) {
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
