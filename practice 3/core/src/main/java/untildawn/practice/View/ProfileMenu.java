package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.ProfileMenuController;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import java.io.File;

public class ProfileMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Image currentAvatar;

    // Form elements
    private TextField newUsernameField;
    private TextButton changeUsernameButton;
    private TextField newPasswordField;
    private TextButton changePasswordButton;
    private SelectBox<String> avatarSelectBox;
    private TextButton deleteAccountButton;
    private TextButton backButton;
    private TextButton chooseImageButton;
    private Label dragDropLabel;
    private FileHandleDropTarget dropTarget;

    private ProfileMenuController controller;

    public ProfileMenu(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;

        // Initialize form elements
        newUsernameField = new TextField("", skin);
        newUsernameField.setMessageText("New username");

        changeUsernameButton = new TextButton("Change Username", skin);

        newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New password");
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        changePasswordButton = new TextButton("Change Password", skin);

        chooseImageButton = new TextButton("Choose Image", skin);
        dragDropLabel = new Label("Or drag & drop image here", skin);
        dragDropLabel.setAlignment(Align.center);

        // Avatar selection
        Array<String> avatarOptions = new Array<>();
        avatarOptions.addAll("1", "2", "3", "4");
        avatarSelectBox = new SelectBox<>(skin);
        avatarSelectBox.setItems(avatarOptions);

        deleteAccountButton = new TextButton("Delete Account", skin);
        backButton = new TextButton("Back", skin);

        currentAvatar = new Image(App.getLoggedInUser().getAvatar());

        mainTable = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable.setFillParent(true);
        mainTable.defaults().pad(10f).width(300).height(40);

        // Current profile section
        Table profileSection = new Table();
        profileSection.add(currentAvatar).size(150f).padBottom(20f).row();
        profileSection.add(new Label("Current: " + App.getLoggedInUser().getUsername(), skin)).row();

        // Form section
        Table formSection = new Table();
        formSection.add(new Label("Change Username", skin)).colspan(2).row();
        formSection.add(newUsernameField).width(300).colspan(2).row();
        formSection.add(changeUsernameButton).colspan(2).row();

        formSection.add(new Label("Change Password", skin)).colspan(2).padTop(20f).row();
        formSection.add(newPasswordField).width(300).colspan(2).row();
        formSection.add(changePasswordButton).colspan(2).row();

        formSection.add(new Label("Change Avatar", skin)).colspan(2).padTop(20f).row();
        formSection.add(avatarSelectBox).colspan(2).row();

        formSection.add(new Label("Upload Custom Avatar", skin)).colspan(2).padTop(100f).row();
        formSection.add(chooseImageButton).colspan(2).row();
        formSection.add(dragDropLabel).colspan(2).width(300).height(100).padTop(10f).row();

        // Danger zone section
        Table dangerSection = new Table();
        dangerSection.add(new Label("Account Actions", skin)).row();
        dangerSection.add(deleteAccountButton).padTop(10f).row();

        // Combine everything
        mainTable.add(profileSection).padRight(50f);
        mainTable.add(formSection).padRight(50f);
        mainTable.add(dangerSection);
        mainTable.row();
        mainTable.add(backButton).colspan(3).padTop(30f);

        stage.addActor(mainTable);

        // Set up button listeners
        setupButtonListeners();
        setupDragAndDrop();
    }

    private void setupDragAndDrop() {
        DragAndDrop dragAndDrop = new DragAndDrop();
        dropTarget = new FileHandleDropTarget(currentAvatar);

        dragAndDrop.addTarget(new Target(currentAvatar) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                FileHandle file = (FileHandle) payload.getObject();
                try {
                    Texture newTexture = new Texture(file);
                    currentAvatar.setDrawable(new TextureRegionDrawable(newTexture));
                    App.getLoggedInUser().setAvatar(newTexture);
                } catch (Exception e) {
                    Gdx.app.error("Image Upload", "Error loading dropped image", e);
                }
            }
        });
    }

    public TextButton getChooseImageButton() {
        return chooseImageButton;
    }

    private void setupButtonListeners() {
        changeUsernameButton.addListener(controller.getChangeUsernameListener());
        changePasswordButton.addListener(controller.getChangePasswordListener());
        deleteAccountButton.addListener(controller.getDeleteAccountListener());
        backButton.addListener(controller.getBackListener());
        chooseImageButton.addListener(controller.getChooseImageListener());
        avatarSelectBox.addListener(controller.getAvatarSelectListener());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1); // Dark gray background
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

    // Getters for controller access
    public TextField getNewUsernameField() {
        return newUsernameField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public SelectBox<String> getAvatarSelectBox() {
        return avatarSelectBox;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Image getCurrentAvatar() {
        return currentAvatar;
    }

    private static class FileHandleDropTarget extends Target {
        public FileHandleDropTarget(Actor actor) {
            super(actor);
        }

        @Override
        public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            return payload.getObject() instanceof FileHandle;
        }

        @Override
        public void drop(Source source, Payload payload, float x, float y, int pointer) {
            // Handled in the main drop target
        }
    }
}
