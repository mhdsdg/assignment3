package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet extends Bullet {
    public EnemyBullet(Weapon weapon, int x, int y) {
        super(weapon,x,y);
        sprite.setSize(20, 20);
        sprite.setX((float) x);
        sprite.setY((float) y);
        rect = new CollisionRect(x,y, 5, 5);
        this.damage = weapon.getDamage();
    }
}
