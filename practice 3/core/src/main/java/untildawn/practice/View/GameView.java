package untildawn.practice.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.GameControllers.GameController;
import untildawn.practice.Main;
import untildawn.practice.Model.Abilities.Damager;
import untildawn.practice.Model.Abilities.Procrease;
import untildawn.practice.Model.Abilities.Speedy;
import untildawn.practice.Model.Abilities.Vitality;
import untildawn.practice.Model.Player;

import java.util.Collections;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private final Skin skin;
    private PauseMenu pauseMenu;
    private boolean isPaused = false;
    private EndGameScreen endGameScreen;
    private boolean gameEnded = false;
    private boolean gaveUp = false;
    private float deathTimer;

    public GameView(GameController controller , Skin skin) {
        this.controller = controller;
        this.skin = skin;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        pauseMenu = new PauseMenu(stage, skin, this);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (gameEnded) {
            checkEndGame();
            endGameScreen.render(Main.getBatch());
            return;
        }
        if (!isPaused) {
            Main.getBatch().begin();
            controller.updateGame();
            Main.getBatch().end();
            checkEndGame();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void checkEndGame() {
        Player player = controller.getPlayerController().getPlayer();

        // Check for death
        if (player.getHP() <= 0) {
            if(deathTimer >= 5) {
                showEndScreen(false);
                return;
            }
            else{
                deathTimer += Gdx.graphics.getDeltaTime();
            }
        }
        if (gaveUp){
            showEndScreen(false);
            return;
        }

        // Check for win
        if (controller.getTotalTime() >= controller.getEndTime()) {
            showEndScreen(true);
        }
    }

    private void showEndScreen(boolean isWin) {
        gameEnded = true;
        endGameScreen = new EndGameScreen(
            isWin,
            controller.getPlayerController().getPlayer().getUsername(), // Replace with actual username
            controller.getTotalTime(),
            controller.getPlayerController().getPlayer().getKillCount()
        );
        Gdx.input.setInputProcessor(endGameScreen.getStage());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    boolean shiftPressed ;
    boolean controlPressed ;
    boolean upPressed ;
    boolean downPressed ;
    boolean leftPressed ;
    boolean rightPressed ;

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.ESCAPE) {
            setPaused(!isPaused);  // THIS is the fix
            return true;
        }
        switch (keycode) {
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                shiftPressed = true;
                break;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                controlPressed = true;
                break;
            case Input.Keys.UP:
                upPressed = true;
                break;
            case Input.Keys.DOWN:
                downPressed = true;
                break;
            case Input.Keys.RIGHT:
                rightPressed = true;
                break;
        }
        checkCombination();
        return false;
    }

    private void checkCombination() {
        if(shiftPressed && upPressed){
            shiftPressed = false;
            upPressed = false;
            if(deathTimer > 0) {
                controller.getPlayerController().getPlayer().setHP(controller.getPlayerController().getPlayer().getHP()+2);
            }
        }
        if(controlPressed && upPressed){
            controlPressed = false;
            upPressed = false;
            controller.setEndTime(controller.getEndTime() - 60);
        }
        if(controlPressed && downPressed){
            controlPressed = false;
            downPressed = false;
            controller.getPlayerController().getPlayer().getAbilities().add(new Vitality(controller.getPlayerController().getPlayer()));
            controller.getPlayerController().getPlayer().getAbilities().add(new Damager(controller.getWeaponController()));
            controller.getPlayerController().getPlayer().getAbilities().add(new Speedy(controller.getPlayerController()));
            controller.getPlayerController().getPlayer().getAbilities().add(new Procrease(controller.getWeaponController()));
        }
        if(shiftPressed && downPressed){
            shiftPressed = false;
            downPressed = false;
            controller.getPlayerController().getPlayer().setLevel(controller.getPlayerController().getPlayer().getLevel()+1);
        }
        if(shiftPressed && rightPressed){
            controlPressed = false;
            upPressed = false;
            shiftPressed = false;
            controller.setTotalTime(controller.getEndTime()/2);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public Skin getSkin() {
        return skin;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            Gdx.input.setInputProcessor(stage);
            System.out.println("InputProcessor set to stage: " + (Gdx.input.getInputProcessor() == stage));
            pauseMenu.show();
        } else {
            Gdx.input.setInputProcessor(this);
            pauseMenu.hide();
        }
    }

    public boolean isGaveUp() {
        return gaveUp;
    }

    public void setGaveUp(boolean gaveUp) {
        this.gaveUp = gaveUp;
    }

    public void setGameEnded(boolean b) {
        this.gameEnded = b;
    }
}
