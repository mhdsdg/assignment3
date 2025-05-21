package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import untildawn.practice.Main;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.Monsters.Tree;

import java.util.ArrayList;

public class MonsterController {
    private WorldController worldController;
    private BulletController bulletController;
    private ArrayList<Tree> trees;

    public MonsterController(WorldController worldController, BulletController bulletController) {
        this.worldController = worldController;
        this.bulletController = bulletController;
        putTrees();
    }

    public void putTrees(){
        trees = new ArrayList<>();
        trees.add(new Tree(Gdx.graphics.getWidth()/2 + 100, Gdx.graphics.getHeight()/2 + 100));
    }

    public void update(){
        for (Tree tree : trees) {
            tree.getSprite().setPosition(worldController.getPlayer().getX() + tree.getX()
                , worldController.getPlayer().getY() + tree.getY());
            tree.getSprite().draw(Main.getBatch());
            tree.getRect().move(tree.getSprite().getX(), tree.getSprite().getY());
        }
        checkHit();
    }

    public void checkHit(){
        for (int i = 0; i < trees.size(); i++) {
            if(trees.get(i).getHP() <= 0) {
                handleTreeDeath(trees, trees.get(i));
                continue;
            }
            for (int i1 = 0; i1 < bulletController.bullets.size(); i1++) {
                Bullet bullet = bulletController.bullets.get(i1);
                if(trees.get(i).getRect().collidesWith(bullet.getRect())){
                    bulletController.bullets.remove(bullet);
                    trees.get(i).setHP(trees.get(i).getHP() - bullet.getDamage());
                }
            }
        }
    }

    private void handleTreeDeath(ArrayList<Tree> trees, Tree tree) {
        trees.remove(tree);
    }
}
