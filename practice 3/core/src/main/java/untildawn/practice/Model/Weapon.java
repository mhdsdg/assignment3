package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import untildawn.practice.Model.Enum.Weapons.Weapons;

public class Weapon {
    private final Texture texture;
    private Animation<TextureRegion> reloadAnimation;
    private Sprite sprite;
    private final Weapons details;
    private boolean reloading;
    private float reloadTimer;
    private int damage;
    private int projectile;

    private int ammoInMag;
    private int magSize;

    public Weapon(){
        this.details = GameAssetManager.getWeapon();
        this.texture = GameAssetManager.getWeaponTexture();
        this.sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(texture.getWidth()*3, texture.getHeight()*3);
        this.reloadAnimation = GameAssetManager.getWeaponReloadAnimation();
        this.ammoInMag = details.magSize;
        this.magSize = details.magSize;
        this.damage = details.damage;
        this.projectile = details.projectile;
    }

    public int getAmmoInMag() {
        return ammoInMag;
    }

    public void setAmmoInMag(int ammoInMag) {
        this.ammoInMag = ammoInMag;
    }

    public Texture getTexture() {
        return texture;
    }

    public Animation<TextureRegion> getReloadAnimation() {
        return reloadAnimation;
    }

    public void setReloadAnimation(Animation reloadAnimation) {
        this.reloadAnimation = reloadAnimation;
    }

    public Weapons getDetails() {
        return details;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public int getMagSize() {
        return magSize;
    }

    public int reloadDuration() {
        return details.reloadTime;
    }

    public float getReloadTimer() {
        return reloadTimer;
    }

    public void setReloadTimer(float reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public void setMagSize(int magSize) {
        this.magSize = magSize;
    }
}
