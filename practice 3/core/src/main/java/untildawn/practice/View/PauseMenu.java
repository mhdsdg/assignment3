package untildawn.practice.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;

public class PauseMenu {
    private Table table;
    private boolean isVisible = false;
    private GameView gameView;
    TextButton resumeButton;

    public PauseMenu(Stage stage, Skin skin, GameView gameView) {
        this.gameView = gameView;
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.setVisible(false);

        // Title
        Label title = new Label("Game Paused", skin, "title");
        table.add(title).padBottom(30f).row();

        // Resume Button
        resumeButton = new TextButton("Resume", skin, "default");
        resumeButton.setChecked(false);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameView.setPaused(false);
            }
        });
        table.add(resumeButton).width(200f).height(60f).padBottom(15f).row();

        // Options Button
        TextButton optionsButton = new TextButton("Options", skin);
        table.add(optionsButton).width(200f).height(60f).padBottom(15f).row();

        // Exit Button
        TextButton exitButton = new TextButton("Exit to Menu", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle exit to main menu
            }
        });
        table.add(exitButton).width(200f).height(60f);
        TextButton giveUpButton = new TextButton("Give Up", skin);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameView.setPaused(false);
                gameView.setGaveUp(true);
                gameView.setGameEnded(true);
            }
        });
        table.add(giveUpButton).width(200f).height(60f);

        stage.addActor(table);
    }

    public void show() {
        isVisible = true;
        table.setVisible(true);
        table.setTouchable(Touchable.enabled);
        table.getStage().setKeyboardFocus(table);
        table.getStage().setScrollFocus(table);
    }

    public void hide() {
        isVisible = false;
        table.setVisible(false);
        table.getStage().unfocusAll();
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void toggle() {
        if(isVisible) hide();
        else show();
    }
}
