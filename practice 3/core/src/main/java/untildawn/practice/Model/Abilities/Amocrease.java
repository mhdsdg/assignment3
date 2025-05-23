package untildawn.practice.Model.Abilities;

import untildawn.practice.Controller.GameControllers.WeaponController;

public class Amocrease extends Ability {
    public WeaponController controller;
    public Amocrease(WeaponController controller) {
        this.controller = controller;
        doEffect();
    }

    @Override
    public void doEffect() {
        controller.weapon.setMagSize(controller.weapon.getMagSize() + 5);
    }
}
