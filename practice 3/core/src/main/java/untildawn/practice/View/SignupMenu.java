package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.SignupMenuController;
import untildawn.practice.Main;

public class SignupMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private final TextButton advanceButton;
    private final TextButton guestButton;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField securityQuestionField;
    private final TextField securityAnswerField;
    private final Label title;
    public Table table;
    private final SignupMenuController controller;

    public SignupMenu(SignupMenuController controller , Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.advanceButton = new TextButton("Signup", skin);
        advanceButton.setChecked(false);
        this.guestButton = new TextButton("Guest", skin);
        guestButton.setChecked(false);
        this.title = new Label("Signup Menu", skin);
        this.usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        this.passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter your password");
        this.securityQuestionField = new TextField("", skin);
        securityQuestionField.setMessageText("Enter your security question(optional)");
        this.securityAnswerField = new TextField("", skin);
        securityAnswerField.setMessageText("Enter your security answer(optional)");
        this.table = new Table();
        controller.setView(this);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.add(title);
        table.row().pad(15, 0 , 15 , 0);
        table.add(usernameField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(passwordField).width(600);
        table.row().pad(15, 0 , 10 , 0);
        table.add(securityQuestionField).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(securityAnswerField).width(600);
        table.row().pad(15, 0 , 10 , 0);
        table.add(advanceButton);
        table.row().pad(10, 0 , 10 , 0);
        table.add(guestButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.signup();
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
        stage.dispose();
    }

    public TextButton getAdvanceButton() {
        return advanceButton;
    }

    public Label getTitle() {
        return title;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SignupMenuController getController() {
        return controller;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getGuestButton() {
        return guestButton;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public TextField getSecurityAnswerField() {
        return securityAnswerField;
    }
}
