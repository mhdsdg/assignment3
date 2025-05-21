package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import untildawn.practice.Model.Enum.Heros.Hero;
import untildawn.practice.Model.Enum.Weapons.Weapons;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static GameAssetManager manager;
    private static Texture Background = new Texture(Gdx.files.internal("Map/background.png"));

    private static Hero hero = Hero.Shana;
    private static Texture characterSheet = new Texture(Gdx.files.internal(hero.spriteSheet));
    private static Texture characterTexture ;
    private static Animation<TextureRegion> idleAnimation = makeHeroIdleAnimation(characterSheet);
    private static Animation<TextureRegion> walkAnimation = makeHeroWalkAnimation(characterSheet);

    private static TextureRegion heartFull;
    private static TextureRegion heartEmpty;
    private static Animation<TextureRegion> heartLossAnimation = makeHeartLossAnimation();

    private static Weapons weapon = Weapons.Revolver;
    private static Texture weaponTexture = new Texture(weapon.stillAddress);
    private static Animation<TextureRegion> weaponReloadAnimation = makeWeaponReloadAnimation();

    private static TextureRegion TreeTexture ;
    private static Animation<TextureRegion> TreeAnimation = makeTreeAnimation();

    private static TextureRegion TentacleTexture ;
    private static Animation<TextureRegion> TentacleAnimation = makeTentacleAnimation();

    private static TextureRegion eyeBatTexture ;
    private static Animation<TextureRegion> eyeBatAnimation = makeEyeBatAnimation();

    private static Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));

    private final Skin skin = new Skin(Gdx.files.internal("skin/quantumSkinRed.json"));

    public static GameAssetManager getManager() {
        if(manager == null) {
             manager = new GameAssetManager();
        }
        return manager;
    }

    private static Animation<TextureRegion> makeHeartLossAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(new Texture("T_HeartAnimation.png"), 32,32);
        TextureRegion[] frames = new TextureRegion[4];
        for(int i = 0; i < 4; i++){
            frames[i] = tmp[0][i];
        }
        heartFull = frames[0];
        heartEmpty = frames[3];
        return new Animation<>(0.2f, frames);
    }

    private static Animation<TextureRegion> makeTreeAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(new Texture("Monsters/TreeSpriteSheet.png"),96,96);
        TextureRegion[] frames = new TextureRegion[3];
        for(int i = 0 ; i < 3 ; i++) {
            frames[i] = tmp[0][i];
        }
        TreeTexture = frames[0];
        return new Animation<>(0.5f, frames);
    }
    private static Animation<TextureRegion> makeTentacleAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(new Texture("Monsters/BrainMonster.png"),64,64);
        TextureRegion[] frames = new TextureRegion[4];
        for(int i = 0 ; i < 4 ; i++) {
            frames[i] = tmp[0][i];
        }
        TentacleTexture = frames[0];
        return new Animation<>(0.2f, frames);
    }
    private static Animation<TextureRegion> makeEyeBatAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(new Texture("Monsters/T_EyeBat.png"),96,96);
        TextureRegion[] frames = new TextureRegion[4];
        for(int i = 0 ; i < 4 ; i++) {
            frames[i] = tmp[0][i];
        }
        eyeBatTexture = frames[0];
        return new Animation<>(0.2f, frames);
    }



    public static Weapons getWeapon() {
        return weapon;
    }

    public static void setWeapon(Weapons weapon) {
        GameAssetManager.weapon = weapon;
        weaponReloadAnimation = makeWeaponReloadAnimation();
        weaponTexture = new Texture(weapon.stillAddress);
    }

    private static Animation<TextureRegion> makeWeaponReloadAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(new Texture(weapon.animationAddress),32,32);
        TextureRegion[] frames = new TextureRegion[4];
        for(int i = 0 ; i < 4 ; i++) {
            frames[i] = tmp[0][i];
        }
        return new Animation<>(0.1f, frames);
    }


    public static Animation<TextureRegion> getWeaponReloadAnimation() {
        return weaponReloadAnimation;
    }

    public static void setWeaponReloadAnimation(Animation<TextureRegion> weaponReloadAnimation) {
        GameAssetManager.weaponReloadAnimation = weaponReloadAnimation;
    }

    public static Texture getWeaponTexture() {
        return weaponTexture;
    }

    public static void setWeaponTexture(Texture weaponTexture) {
        GameAssetManager.weaponTexture = weaponTexture;
    }

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero hero) {
        GameAssetManager.hero = hero;
        characterSheet = new Texture(Gdx.files.internal(hero.spriteSheet));
        idleAnimation = makeHeroIdleAnimation(characterSheet);
        walkAnimation = makeHeroWalkAnimation(characterSheet);
    }

    private static Animation<TextureRegion> makeHeroWalkAnimation(Texture characterSheet) {
        TextureRegion[][] tmp = TextureRegion.split(characterSheet,32,32);
        TextureRegion[] frames = new TextureRegion[4];
        for(int i = 0 ; i < 4 ; i++) {
            frames[i] = tmp[1][i];
        }
        return new Animation<>(0.15f, frames);
    }

    private static Animation<TextureRegion> makeHeroIdleAnimation(Texture characterSheet) {
        TextureRegion[][] tmp = TextureRegion.split(characterSheet,32,32);
        characterTexture = tmp[0][0].getTexture();
        TextureRegion[] frames = new TextureRegion[6];
        for(int i = 0 ; i < 6 ; i++) {
            frames[i] = tmp[0][i];
        }
        return new Animation<>(0.15f, frames);
    }

    public static Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public static void setIdleAnimation(Animation idleAnimation) {
        GameAssetManager.idleAnimation = idleAnimation;
    }

    public static Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public static void setWalkAnimation(Animation walkAnimation) {
        GameAssetManager.walkAnimation = walkAnimation;
    }

    public static Texture getBackground() {
        return Background;
    }

    public static void setBackground(Texture background) {
        Background = background;
    }

    public static Texture getBulletTexture() {
        return bulletTexture;
    }

    public static void setBulletTexture(Texture bulletTexture) {
        GameAssetManager.bulletTexture = bulletTexture;
    }

    public static Texture getCharacterTexture() {
        return characterTexture;
    }

    public static void setCharacterTexture(Texture characterTexture) {
        GameAssetManager.characterTexture = characterTexture;
    }

    public static TextureRegion getTreeTexture() {
        return TreeTexture;
    }

    public static void setTreeTexture(TextureRegion treeTexture) {
        TreeTexture = treeTexture;
    }

    public static Animation<TextureRegion> getTreeAnimation() {
        return TreeAnimation;
    }

    public static void setTreeAnimation(Animation<TextureRegion> treeAnimation) {
        TreeAnimation = treeAnimation;
    }

    public static TextureRegion getTentacleTexture() {
        return TentacleTexture;
    }

    public static void setTentacleTexture(TextureRegion tentacleTexture) {
        TentacleTexture = tentacleTexture;
    }

    public static Animation<TextureRegion> getTentacleAnimation() {
        return TentacleAnimation;
    }

    public static TextureRegion getHeartFull() {
        return heartFull;
    }

    public static void setHeartFull(TextureRegion heartFull) {
        GameAssetManager.heartFull = heartFull;
    }

    public static TextureRegion getHeartEmpty() {
        return heartEmpty;
    }

    public static void setHeartEmpty(TextureRegion heartEmpty) {
        GameAssetManager.heartEmpty = heartEmpty;
    }

    public static Animation<TextureRegion> getHeartLossAnimation() {
        return heartLossAnimation;
    }

    public Skin getSkin() {
        return skin;
    }
}
