package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Controller.GameControllers.PlayerController;
import untildawn.practice.Model.GameAssetManager;

public class Speedy extends Ability {

    PlayerController controller;
    public Speedy(PlayerController controller) {
        name = "Speedy";
        texture = GameAssetManager.getSpeedyTexture();
        this.controller = controller;
    }
    public Speedy(PlayerController controller, boolean doEffect) {
        name = "Speedy";
        texture = GameAssetManager.getSpeedyTexture();
        this.controller = controller;
        doEffect();
    }

    @Override
    public void doEffect() {
        controller.setSpeedIsBoosted(true);
        controller.getPlayer().setSpeed(controller.getPlayer().getSpeed() * 2);
    }
}
