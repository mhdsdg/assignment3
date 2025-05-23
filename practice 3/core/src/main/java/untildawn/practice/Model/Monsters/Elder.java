package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.CollisionRect;
import untildawn.practice.Model.GameAssetManager;

import java.awt.*;

public class Elder extends Enemy{
    private final Sprite sprite;
    private float stateTime = 0;
    private float speed = 50f;
    private Vector2 direction = new Vector2();
    private float HP = 400;

    public Elder(float x, float y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(GameAssetManager.getElderTexture());
        this.sprite.setSize(210, 210);
        this.sprite.setPosition(x, y);
        this.rect = new CollisionRect(x, y, 210, 210);
    }
    public void update(float delta, float playerX, float playerY) {
        direction.set(playerX - x, playerY - y).nor();
        stateTime += delta;
        if(stateTime >= 5f){
            speed = 10000f;
        }
        // Update world position
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;
        if(speed >= 200f){
            speed = 50f;
            stateTime = 0;
        }

        // Update collision rect (world space)
        rect.move(x, y);
    }
    public void handleKnockBack(Bullet b) {
        Vector2 direction1 = new Vector2();
        direction1.set(b.getSprite().getX() - Gdx.graphics.getWidth()/2f, b.getSprite().getY() - Gdx.graphics.getHeight()/2f).nor();
        x += direction1.x * 5;
        y += direction1.y * 5;
    }

    public void draw(float offsetX, float offsetY) {
        sprite.setPosition(x - offsetX, y - offsetY);
        rect.move(sprite.getX(), sprite.getY());
        sprite.draw(Main.getBatch());
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getHP() { return HP; }
    public void setHP(float HP) { this.HP = HP; }
    public CollisionRect getRect() {
        return rect;
    }
}
