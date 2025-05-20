package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private final Weapon weapon ;
    private final Texture texture = GameAssetManager.getBulletTexture();
    private final Sprite sprite = new Sprite(texture);
    private final int damage;
    private int x;
    private int y;

    public Bullet(Weapon weapon,int x,int y) {
        sprite.setSize(20 , 20);
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.damage = weapon.getDetails().damage;
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
}
