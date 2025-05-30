package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Controller.HUDs.*;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
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
    private XPLevelController xpLevelController;
    private Skin skin = GameAssetManager.getManager().getSkin();
    private float TotalTime = 0;
    private float EndTime = App.getGameDuration();

    public void setView(GameView view){
        this.view = view;
        playerController = new PlayerController(new Player());
        playerController.player.playerController = playerController;
        healthBarController = new HealthBarController(playerController.getPlayer());
        weaponController = new WeaponController(new BulletController());
        playerController.player.weaponController = weaponController;
        ammoCounterController = new AmmoCounterController(weaponController.weapon, skin);
        worldController = new WorldController(playerController);
        playerController.worldController = worldController;
        monsterController = new MonsterController(worldController, getWeaponController().bulletController);
        monsterController.setPlayerController(playerController);
        playerController.monsterController = monsterController;
        playerController.worldController = worldController;
        timeCounterController = new TimeCounterController(skin, EndTime);
        killCounterController = new KillCounterController(skin);
        xpLevelController = new XPLevelController(skin);
        weaponController.player =playerController.getPlayer();
        weaponController.monsterController = monsterController;
        weaponController.worldController = worldController;
        setCustomCursor();
    }

    public void updateGame() {
        if (view != null && !view.isPaused()) {
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update();
            if(!monsterController.elderHasSpawned && TotalTime > EndTime/2 && monsterController.getElder() == null){
                monsterController.spawnElder();
            }
        }
        TotalTime += Gdx.graphics.getDeltaTime();
    }
    public void updateHUDs() {
        healthBarController.update(Gdx.graphics.getDeltaTime());
        ammoCounterController.update(weaponController.weapon);
        timeCounterController.update(TotalTime, EndTime);
        killCounterController.update(playerController.player.getKillCount());
        killCounterController.render(Main.getBatch());
        timeCounterController.render(Main.getBatch());
        healthBarController.render(Main.getBatch());
        ammoCounterController.render(Main.getBatch());
        xpLevelController.render(Main.getBatch(), playerController.getPlayer());
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

    public float getTotalTime() {
        return TotalTime;
    }

    public float getEndTime() {
        return EndTime;
    }

    public void setEndTime( float endTime ) {
        this.EndTime = endTime;
    }

    public void setTotalTime( float totalTime ) {
        this.TotalTime = totalTime;
    }
}
