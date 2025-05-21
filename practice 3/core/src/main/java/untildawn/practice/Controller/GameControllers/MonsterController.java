package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Main;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.Monsters.Tentacle;
import untildawn.practice.Model.Monsters.Tree;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private WorldController worldController;
    private BulletController bulletController;
    private ArrayList<Tree> trees;
    private ArrayList<Tentacle> tentacles;

    public MonsterController(WorldController worldController, BulletController bulletController) {
        this.worldController = worldController;
        this.bulletController = bulletController;
        putTrees();
    }

    public void putTrees() {
        trees = new ArrayList<>();
        Texture backgroundTexture = worldController.getWorld().getBackgroundTexture();
        float bgWidth = backgroundTexture.getWidth();
        float bgHeight = backgroundTexture.getHeight();
        int gridCols = 6;
        int gridRows = 5;
        float cellWidth = bgWidth / gridCols;
        float cellHeight = bgHeight / gridRows;
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int col = i % gridCols;
            int row = i / gridCols % gridRows;
            float x = col * cellWidth + random.nextFloat() * cellWidth;
            float y = row * cellHeight + random.nextFloat() * cellHeight;
            trees.add(new Tree(x, y));
        }
    }
    public void putTentacles() {
        tentacles = new ArrayList<>();
        tentacles.add(new Tentacle(Gdx.graphics.getWidth()/2 + 200, Gdx.graphics.getHeight()/2 + 200));
    }

    public void update(){
        for (Tree tree : trees) {
            tree.update(Gdx.graphics.getDeltaTime());
            tree.getSprite().setPosition(worldController.getPlayer().getX() + tree.getX()
                , worldController.getPlayer().getY() + tree.getY());
            tree.getSprite().draw(Main.getBatch());
            tree.getRect().move(tree.getSprite().getX(), tree.getSprite().getY());
        }
        for (Tentacle tentacle : tentacles) {
            tentacle.update(Gdx.graphics.getDeltaTime());
            tentacle.getSprite().setPosition(worldController.getPlayer().getX() + tentacle.getX()
                , worldController.getPlayer().getY() + tentacle.getY());
            tentacle.getSprite().draw(Main.getBatch());
            tentacle.getRect().move(tentacle.getX(), tentacle.getY());
        }
        checkHit();

    }

    public void checkHit() {
        trees.removeIf(tree -> {
            if (tree.getHP() <= 0) return true;

            bulletController.bullets.removeIf(bullet -> {
                if (tree.getRect().collidesWith(bullet.getRect())) {
                    tree.setHP(tree.getHP() - bullet.getDamage());
                    return true;
                }
                return false;
            });

            return false;
        });
        tentacles.removeIf(tentacle -> {
            if (tentacle.getHP() <= 0) return true;

            bulletController.bullets.removeIf(bullet -> {
                if (tentacle.getRect().collidesWith(bullet.getRect())) {
                    tentacle.setHP(tentacle.getHP() - bullet.getDamage());
                    return true;
                }
                return false;
            });

            return false;
        });
    }

    private void handleTreeDeath(ArrayList<Tree> trees, Tree tree) {
        trees.remove(tree);
    }
}
