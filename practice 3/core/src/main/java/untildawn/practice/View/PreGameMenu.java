package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.PreGameMenuController;
import untildawn.practice.Main;

public class PreGameMenu implements Screen {
    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final SelectBox selectHero;
    public Table table;
    private PreGameMenuController controller;

    public PreGameMenu(PreGameMenuController controller, Skin skin) {
        this.gameTitle = new Label("Pregame Menu", skin);
        this.selectHero = new SelectBox<>(skin);
        this.playButton = new TextButton("Play", skin);
        this.table = new Table();
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Array<String> hero = new Array<>();

        hero.add("hero1");
        hero.add("hero2");
        hero.add("hero3");

        selectHero.setItems(hero);

        table.setFillParent(true);
        table.center();
        table.row().pad(10, 0 , 10 , 0);
        table.add(gameTitle);
        table.row().pad(10, 0 , 10 , 0);
        table.add(selectHero);
        table.row().pad(10, 0 , 10 , 0);
        table.add(playButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleAdvanceButton();
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

    public Label getGameTitle() {
        return gameTitle;
    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public SelectBox getSelectHero() {
        return selectHero;
    }

    public PreGameMenuController getController() {
        return controller;
    }

    public void setController(PreGameMenuController controller) {
        this.controller = controller;
    }
}
