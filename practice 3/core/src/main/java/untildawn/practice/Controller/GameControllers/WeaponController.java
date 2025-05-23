package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.*;
import untildawn.practice.Model.Abilities.Ability;
import untildawn.practice.Model.Abilities.Damager;
import untildawn.practice.Model.Enum.Weapons.Weapons;
import untildawn.practice.Model.Monsters.*;

public class WeaponController {
    BulletController bulletController;
    public Player player;
    public Weapon[] weapons = new Weapon[3];
    public Weapon weapon ;
    public boolean weaponIsBoosted;
    public float weaponBoostTimer;
    public MonsterController monsterController;
    public WorldController worldController;
    private boolean autoFireEnabled = false;
    private float autoFireTimer = 0;
    private float autoFireRate = 0.2f;


    public WeaponController(BulletController bulletController) {
        weapons[0] = App.getWeapons().get(0);
        weapons[1] = App.getWeapons().get(1);
        weapons[2] = App.getWeapons().get(2);
        weapon = weapons[0];
        this.bulletController = bulletController;
    }

    public void toggleAutoFire() {
        autoFireEnabled = !autoFireEnabled;
        autoFireTimer = 0; // Reset timer when toggling
    }

    private Enemy findNearestEnemy(MonsterController monsterController) {
        float playerX = worldController.getPlayer().getX();
        float playerY = worldController.getPlayer().getY();

        Enemy nearestEnemy = null;
        float nearestDistance = Float.MAX_VALUE;

        float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
        float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;

        // Check trees
        for (Tree tree : monsterController.getTrees()) {
            float distance = Vector2.dst(playerX, playerY,
                tree.getX() + tree.getRect().getWidth()/2 + worldController.getPlayer().getX() ,
                tree.getY() + tree.getRect().getHeight()/2 + worldController.getPlayer().getY());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEnemy = tree;
            }
        }

        // Check tentacles
        for (Tentacle tentacle : monsterController.getTentacles()) {
            float distance = Vector2.dst(playerX, playerY,
                tentacle.getRect().getX() + tentacle.getRect().getWidth()/2 - offsetX,
                tentacle.getRect().getY() + tentacle.getRect().getHeight()/2 - offsetY);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEnemy = tentacle;
            }
        }

        // Check eye bats
        for (EyeBat bat : monsterController.getEyeBats()) {
            float distance = Vector2.dst(playerX, playerY,
                bat.getRect().getX() + bat.getRect().getWidth()/2 - offsetX,
                bat.getRect().getY() + bat.getRect().getHeight()/2 - offsetY);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEnemy = bat;
            }
        }

        // Check elder (if exists)
        if (monsterController.getElder() != null) {
            Elder elder = monsterController.getElder();
            float distance = Vector2.dst(playerX, playerY,
                elder.getRect().getX() + elder.getRect().getWidth()/2 - offsetX,
                elder.getRect().getY() + elder.getRect().getHeight()/2 - offsetY);
            if (distance < nearestDistance) {
                nearestEnemy = elder;
            }
        }

        return nearestEnemy;
    }

    public void update() {
        handleReload();
        handleSwapWeapon();

        if (weapon.isReloading()) {
            doReloadAnimation();
        }
        if(weaponIsBoosted){
            weaponBoostTimer += Gdx.graphics.getDeltaTime();
            if(weaponBoostTimer >= 10){
                weaponIsBoosted = false;
                for (Weapon weapon : weapons) {
                    weapon.setDamage(weapon.getDamage()*8/10);
                }
                weaponBoostTimer = 0;
                for (int i = 0; i < player.getAbilities().size(); i++) {
                    if(player.getAbilities().get(i) instanceof Damager){
                        player.getAbilities().remove(player.getAbilities().get(i));
                    }
                }
            }
        }
        if (autoFireEnabled) {
            autoFireTimer += Gdx.graphics.getDeltaTime();
            if (autoFireTimer >= autoFireRate) {
                autoFireTimer = 0;
                Enemy nearestEnemy = findNearestEnemy(monsterController);
                if (nearestEnemy != null) {
                    if(nearestEnemy instanceof Tree){
                        handleWeaponShoot((int) (((Tree) nearestEnemy).getX()+ nearestEnemy.getRect().getWidth() / 2f + worldController.getPlayer().getX()) ,
                            (int) (((Tree) nearestEnemy).getY()+ nearestEnemy.getRect().getHeight() / 2f + worldController.getPlayer().getY()));
                    }
                    else {
                        // Calculate target position (center of enemy's rect)
                        float enemyWorldX = nearestEnemy.getRect().getX() + nearestEnemy.getRect().getWidth() / 2f;
                        float enemyWorldY = nearestEnemy.getRect().getY() + nearestEnemy.getRect().getHeight() / 2f;
                        float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
                        float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;
                        float targetX = enemyWorldX - offsetX;
                        float targetY = enemyWorldY - offsetY;
                        handleWeaponShoot((int) targetX, (int) targetY);
                    }
                }
            }
        }

        weapon.getSprite().draw(Main.getBatch());
        updateBullets();
    }

    private void handleSwapWeapon() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            weapon = weapons[0];
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            weapon = weapons[1];
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            weapon = weapons[2];
        }
    }

    public void handleWeaponShoot(int x, int y) {
        if(weapon.isReloading()) return;
        if(weapon.getAmmoInMag() <= 0) return;

        int projectiles = weapon.getProjectile();
        float spreadAngle = 15f; // Degrees of spread between bullets
        float baseAngle = (float) Math.atan2(y - Gdx.graphics.getHeight()/2f,
            x - Gdx.graphics.getWidth()/2f);

        // Calculate offsets based on projectile count
        if (projectiles > 1) {
            float startAngle = baseAngle - (spreadAngle * (projectiles - 1) / 2) * MathUtils.degreesToRadians;

            for (int i = 0; i < projectiles; i++) {
                float angle = startAngle + i * spreadAngle * MathUtils.degreesToRadians;
                float offsetX = 35f * (float)Math.cos(angle);
                float offsetY = 35f * (float)Math.sin(angle);

                bulletController.bullets.add(new Bullet(weapon,
                    x + (int)offsetX,
                    y + (int)offsetY));
            }
        } else {
            // Single projectile
            bulletController.bullets.add(new Bullet(weapon, x, y));
        }

        weapon.setAmmoInMag(weapon.getAmmoInMag() - 1);
    }

    private void handleReload() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.R) && weapon.getAmmoInMag() < weapon.getMagSize()) {
            if (!weapon.isReloading() && weapon.getAmmoInMag() < weapon.getMagSize()) {
                weapon.setReloading(true);
                weapon.setReloadTimer(0);
            }
        }

        if (weapon.isReloading()) {
            weapon.setReloadTimer(weapon.getReloadTimer() + Gdx.graphics.getDeltaTime());

            if (weapon.getReloadTimer() >= weapon.reloadDuration()) {
                int neededAmmo = weapon.getMagSize() - weapon.getAmmoInMag();
                weapon.setAmmoInMag(weapon.getAmmoInMag() + neededAmmo);
                weapon.getSprite().setRegion(weapon.getTexture());
                weapon.setReloading(false);
            }
        }
    }

    private void doReloadAnimation() {
        Animation<TextureRegion> animation = weapon.getReloadAnimation();

        if (animation != null) {
            TextureRegion frame = animation.getKeyFrame(weapon.getReloadTimer(), true);
            // Update the sprite's region and maintain its current position and rotation
            weapon.getSprite().setRegion(frame);
            weapon.getSprite().setSize(frame.getRegionWidth()*1.5f, frame.getRegionHeight()*1.5f);
        }
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

            b.getSprite().setX(b.getSprite().getX() - direction.x * 6);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 6);
            b.getRect().move(b.getSprite().getX() - direction.x * 6,
                             b.getSprite().getY() + direction.y * 6);
        }
    }

    public boolean isAutoFireEnabled() {
        return autoFireEnabled;
    }

    public void setAutoFireEnabled(boolean autoFireEnabled) {
        this.autoFireEnabled = autoFireEnabled;
    }

    public float getAutoFireTimer() {
        return autoFireTimer;
    }

    public void setAutoFireTimer(float autoFireTimer) {
        this.autoFireTimer = autoFireTimer;
    }

    public float getAutoFireRate() {
        return autoFireRate;
    }

    public void setAutoFireRate(float autoFireRate) {
        this.autoFireRate = autoFireRate;
    }
}
