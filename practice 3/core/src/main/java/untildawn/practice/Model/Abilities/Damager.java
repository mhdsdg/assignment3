package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Controller.GameControllers.WeaponController;
import untildawn.practice.Controller.GameControllers.WorldController;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Weapon;

import java.util.ArrayList;

public class Damager extends Ability {

    public WeaponController controller;
    public Damager(WeaponController controller) {
        name = "Damager";
        texture = GameAssetManager.getDamagerTexture();
        this.controller = controller;
    }
    public Damager(WeaponController controller, boolean doEffect) {
        name = "Damager";
        texture = GameAssetManager.getDamagerTexture();
        this.controller = controller;
        doEffect();
    }

    @Override
    public void doEffect() {
        controller.weaponIsBoosted = true;
        for (Weapon weapon : controller.weapons) {
            weapon.setDamage((int)(weapon.getDamage() * 1.25));
        }
    }
}
