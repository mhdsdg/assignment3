package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Controller.HUDs.AmmoCounterController;
import untildawn.practice.Controller.HUDs.HealthBarController;
import untildawn.practice.Controller.HUDs.KillCounterController;
import untildawn.practice.Controller.HUDs.TimeCounterController;
import untildawn.practice.Main;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.Player;
import untildawn.practice.View.GameView;

public class GameController {
    private GameView view ;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private HealthBarController healthBarController;
    private AmmoCounterController ammoCounterController;
    private TimeCounterController timeCounterController;
    private KillCounterController killCounterController;
    private Skin skin = GameAssetManager.getManager().getSkin();
    private float TotalTime = 0;
    private float EndTime = 360;

    public void setView(GameView view){
        this.view = view;
        playerController = new PlayerController(new Player());
        healthBarController = new HealthBarController(playerController.getPlayer());
        weaponController = new WeaponController(new BulletController());
        ammoCounterController = new AmmoCounterController(weaponController.weapon, skin);
        worldController = new WorldController(playerController);
        monsterController = new MonsterController(worldController, getWeaponController().bulletController);
        playerController.monsterController = monsterController;
        playerController.worldController = worldController;
        timeCounterController = new TimeCounterController(skin, EndTime);
        killCounterController = new KillCounterController(skin);
        setCustomCursor();
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update();
            healthBarController.update(Gdx.graphics.getDeltaTime());
            ammoCounterController.update(weaponController.weapon);
            timeCounterController.update(TotalTime);
            killCounterController.update(playerController.player.getKillCount());
            killCounterController.render(Main.getBatch());
            timeCounterController.render(Main.getBatch());
            healthBarController.render(Main.getBatch());
            ammoCounterController.render(Main.getBatch());
        }
        TotalTime += Gdx.graphics.getDeltaTime();
    }

    public WorldController getWorldController() {
        return worldController;
    }
    public PlayerController getPlayerController() {
        return playerController;
    }
    public WeaponController getWeaponController(){
        return weaponController;
    }

    public void setCustomCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("T_Cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }
}
