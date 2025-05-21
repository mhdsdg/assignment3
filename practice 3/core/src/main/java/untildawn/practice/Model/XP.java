package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import untildawn.practice.Main;

public class XP {
    private final Texture texture = GameAssetManager.getXPTexture();
    private final Sprite sprite = new Sprite(texture);
    private float x;
    private float y;
    private CollisionRect rect;

    public XP(float x,float y) {
        sprite.setSize(20, 20);
        sprite.setX(x);
        sprite.setY(y);
        rect = new CollisionRect(x,y, 5, 5);
        this.x = x;
        this.y = y;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
