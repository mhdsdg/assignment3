package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.CollisionRect;

public class Enemy {
    CollisionRect rect;
    float x, y;

    public CollisionRect getRect() {
        return rect;
    }

    public void handleKnockBack(Bullet b) {
        Vector2 direction1 = new Vector2();
        direction1.set(b.getSprite().getX() - Gdx.graphics.getWidth()/2f, b.getSprite().getY() - Gdx.graphics.getHeight()/2f).nor();
        x += direction1.x * 10;
        y += direction1.y * 10;
    }

}
