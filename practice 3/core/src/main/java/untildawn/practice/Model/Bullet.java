package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
     Weapon weapon ;
    final Texture texture = GameAssetManager.getBulletTexture();
    final Sprite sprite = new Sprite(texture);
    int damage;int x;
    int y;
    CollisionRect rect;

    public Bullet(Weapon weapon,int x,int y) {
        sprite.setSize(20, 20);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        rect = new CollisionRect(x,y, 5, 5);
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.damage = weapon.getDamage();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }
}
