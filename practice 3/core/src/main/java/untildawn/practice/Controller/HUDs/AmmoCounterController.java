package untildawn.practice.Controller.HUDs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Model.Weapon;

public class AmmoCounterController {
    private BitmapFont ammoFont;
    private Weapon weapon;
    private Skin skin;
    private float padding = 10f;
    private float heartSize = 60f; // Should match HealthBarController

    public AmmoCounterController(Weapon weapon, Skin skin) {
        this.weapon = weapon;
        this.skin = skin;
        initFont();
    }

    private void initFont() {
        this.ammoFont = skin.getFont("ChevyRay_-_Express");
    }

    public void update(Weapon currentWeapon) {
        this.weapon = currentWeapon;
    }

    public void render(SpriteBatch batch) {
        // Position below hearts (using same padding/heartSize as HealthBarController)
        float x = padding;
        float y = Gdx.graphics.getHeight() - heartSize - padding - 30f; // Below hearts

        String ammoText = String.format("%3d", weapon.getAmmoInMag());
        ammoFont.draw(batch, ammoText, x, y);

        // Optional: Draw max ammo or separator
        ammoFont.draw(batch, " / " + weapon.getMagSize(), x + 60, y);
    }

    public void dispose() {
        ammoFont.dispose();
    }
}
