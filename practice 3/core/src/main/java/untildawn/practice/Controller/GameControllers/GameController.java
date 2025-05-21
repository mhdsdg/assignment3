package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import untildawn.practice.Controller.HUDs.HealthBarController;
import untildawn.practice.Main;
import untildawn.practice.Model.Player;
import untildawn.practice.View.GameView;

public class GameController {
    private GameView view ;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private HealthBarController healthBarController;
    private float TotalTime = 0;
    private float EndTime = 360;

    public void setView(GameView view){
        this.view = view;
        playerController = new PlayerController(new Player());
        healthBarController = new HealthBarController(playerController.getPlayer());
        weaponController = new WeaponController(new BulletController());
        worldController = new WorldController(playerController);
        monsterController = new MonsterController(worldController, getWeaponController().bulletController);
        playerController.monsterController = monsterController;
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update();
            healthBarController.update(Gdx.graphics.getDeltaTime());
            healthBarController.render(Main.getBatch());
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
}
