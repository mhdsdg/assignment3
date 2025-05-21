package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
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
        if(player.isWalking()){
            walkAnimation();
        } else if(player.isIdle()){
            idleAnimation();
        }

        player.getSprite().draw(Main.getBatch());

        handlePlayerInput();
    }


    private void handlePlayerInput() {
        boolean isWalking = false;

        if (Gdx.input.isKeyPressed(ControlKeys.GO_UP.getKeyCode())) {
            player.setY(player.getY() - player.getSpeed());
            isWalking = true;
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_RIGHT.getKeyCode())) {
            player.setX(player.getX() - player.getSpeed());
            player.setFacingRight(true);
            isWalking = true;
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_DOWN.getKeyCode())) {
            player.setY(player.getY() + player.getSpeed());
            isWalking = true;
        }
        if (Gdx.input.isKeyPressed(ControlKeys.GO_LEFT.getKeyCode())) {
            player.setX(player.getX() + player.getSpeed());
            player.setFacingRight(false);
            isWalking = true;
        }

        player.setWalking(isWalking);
        player.setIdle(!isWalking);
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
        TextureRegion frame = animation.getKeyFrame(player.getAnimationTime());
        if (player.isFacingRight() && frame.isFlipX()) {
            frame.flip(true, false);
        }
        else if (!player.isFacingRight() && !frame.isFlipX()) {
            frame.flip(true, false);
        }
        player.getSprite().setRegion(frame);
        if (!animation.isAnimationFinished(player.getAnimationTime())) {
            player.setAnimationTime(player.getAnimationTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setAnimationTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }


    public Player getPlayer() {
        return player;
    }
}
