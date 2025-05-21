package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import untildawn.practice.Model.Player;
import untildawn.practice.View.GameView;

public class GameController {
    private GameView view ;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private float TotalTime = 0;

    public void setView(GameView view){
        this.view = view;
        playerController = new PlayerController(new Player());
        weaponController = new WeaponController(new BulletController());
        worldController = new WorldController(playerController);
        monsterController = new MonsterController(worldController, getWeaponController().bulletController);
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterController.update();
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
