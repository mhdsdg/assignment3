package untildawn.practice.Model.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.CollisionRect;
import untildawn.practice.Model.GameAssetManager;

public class Elder extends Enemy {
    private final Sprite sprite;
    private float stateTime = 0.0F;
    private float speed = 50.0F;
    private Vector2 direction = new Vector2();
    private float HP = 400.0F;

    public Elder(float x, float y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(GameAssetManager.getElderTexture());
        this.sprite.setSize(210.0F, 210.0F);
        this.sprite.setPosition(x, y);
        this.rect = new CollisionRect(x, y, 210.0F, 210.0F);
    }

    public void update(float delta, float playerX, float playerY) {
        this.direction.set(playerX - this.x, playerY - this.y).nor();
        this.stateTime += delta;
        if (this.stateTime >= 5.0F) {
            this.speed = 10000.0F;
        }

        this.x += this.direction.x * this.speed * delta;
        this.y += this.direction.y * this.speed * delta;
        if (this.speed >= 200.0F) {
            this.speed = 50.0F;
            this.stateTime = 0.0F;
        }

        this.rect.move(this.x, this.y);
    }

    public void handleKnockBack(Bullet b) {
        Vector2 direction1 = new Vector2();
        direction1.set(b.getSprite().getX() - (float)Gdx.graphics.getWidth() / 2.0F, b.getSprite().getY() - (float)Gdx.graphics.getHeight() / 2.0F).nor();
        this.x += direction1.x * 5.0F;
        this.y += direction1.y * 5.0F;
    }

    public void draw(float offsetX, float offsetY) {
        this.sprite.setPosition(this.x - offsetX, this.y - offsetY);
        this.rect.move(this.sprite.getX(), this.sprite.getY());
        this.sprite.draw(Main.getBatch());
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getHP() {
        return this.HP;
    }

    public void setHP(float HP) {
        this.HP = HP;
    }

    public CollisionRect getRect() {
        return this.rect;
    }
}
