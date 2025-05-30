package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Model.App;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Player;
import untildawn.practice.Model.User;

public class EndGameScreen {
    private Texture background;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private boolean isWin;
    private String username;
    private float surviveTime;
    private int killCount;
    private int score;
    private Skin skin;

    public EndGameScreen(boolean isWin, String username, float surviveTime, int killCount) {
        this.isWin = isWin;
        this.username = username;
        this.surviveTime = surviveTime;
        this.killCount = killCount;
        this.score = (int)(surviveTime * killCount);
        App.getLoggedInUser().setScore(App.getLoggedInUser().getScore() + score);
        App.getLoggedInUser().setKills(App.getLoggedInUser().getKills() + killCount);
        App.getLoggedInUser().setSurvivalTime(App.getLoggedInUser().getSurvivalTime() + (int)surviveTime);

        this.background = isWin ?
            GameAssetManager.getWinScreenTexture() :
            GameAssetManager.getLoseScreenTexture();

        stage = new Stage(new ScreenViewport());
        this.skin = GameAssetManager.getManager().getSkin();
        font = skin.getFont("ChevyRay_-_Express");
        font.getData().setScale(2f);

        createUI();
    }

    private void createUI() {
        table = new Table();
        table.setFillParent(true);
        if(isWin){
            table.left();
            table.padTop(300).padLeft(100);
        }
        else{
            table.center();
            table.padTop(300);
        }

        // Result label
        Label resultLabel = new Label(isWin ? "VICTORY!" : "GAME OVER",
            new Label.LabelStyle(font, isWin ? Color.GOLD : Color.RED));
        table.add(resultLabel).padBottom(50f).row();

        // Stats
        table.add(new Label("Username: " + username, new Label.LabelStyle(font, Color.WHITE))).row();
        table.add(new Label("Time Survived: " + String.format("%.1f", surviveTime) + "s",
            new Label.LabelStyle(font, Color.WHITE))).row();
        table.add(new Label("Kills: " + killCount, new Label.LabelStyle(font, Color.WHITE))).row();
        table.add(new Label("Score: " + score, new Label.LabelStyle(font, Color.YELLOW))).row();

        App.getLoggedInUser().setScore(App.getLoggedInUser().getScore() + score);
        App.getLoggedInUser().setSurvivalTime((int)(App.getLoggedInUser().getSurvivalTime() + surviveTime));
        App.getLoggedInUser().setKills(App.getLoggedInUser().getKills() + killCount);

        stage.addActor(table);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
