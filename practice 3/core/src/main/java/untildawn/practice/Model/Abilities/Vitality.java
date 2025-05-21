package untildawn.practice.Model.Abilities;

import untildawn.practice.Model.Player;

public class Vitality extends Ability {
    public Player player ;
    public Vitality(Player player) {
        this.player = player;
        increaseHealth();
    }

    private void increaseHealth() {
        player.setMaxHP(player.getMaxHP() + 1);
        player.setHP(player.getHP() + 1);
    }
}
