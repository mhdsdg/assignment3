package untildawn.practice.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import untildawn.practice.Model.App;

import java.util.ArrayList;
import java.util.List;

public class SettingsMenu implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private Screen gameView;
    private Window window;
    private List<String> musicTracks;
    private boolean grayscaleEnabled = false;
    private ShaderProgram grayscaleShader;
    private SpriteBatch grayscaleBatch;

    public SettingsMenu(Stage stage, Skin skin, Screen gameView) {
        this.stage = stage;
        this.skin = skin;
        this.gameView = gameView;

        // Initialize music tracks
        musicTracks = new ArrayList<>();
        musicTracks.add("Track 1");
        musicTracks.add("Track 2");
        musicTracks.add("Track 3");

        // Initialize grayscale shader if needed
        initGrayscaleShader();

        createWindow();
    }

    private void initGrayscaleShader() {
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/grayscale.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", grayscaleShader.getLog());
        }
        grayscaleBatch = new SpriteBatch();
        grayscaleBatch.setShader(grayscaleShader);
    }

    private void createWindow() {
        window = new Window("Settings", skin);
        window.setModal(true);
        window.setMovable(false);

        table = new Table();
        table.pad(20);
        table.defaults().pad(10).fillX().expandX();

        // Music Volume Slider
        Label volumeLabel = new Label("Music Volume:", skin);
        final Slider volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSlider.setValue(App.getMusicVolume());

        // Music Track Select Box
        Label musicLabel = new Label("Music Track:", skin);
        final SelectBox<String> musicSelect = new SelectBox<>(skin);
        musicSelect.setItems(musicTracks.toArray(new String[0]));

        // Auto Reload Toggle
        Label autoReloadLabel = new Label("Auto Reload:", skin);
        final CheckBox autoReloadCheck = new CheckBox("", skin);
        autoReloadCheck.setChecked(App.isAutoReloadEnabled());

        // Sound Effects Toggle
        Label soundEffectsLabel = new Label("Sound Effects:", skin);
        final CheckBox soundEffectsCheck = new CheckBox("", skin);
        soundEffectsCheck.setChecked(App.isSoundEffectsEnabled());

        // Grayscale Toggle
        Label grayscaleLabel = new Label("Grayscale Mode:", skin);
        final CheckBox grayscaleCheck = new CheckBox("", skin);
        grayscaleCheck.setChecked(grayscaleEnabled);

        // Keybindings Button
        TextButton keybindingsButton = new TextButton("Change Keybindings", skin);

        // Back Button
        TextButton backButton = new TextButton("Back", skin);

        // Add elements to table
        table.add(volumeLabel).left();
        table.add(volumeSlider).right().row();
        table.add(musicLabel).left();
        table.add(musicSelect).right().row();
        table.add(autoReloadLabel).left();
        table.add(autoReloadCheck).right().row();
        table.add(soundEffectsLabel).left();
        table.add(soundEffectsCheck).right().row();
        table.add(grayscaleLabel).left();
        table.add(grayscaleCheck).right().row();
        table.add(keybindingsButton).colspan(2).fillX().row();
        table.add(backButton).colspan(2).fillX();

        window.add(table);
        window.pack();
        window.setPosition(
            Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
        );

        // Add listeners
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.updateMusicVolume(volumeSlider.getValue());
            }
        });

        musicSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int selectedTrack = musicSelect.getSelectedIndex();
                App.changeMusicTrack(selectedTrack);
            }
        });

        autoReloadCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.setAutoReloadEnabled(autoReloadCheck.isChecked());
            }
        });

        soundEffectsCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.setSoundEffectsEnabled(soundEffectsCheck.isChecked());
            }
        });

        grayscaleCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                grayscaleEnabled = grayscaleCheck.isChecked();
            }
        });

//        keybindingsButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                // Show keybindings menu
//                hide();
//                new KeybindingsMenu(stage, skin, gameView).show();
//            }
//        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
                if(gameView instanceof GameView) {
                    if (((GameView)gameView).isPaused()) {
                        ((GameView)gameView).getPauseMenu().show();
                    }
                }
            }
        });
    }

    public void show() {
        stage.addActor(window);
    }

    @Override
    public void render(float delta) {

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

    public void hide() {
        window.remove();
    }

    public void render(SpriteBatch batch) {
        if (grayscaleEnabled) {
            // Render with grayscale effect
            grayscaleBatch.begin();
            stage.draw();
            grayscaleBatch.end();
        } else {
            stage.draw();
        }
    }

    public void dispose() {
        if (grayscaleShader != null) {
            grayscaleShader.dispose();
        }
        if (grayscaleBatch != null) {
            grayscaleBatch.dispose();
        }
    }
}
