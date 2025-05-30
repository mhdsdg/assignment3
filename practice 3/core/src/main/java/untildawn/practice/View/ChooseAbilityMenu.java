package untildawn.practice.View;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import untildawn.practice.Model.Abilities.Ability;
import untildawn.practice.Model.Player;

import java.util.List;

public class ChooseAbilityMenu {
    private Table table;
    private Stage stage;
    private Skin skin;
    private GameView gameView;
    private boolean isVisible = false;
    private Player player;

    public ChooseAbilityMenu(Stage stage, Skin skin, GameView gameView, Player player) {
        this.player = player;
        this.stage = stage;
        this.skin = skin;
        this.gameView = gameView;

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.setVisible(false);

        stage.addActor(table);
    }

    public void show(List<Ability> abilities) {
        if (abilities == null || abilities.size() < 3) {
            return; // Not enough abilities to show
        }

        table.clear();
        table.toFront();

        // Title
        Label title = new Label("Choose an Ability", skin, "title");
        table.add(title).colspan(3).padBottom(30f).row();

        // Create three ability cards
        for (int i = 0; i < 3; i++) {
            Ability ability = abilities.get(i);
            Table abilityCard = createAbilityCard(ability);
            table.add(abilityCard).pad(15f).width(200f).height(250f);
        }

        table.setVisible(true);
        isVisible = true;
        gameView.setPaused(true);
        stage.setKeyboardFocus(table);
        stage.setScrollFocus(table);
    }

    private Table createAbilityCard(Ability ability) {
        Table card = new Table();
        card.defaults().pad(5f);

        // Ability name
        Label nameLabel = new Label(ability.name, skin);
        nameLabel.setAlignment(Align.center);

        // Ability image
        Image abilityImage = new Image(new TextureRegionDrawable(ability.texture));
        abilityImage.setScaling(Scaling.fit);

        // Select button
        TextButton selectButton = new TextButton("Select", skin);
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ability.doEffect();
                player.getAbilities().add(ability);
                player.setPreviousLevel(player.getLevel());
                hide();
                gameView.setPaused(false);
            }
        });

        card.add(nameLabel).width(180f).height(30f).row();
        card.add(abilityImage).width(180f).height(180f).row();
        card.add(selectButton).width(180f).height(40f);

        return card;
    }

    public void hide() {
        table.clear();
        table.setVisible(false);
        isVisible = false;
        stage.unfocusAll();
    }

    public boolean isVisible() {
        return isVisible;
    }
}
