package untildawn.practice.Model.Abilities;

import untildawn.practice.Controller.GameControllers.PlayerController;

public class Speedy extends Ability {
    PlayerController controller;
    public Speedy(PlayerController controller) {
        this.controller = controller;
        doEffect();
    }

    private void doEffect() {
        controller.setSpeedIsBoosted(true);
        controller.getPlayer().setSpeed(controller.getPlayer().getSpeed() * 2);
    }
}
