package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture texture;
    private Sprite sprite;
    private float x = 0;
    private float y = 0;
    private int MaxHP;
    private int HP;
    private final float speed;
    private float animationTime = 0;
    private CollisionRect rect ;
    private boolean facingRight = true;


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
}
