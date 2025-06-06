package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import untildawn.practice.Controller.GameControllers.PlayerController;
import untildawn.practice.Controller.GameControllers.WeaponController;
import untildawn.practice.Model.Abilities.*;

import java.util.ArrayList;

public class Player {
    public WeaponController weaponController;
    public PlayerController playerController;
    private Texture texture;
    private Sprite sprite;
    private float x = 0;
    private float y = 0;
    private int MaxHP;
    private int HP;
    private float speed;
    private float animationTime = 0;
    private CollisionRect rect ;
    private boolean facingRight = true;
    private int xp;
    private int previousLevel = 1;
    private int level = 1;
    private int killCount;
    private ArrayList<Ability> abilities = new ArrayList<>();


    private boolean idle = true;
    private boolean walking = false;

    public Player(){
        texture = GameAssetManager.getCharacterTexture();
        sprite = new Sprite(texture);
        sprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        sprite.setSize((float) texture.getWidth() /5 , (float) texture.getHeight() /5 );
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight()/2, (float) texture.getWidth() /5 , (float) texture.getHeight() /5);
        MaxHP = GameAssetManager.getHero().HP;
        HP = GameAssetManager.getHero().HP;
        speed = GameAssetManager.getHero().Speed;
        }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    public float getSpeed() {
        return speed;
    }

    public Sprite getSprite() {
        return sprite;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public CollisionRect getRect() {
        return rect;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public void setFacingRight(boolean facingRight) {
        if (this.facingRight != facingRight) {
            this.facingRight = facingRight;
            sprite.flip(true, false);
        }
    }
    public boolean isFacingRight() {
        return facingRight;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void setMaxHP(int MaxHP) {
        this.MaxHP = MaxHP;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpNeeded(){
        return level*20;
    }

    public int getThisLevelXp(){
        return xp ;
    }

    public void addXP(int i) {
        xp += i ;
        previousLevel = level;
        if(xp >= level*20){
            xp = xp - level*20;
            level++;
        }
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getUsername() {
        if(App.getLoggedInUser() != null){
            return App.getLoggedInUser().getUsername();
        }
        return "GUEST";
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public void setPreviousLevel(int previousLevel) {
        this.previousLevel = previousLevel;
    }
}
