package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Controller.GameControllers.WeaponController;
import untildawn.practice.Model.GameAssetManager;

public class Procrease extends Ability {

    public WeaponController controller;
    public Procrease(WeaponController controller) {
        name = "Procrease";
        texture = GameAssetManager.getProcreaseTexture();
        this.controller = controller;
    }
    public Procrease(WeaponController controller, boolean doEffect) {
        name = "Procrease";
        texture = GameAssetManager.getProcreaseTexture();
        this.controller = controller;
        doEffect();
    }

    @Override
    public void doEffect() {
        controller.weapon.setProjectile(controller.weapon.getProjectile() + 1);
    }
}
