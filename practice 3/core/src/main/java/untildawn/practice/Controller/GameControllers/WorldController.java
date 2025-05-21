package untildawn.practice.Controller.GameControllers;

import untildawn.practice.Main;
import untildawn.practice.Model.Player;
import untildawn.practice.Model.World;

public class WorldController {
    private PlayerController playerController;
    private World world;
    private float backgroundX = 0;
    private float backgroundY = 0;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.world = new World();
    }

    public void update() {
        backgroundX = playerController.getPlayer().getX();
        backgroundY = playerController.getPlayer().getY();
        Main.getBatch().draw(world.getBackgroundTexture(), backgroundX, backgroundY);
    }

    public float getBackgroundX() {
        return backgroundX;
    }

    public void setBackgroundX(float backgroundX) {
        this.backgroundX = backgroundX;
    }

    public float getBackgroundY() {
        return backgroundY;
    }

    public void setBackgroundY(float backgroundY) {
        this.backgroundY = backgroundY;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Player getPlayer() {
        return playerController.getPlayer();
    }
}
