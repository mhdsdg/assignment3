package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import untildawn.practice.Main;
import untildawn.practice.Model.Bullet;
import untildawn.practice.Model.EnemyBullet;
import untildawn.practice.Model.Monsters.Elder;
import untildawn.practice.Model.Monsters.EyeBat;
import untildawn.practice.Model.Monsters.Tentacle;
import untildawn.practice.Model.Monsters.Tree;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private WorldController worldController;
    private BulletController bulletController;

    private ArrayList<Tree> trees;
    private ArrayList<Tentacle> tentacles;
    private ArrayList<EyeBat> eyeBats = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private Elder elder;

    private float tentacleSpawnTimer = 0;
    private float totalTime = 0;
    private Random random = new Random();

    public MonsterController(WorldController worldController, BulletController bulletController) {
        this.worldController = worldController;
        this.bulletController = bulletController;
        putTrees();
        this.tentacles = new ArrayList<>();
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
        totalTime += Gdx.graphics.getDeltaTime();
        tentacleSpawnTimer += Gdx.graphics.getDeltaTime();
        if (tentacleSpawnTimer >= 3f) { // Every 3 seconds
            int tentaclesToSpawn = (int)(totalTime / 30f);
            for (int i = 0; i < tentaclesToSpawn; i++) {
                spawnTentacleAwayFromPlayer(worldController.getPlayerWorldX(), worldController.getPlayerWorldY());
            }
            tentacleSpawnTimer = 0;
        }

        for (Tentacle tentacle : tentacles) {
            tentacle.update(Gdx.graphics.getDeltaTime(), worldController.getPlayerWorldX(), worldController.getPlayerWorldY());

            float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
            float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;

            tentacle.draw(offsetX, offsetY);
        }
        checkHit();

    }

    private void spawnTentacleAwayFromPlayer(float playerX, float playerY) {
        Texture backgroundTexture = worldController.getWorld().getBackgroundTexture();
        float minDistance = 300f; // Minimum distance from player to spawn

        float x, y;
        int attempts = 0;
        int maxAttempts = 10;

        do {
            // Random position within world bounds
            x = random.nextFloat() * backgroundTexture.getWidth();
            y = random.nextFloat() * backgroundTexture.getHeight();
            attempts++;

            // Give up after max attempts to avoid infinite loop
            if (attempts >= maxAttempts) {
                // Fallback position at edge of minimum distance circle
                float angle = random.nextFloat() * (float)Math.PI * 2;
                x = playerX + (float)Math.cos(angle) * minDistance;
                y = playerY + (float)Math.sin(angle) * minDistance;
                break;
            }
        } while (Math.sqrt(Math.pow(x - playerX, 2) + Math.pow(y - playerY, 2)) < minDistance);

        tentacles.add(new Tentacle(x, y));
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

    public ArrayList<EyeBat> getEyeBats() {
        return eyeBats;
    }

    public void setEyeBats(ArrayList<EyeBat> eyeBats) {
        this.eyeBats = eyeBats;
    }

    public ArrayList<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<EnemyBullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public Elder getElder() {
        return elder;
    }

    public void setElder(Elder elder) {
        this.elder = elder;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Tentacle> getTentacles() {
        return tentacles;
    }
}
