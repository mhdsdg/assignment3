package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.MainMenuController;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import untildawn.practice.Model.GameAssetManager;

public class MainMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table userInfoTable;
    private Image avatarImage;
    private SettingsMenu settingsMenu;

    // Buttons
    private TextButton settingsButton;
    private TextButton profileButton;
    private TextButton preGameButton;
    private TextButton scoreBoardButton;
    private TextButton hintMenuButton;
    private TextButton logoutButton;

    // User info labels
    private Label usernameLabel;
    private Label scoreLabel;

    private MainMenuController controller;

    public MainMenu(MainMenuController controller, Skin skin) {
        App.initializeMusic();
        this.controller = controller;
        this.skin = skin;

        // Initialize buttons
        settingsButton = new TextButton("Settings", skin);
        profileButton = new TextButton("Profile", skin);
        preGameButton = new TextButton("Start Game", skin);
        scoreBoardButton = new TextButton("Scoreboard", skin);
        hintMenuButton = new TextButton("Hints", skin);
        logoutButton = new TextButton("Logout", skin);

        avatarImage = new Image(App.getLoggedInUser().getAvatar());

        usernameLabel = new Label(App.getLoggedInUser().getUsername(), skin, "title");
        scoreLabel = new Label("Score: " + App.getLoggedInUser().getScore(), skin);

        // Create tables
        mainTable = new Table();
        userInfoTable = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        settingsMenu = new SettingsMenu(stage, skin, this);

        // Set up user info table (right side)
        userInfoTable.add(usernameLabel).row();
        userInfoTable.add(scoreLabel).padTop(10f).row();

        // Set up main menu table
        mainTable.setFillParent(true);

        // Left column - avatar
        mainTable.add(avatarImage)
            .size(150f)  // Avatar size
            .padLeft(300)
            .align(Align.left);

        // Center column - menu buttons
        Table menuTable = new Table();
        menuTable.add(preGameButton).width(300).padBottom(15f).row();
        menuTable.add(profileButton).width(300).padBottom(15f).row();
        menuTable.add(scoreBoardButton).width(300).padBottom(15f).row();
        menuTable.add(settingsButton).width(300).padBottom(15f).row();
        menuTable.add(hintMenuButton).width(300).padBottom(15f).row();
        menuTable.add(logoutButton).width(300);

        mainTable.add(menuTable).center().expandX();

        // Right column - user info
        mainTable.add(userInfoTable)
            .padRight(300)  // Space between menu and user info
            .align(Align.right);

        stage.addActor(mainTable);

        // Set up button listeners
        setupButtonListeners();
    }

    public void showSettings() {
        settingsMenu.show();
    }

    private void setupButtonListeners() {
        preGameButton.addListener(controller.getPreGameListener());
        profileButton.addListener(controller.getProfileListener());
        scoreBoardButton.addListener(controller.getScoreBoardListener());
        settingsButton.addListener(controller.getSettingsListener());
//        hintMenuButton.addListener(controller.getHintMenuListener());
        logoutButton.addListener(controller.getLogoutListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1); // Dark background
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    // ... Other required Screen methods (resize, pause, resume, hide, dispose) ...

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getPreGameButton() {
        return preGameButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getHintMenuButton() {
        return hintMenuButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
