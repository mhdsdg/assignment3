package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Model.Enum.Weapons.Weapons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager manager;
    private static Weapons weapon = Weapons.Revolver;
    private static Texture weaponTexture = new Texture(weapon.stillAddress);
    private static Animation<Texture> weaponReloadAnimation;

    private final Skin skin = new Skin(Gdx.files.internal("skin/quantumSkinRed.json"));

    public static GameAssetManager getManager() {
        if(manager == null) {
             manager = new GameAssetManager();
        }
        return manager;
    }

    public static Weapons getWeapon() {
        return weapon;
    }

    public static void setWeapon(Weapons weapon) {
        GameAssetManager.weapon = weapon;
        makeWeaponReloadAnimation();
        weaponTexture = new Texture(weapon.stillAddress);
    }

    private static void makeWeaponReloadAnimation() {
        FileHandle[] reloadFrames = Gdx.files.internal(weapon.animationAddress).list();
        TextureRegion[] reloadRegions = new TextureRegion[reloadFrames.length];
        for(int i = 0; i < reloadFrames.length; i++) {
            reloadRegions[i] = new TextureRegion(new Texture(reloadFrames[i]));
        }
        float timeBetweenFrames = ((float) weapon.reloadTime)/(reloadFrames.length-1);
        weaponReloadAnimation = new Animation(timeBetweenFrames, reloadRegions);
    }

    public static Animation<Texture> getWeaponReloadAnimation() {
        return weaponReloadAnimation;
    }

    public static void setWeaponReloadAnimation(Animation<Texture> weaponReloadAnimation) {
        GameAssetManager.weaponReloadAnimation = weaponReloadAnimation;
    }

    public static Texture getWeaponTexture() {
        return weaponTexture;
    }

    public static void setWeaponTexture(Texture weaponTexture) {
        GameAssetManager.weaponTexture = weaponTexture;
    }

    public Skin getSkin() {
        return skin;
    }
}
