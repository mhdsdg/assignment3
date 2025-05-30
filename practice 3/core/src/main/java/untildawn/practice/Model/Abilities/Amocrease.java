package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Controller.GameControllers.WeaponController;
import untildawn.practice.Model.GameAssetManager;

public class Amocrease extends Ability {

    public WeaponController controller;
    public Amocrease(WeaponController controller) {
        name = "Amocrease";
        texture = GameAssetManager.getAmocreaseTexture();
        this.controller = controller;
    }
    public Amocrease(WeaponController controller, boolean doEffect) {
        name = "Amocrease";
        texture = GameAssetManager.getAmocreaseTexture();
        this.controller = controller;
        doEffect();
    }

    @Override
    public void doEffect() {
        controller.weapon.setMagSize(controller.weapon.getMagSize() + 5);
    }
}
