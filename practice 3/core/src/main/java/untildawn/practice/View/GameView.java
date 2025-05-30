package untildawn.practice.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.GameControllers.GameController;
import untildawn.practice.Main;
import untildawn.practice.Model.Abilities.*;
import untildawn.practice.Model.Player;
import untildawn.practice.Model.User;

import java.util.ArrayList;
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
    private SettingsMenu settingsMenu;
    private Texture shadowTexture;
    private Pixmap shadowPixmap;
    private int sightRadius = 300;
    private ChooseAbilityMenu abilityMenu;
    private boolean levelUpPending = false;
    private ArrayList<Ability> pendingAbilities;

    public GameView(GameController controller , Skin skin) {
        this.controller = controller;
        this.skin = skin;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        pauseMenu = new PauseMenu(stage, skin, this);
        settingsMenu = new SettingsMenu(stage, skin, this);
        abilityMenu = new ChooseAbilityMenu(stage, skin, this, controller.getPlayerController().getPlayer());
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));
        createShadowTexture();
    }

    private ArrayList<Ability> getRandomAbilities() {
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(new Amocrease(controller.getWeaponController()));
        abilities.add(new Damager(controller.getWeaponController()));
        abilities.add(new Procrease(controller.getWeaponController()));
        abilities.add(new Speedy(controller.getPlayerController()));
        abilities.add(new Vitality(controller.getPlayerController().getPlayer()));
        Collections.shuffle(abilities);
        ArrayList<Ability> randomAbilities = new ArrayList<>();
        randomAbilities.add(abilities.get(0));
        randomAbilities.add(abilities.get(1));
        randomAbilities.add(abilities.get(2));
        return abilities;
    }

    private void createShadowTexture() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        shadowPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        shadowPixmap.setColor(0, 0, 0, 0.7f); // Semi-transparent black
        shadowPixmap.fill();

        // Clear a circle in the center
        shadowPixmap.setBlending(Pixmap.Blending.None);
        shadowPixmap.setColor(0, 0, 0, 0); // Transparent
        shadowPixmap.fillCircle(width/2, height/2, sightRadius);

        shadowTexture = new Texture(shadowPixmap);
        shadowPixmap.dispose();
    }

    public void showSettings() {
        pauseMenu.hide();
        settingsMenu.show();
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
            // 1. Render the game world
            controller.updateGame();

            // 2. Draw the shadow texture
            Main.getBatch().setColor(1, 1, 1, 1);
            Main.getBatch().draw(shadowTexture, 0, 0);

            // 3. Render the HUD elements
            controller.updateHUDs();
            Player player = controller.getPlayerController().getPlayer();
            if (player.getPreviousLevel()<player.getLevel() && !abilityMenu.isVisible() && !levelUpPending) {
                levelUpPending = true;
                pendingAbilities = getRandomAbilities();
            }

            if (levelUpPending && !abilityMenu.isVisible()) {
                abilityMenu.show(pendingAbilities);
                levelUpPending = false;
            }

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
            controller.getPlayerController().getPlayer().getAbilities().add(new Vitality(controller.getPlayerController().getPlayer(), true));
            controller.getPlayerController().getPlayer().getAbilities().add(new Damager(controller.getWeaponController(),true));
            controller.getPlayerController().getPlayer().getAbilities().add(new Speedy(controller.getPlayerController(),true));
            controller.getPlayerController().getPlayer().getAbilities().add(new Procrease(controller.getWeaponController(),true));
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

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }
}
