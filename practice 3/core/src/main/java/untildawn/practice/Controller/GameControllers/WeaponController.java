package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.*;

public class WeaponController {
    BulletController bulletController;
    Weapon weapon ;

    public WeaponController(BulletController bulletController) {
        weapon = new Weapon();
        this.bulletController = bulletController;
    }

    public void update() {
        weapon.getSprite().draw(Main.getBatch());
        updateBullets();
    }

    public void handleWeaponShoot(int x, int y) {
        bulletController.bullets.add(new Bullet(weapon, x, y));
        weapon.setAmmoInMag(weapon.getAmmoInMag() - 1);
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();
        weaponSprite.setOrigin(weaponSprite.getWidth()/2, weaponSprite.getHeight()/2);

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2 ;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2 ;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void updateBullets() {
        for(Bullet b : bulletController.bullets) {
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth()/2f - b.getX(),
                Gdx.graphics.getHeight()/2f - b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }
}
