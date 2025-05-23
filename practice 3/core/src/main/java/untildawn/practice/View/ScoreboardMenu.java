package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import untildawn.practice.Controller.ScoreboardMenuController;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import untildawn.practice.Model.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table scoreTable;
    private SelectBox<String> sortSelectBox;
    private SelectBox<String> orderSelectBox;
    private Label titleLabel;
    private TextButton backButton;

    private ScoreboardMenuController controller;
    private User loggedInUser;

    public ScoreboardMenu(ScoreboardMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.loggedInUser = App.getLoggedInUser();

        // Initialize UI elements
        titleLabel = new Label("Scoreboard", skin, "title");

        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Score", "Kills", "Time Survived", "Alphabetical");

        orderSelectBox = new SelectBox<>(skin);
        orderSelectBox.setItems("Descending", "Ascending");
        this.backButton = new TextButton("Go Back", skin);

        mainTable = new Table();
        scoreTable = new Table();

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable.setFillParent(true);
        mainTable.defaults().pad(10f);

        // Header section
        Table headerTable = new Table();
        headerTable.add(titleLabel).colspan(2).padBottom(20f).row();
        headerTable.add(new Label("Sort by:", skin)).left();
        headerTable.add(sortSelectBox).width(200f).padRight(20f);
        headerTable.add(new Label("Order:", skin)).left();
        headerTable.add(orderSelectBox).width(150f).row();

        mainTable.add(headerTable).colspan(2).row();

        // Column headers
        Table columnHeaders = new Table();
        columnHeaders.add(new Label("Rank", skin)).width(80f);
        columnHeaders.add(new Label("Username", skin)).width(200f);
        columnHeaders.add(new Label("Score", skin)).width(100f);
        columnHeaders.add(new Label("Kills", skin)).width(100f);
        columnHeaders.add(new Label("Time", skin)).width(100f);

        mainTable.add(columnHeaders).colspan(2).row();

        // Score table (will be updated in refreshScoreboard)
        mainTable.add(scoreTable).colspan(2).expand().fill();
        Table bottomTable = new Table();
        bottomTable.add(backButton).width(200).height(50).padTop(20);

        mainTable.add(bottomTable).colspan(2).bottom().padBottom(80).padRight(200);

        stage.addActor(mainTable);

        // Set up listeners
        setupListeners();

        // Initial refresh
        refreshScoreboard();
    }

    private void setupListeners() {
        sortSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                refreshScoreboard();
            }
        });

        orderSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                refreshScoreboard();
            }
        });
        backButton.addListener(controller.getBackListener());
    }

    private void refreshScoreboard() {
        scoreTable.clear();

        String sortBy = sortSelectBox.getSelected();
        boolean descending = orderSelectBox.getSelected().equals("Descending");

        // Get and sort users
        List<User> users = App.getUsers().stream()
            .sorted(getComparator(sortBy, descending))
            .limit(10)
            .collect(Collectors.toList());

        // Display each user
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            // Create labels with white text
            Label rankLabel = new Label((i+1) + ".", skin);
            Label usernameLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            Label killsLabel = new Label(String.valueOf(user.getKills()), skin);
            Label timeLabel = new Label(formatTime(user.getSurvivalTime()), skin);

            // Set all text to white
            Color textColor = Color.WHITE;
            rankLabel.setColor(textColor);
            usernameLabel.setColor(textColor);
            scoreLabel.setColor(textColor);
            killsLabel.setColor(textColor);
            timeLabel.setColor(textColor);

            // Highlight current user with yellow text
            if (user.equals(loggedInUser)) {
                rankLabel.setColor(Color.YELLOW);
                usernameLabel.setColor(Color.YELLOW);
                scoreLabel.setColor(Color.YELLOW);
                killsLabel.setColor(Color.YELLOW);
                timeLabel.setColor(Color.YELLOW);
            }

            // Add to table
            scoreTable.add(rankLabel).width(80f).pad(5f);
            scoreTable.add(usernameLabel).width(200f).pad(5f);
            scoreTable.add(scoreLabel).width(100f).pad(5f);
            scoreTable.add(killsLabel).width(100f).pad(5f);
            scoreTable.add(timeLabel).width(100f).pad(5f);
            scoreTable.row();
        }
    }

    private Comparator<User> getComparator(String sortBy, boolean descending) {
        Comparator<User> comparator;

        switch (sortBy) {
            case "Score":
                comparator = Comparator.comparingInt(User::getScore);
                break;
            case "Kills":
                comparator = Comparator.comparingInt(User::getKills);
                break;
            case "Time Survived":
                comparator = Comparator.comparingInt(User::getSurvivalTime);
                break;
            case "Alphabetical":
                comparator = Comparator.comparing(User::getUsername);
                break;
            default:
                comparator = Comparator.comparingInt(User::getScore);
        }
        return descending ? comparator.reversed() : comparator;
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
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

    @Override
    public void dispose() {
        stage.dispose();
    }

    public TextButton getBackButton() {
        return backButton;
    }
}
