package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import untildawn.practice.Model.Enum.Weapons.Weapons;

public class Weapon {
    private Texture texture;
    private Animation reloadAnimation;
    private Sprite sprite;
    private final Weapons details;

    private int ammoInMag;

    public Weapon(){
        this.texture = GameAssetManager.getWeaponTexture();
        this.sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(texture.getWidth()*2, texture.getHeight()*2);
        this.reloadAnimation = GameAssetManager.getWeaponReloadAnimation();
        this.details = GameAssetManager.getWeapon();
        this.ammoInMag = details.magSize;
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

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Animation getReloadAnimation() {
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
}
