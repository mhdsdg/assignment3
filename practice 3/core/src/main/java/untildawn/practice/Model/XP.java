package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class XP {
    private final Texture texture = GameAssetManager.getXPTexture();
    private final Sprite sprite = new Sprite(texture);
    private int x;
    private int y;
    private CollisionRect rect;

    public XP(int x,int y) {
        sprite.setSize(20, 20);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        rect = new CollisionRect(x,y, 5, 5);
        this.x = x;
        this.y = y;
    }
}
