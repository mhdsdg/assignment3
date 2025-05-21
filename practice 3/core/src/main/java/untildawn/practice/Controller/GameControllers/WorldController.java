package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import untildawn.practice.Main;
import untildawn.practice.Model.Player;
import untildawn.practice.Model.World;
import untildawn.practice.Model.XP;

import java.util.ArrayList;

public class WorldController {
    private PlayerController playerController;
    private World world;
    private ArrayList<XP> xps = new ArrayList<>();
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
        for (XP xp : xps) {
            float offsetX = getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
            float offsetY = getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;
            xp.getSprite().setPosition(xp.getX() - offsetX, xp.getY() - offsetY);
            xp.getRect().move(xp.getSprite().getX(), xp.getSprite().getY());
            xp.getSprite().draw(Main.getBatch());
        }
    }

    public float getPlayerWorldX() {
        return -backgroundX + Gdx.graphics.getWidth() / 2f;
    }

    public float getPlayerWorldY() {
        return -backgroundY + Gdx.graphics.getHeight() / 2f;
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

    public ArrayList<XP> getXps() {
        return xps;
    }

    public void setXps(ArrayList<XP> xps) {
        this.xps = xps;
    }
}
