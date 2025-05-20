package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import untildawn.practice.Main;
import untildawn.practice.Model.Enum.ControlKeys;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Player;

public class PlayerController {
    Player player;
    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        player.getSprite().draw(Main.getBatch());

        if(player.isIdle()){
            idleAnimation();
        }
        if(player.isWalking()){
            walkAnimation();
        }

        handlePlayerInput();
    }

    private void handlePlayerInput() {
        if (Gdx.input.isKeyPressed(ControlKeys.GO_UP.getKeyCode())){
            player.setY(player.getY() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_RIGHT.getKeyCode())){
            player.setX(player.getX() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_DOWN.getKeyCode())){
            player.setY(player.getY() + player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_LEFT.getKeyCode())){
            player.setX(player.getX() + player.getSpeed());
            player.getSprite().flip(true, false);
        }

    }

    private void idleAnimation() {
        Animation animation = GameAssetManager.getIdleAnimation();

        doAnimation(animation);
    }

    private void walkAnimation() {
        Animation animation = GameAssetManager.getWalkAnimation();
        doAnimation(animation);
    }

    private void doAnimation(Animation<TextureRegion> animation) {
        player.getSprite().setRegion(animation.getKeyFrame(player.getTime()));
        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }
}
