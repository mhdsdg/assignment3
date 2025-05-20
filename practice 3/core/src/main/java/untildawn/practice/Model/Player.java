package untildawn.practice.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture texture;
    private Sprite sprite;
    private float x = 0;
    private float y = 0;
    private float HP;
    private final float speed;
    private float time = 0;
    private CollisionRect rect ;
    private float animationTime = 0;
    private boolean facingRight = true;
    private float walkStateTime = 0;

    private boolean idle = true;
    private boolean walking = false;

    public Player(){
        texture = GameAssetManager.getCharacterTexture();
        sprite = new Sprite(texture);
        sprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(texture.getWidth()/5 , texture.getHeight()/5 );
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight()/2, texture.getWidth()/5 , texture.getHeight()/5);
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

    public float getHP() {
        return HP;
    }
    public void setHP(float HP) {
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

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void update(float deltaTime) {
        animationTime += deltaTime;

        // Update sprite position based on x,y coordinates
        sprite.setPosition(x, y);
        rect.move(x, y);

        // Update walking animation timer if walking
        if (walking) {
            walkStateTime += deltaTime;
        } else {
            walkStateTime = 0;
        }
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight(boolean facingRight) {
        if (this.facingRight != facingRight) {
            this.facingRight = facingRight;
            sprite.flip(true, false);
        }
    }

    public float getWalkStateTime() {
        return walkStateTime;
    }
}
