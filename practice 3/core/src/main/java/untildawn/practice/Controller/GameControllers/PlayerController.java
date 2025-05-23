package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import untildawn.practice.Main;
import untildawn.practice.Model.Abilities.Speedy;
import untildawn.practice.Model.Enum.ControlKeys;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Monsters.EyeBat;
import untildawn.practice.Model.Monsters.Tentacle;
import untildawn.practice.Model.Monsters.Tree;
import untildawn.practice.Model.Player;
import untildawn.practice.Model.XP;

public class PlayerController {
    Player player;
    MonsterController monsterController;
    WorldController worldController;

    private float speedBoostTimer;
    private boolean speedIsBoosted;

    public PlayerController(Player player) {
        this.player = player;
    }
    float timeSinceLastHit ;

    public void update() {
        if(player.isWalking()){
            walkAnimation();
        } else if(player.isIdle()){
            idleAnimation();
        }
        timeSinceLastHit += Gdx.graphics.getDeltaTime();
        if(timeSinceLastHit >= 2.0f){
            checkHit();
        }
        if(speedIsBoosted){
            speedBoostTimer += Gdx.graphics.getDeltaTime();
            if(speedBoostTimer >= 10.0f){
                speedIsBoosted = false;
                speedBoostTimer = 0.0f;
                player.setSpeed(player.getSpeed()/2);
                for (int i = 0; i < player.getAbilities().size(); i++) {
                    if(player.getAbilities().get(i) instanceof Speedy ){
                        player.getAbilities().remove(player.getAbilities().get(i));
                    }
                }
            }
        }
        checkXP();

        player.getSprite().draw(Main.getBatch());

        handlePlayerInput();
    }

    private void checkXP() {
        for (int i = 0; i < worldController.getXps().size(); i++) {
            XP xp = worldController.getXps().get(i);
            if(xp.getRect().collidesWith(player.getRect())){
                player.addXP(3);
                worldController.getXps().remove(xp);
            }
        }
    }

    private void checkHit() {
        for (Tree tree : monsterController.getTrees()) {
            if(tree.getRect().collidesWith(player.getRect())){
                isHit();
                return;
            }
        }
        for (Tentacle tentacle : monsterController.getTentacles()) {
            if(tentacle.getRect().collidesWith(player.getRect())){
                isHit();
                return;
            }
        }
        for (EyeBat eyeBat : monsterController.getEyeBats()) {
            if(eyeBat.getRect().collidesWith(player.getRect())){
                isHit();
                return;
            }
        }
        if(monsterController.getElder() != null) {
            if(monsterController.getElder().getRect().collidesWith(player.getRect())){
                isHit();
                return;
            }
        }
    }

    private void isHit(){
        player.setHP(player.getHP() - 1);
        timeSinceLastHit = 0;
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
    public void setPlayer(Player player) {
        this.player = player;
    }
    public float getSpeedBoostTimer(){
        return speedBoostTimer;
    }
    public void setSpeedBoostTimer(float speedBoostTimer){
        this.speedBoostTimer = speedBoostTimer;
    }
    public boolean isSpeedBoosted(){
        return speedIsBoosted;
    }
    public void setSpeedIsBoosted(boolean speedIsBoosted){
        this.speedIsBoosted = speedIsBoosted;
    }
}
