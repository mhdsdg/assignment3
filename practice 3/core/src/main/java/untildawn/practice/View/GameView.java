package untildawn.practice.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.GameControllers.GameController;
import untildawn.practice.Main;
import untildawn.practice.Model.Player;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private final Skin skin;
    private PauseMenu pauseMenu;
    private boolean isPaused = false;
    private EndGameScreen endGameScreen;
    private boolean gameEnded = false;
    private boolean gaveUp = false;

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
            showEndScreen(false);
            return;
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            setPaused(!isPaused);  // THIS is the fix
            return true;
        }
        return false;
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
