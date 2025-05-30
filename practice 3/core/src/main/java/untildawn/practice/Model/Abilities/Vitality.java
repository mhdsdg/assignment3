package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Player;

public class Vitality extends Ability {

    public Player player ;
    public Vitality(Player player) {
        name = "Vitality";
        texture = GameAssetManager.getVitalityTexture();
        this.player = player;
    }
    public Vitality(Player player, boolean doEffect) {
        name = "Vitality";
        texture = GameAssetManager.getVitalityTexture();
        this.player = player;
        doEffect();
    }

    @Override
    public void doEffect() {
        player.setMaxHP(player.getMaxHP() + 1);
        player.setHP(player.getHP() + 1);
    }
}
