package untildawn.practice.Model.Abilities;

import untildawn.practice.Controller.GameControllers.WeaponController;

public class Procrease extends Ability {
    public WeaponController controller;
    public Procrease(WeaponController controller) {
        this.controller = controller;
        doEffect();
    }

    private void doEffect() {
        controller.weapon.setProjectile(controller.weapon.getProjectile() + 1);
    }
}
