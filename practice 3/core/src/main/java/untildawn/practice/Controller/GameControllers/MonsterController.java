package untildawn.practice.Controller.GameControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import untildawn.practice.Main;
import untildawn.practice.Model.*;
import untildawn.practice.Model.Enum.Weapons.Weapons;
import untildawn.practice.Model.Monsters.*;

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
    private boolean elderSlayed;
    private Weapon eyeBatWeapon ;

    private float tentacleSpawnTimer = 0;
    private float eyeBatSpawnTimer = 0;
    private float totalTime = 0;
    private Random random = new Random();

    public MonsterController(WorldController worldController, BulletController bulletController) {
        this.worldController = worldController;
        this.bulletController = bulletController;
        putTrees();
        this.tentacles = new ArrayList<>();
        GameAssetManager.setWeapon(Weapons.EyeBat);
        eyeBatWeapon = new Weapon();
        eyeBats.add(new EyeBat(Gdx.graphics.getWidth()/2f + 200, Gdx.graphics.getHeight()/2f + 200));
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
    public void spawnElder() {
        if(elder == null) {
            elder = new Elder((float) Gdx.graphics.getWidth() / 2 + 200, (float) Gdx.graphics.getHeight() / 2 + 200);
        }
    }

    public void update(float totalTimeActual, float endTime){
        updateBullets();
        for (Tree tree : trees) {
            tree.update(Gdx.graphics.getDeltaTime());
            tree.getSprite().setPosition(worldController.getPlayer().getX() + tree.getX()
                , worldController.getPlayer().getY() + tree.getY());
            tree.getSprite().draw(Main.getBatch());
            tree.getRect().move(tree.getSprite().getX(), tree.getSprite().getY());
        }
        //check spawn
        if(!elderSlayed && elder == null && totalTimeActual >= endTime/2){
            spawnElder();
        }
        totalTime += Gdx.graphics.getDeltaTime();
        tentacleSpawnTimer += Gdx.graphics.getDeltaTime();
        eyeBatSpawnTimer += Gdx.graphics.getDeltaTime();
        if (tentacleSpawnTimer >= 3f) { // Every 3 seconds
            int tentaclesToSpawn = (int)(totalTime / 30f);
            for (int i = 0; i < tentaclesToSpawn; i++) {
                spawnTentacleAwayFromPlayer(worldController.getPlayerWorldX(), worldController.getPlayerWorldY());
            }
            tentacleSpawnTimer = 0;
        }
        if(totalTime >= 60f && eyeBatSpawnTimer >= 10f){
            int eyeBatsToSpawn = (int)((6 * totalTime - 360 + 30) / 30);
            for (int i = 0; i < eyeBatsToSpawn; i++) {
                spawnEyeBatAwayFromPlayer(worldController.getPlayerWorldX(), worldController.getPlayerWorldY());
            }
            eyeBatSpawnTimer = 0;
        }

        for (Tentacle tentacle : tentacles) {
            tentacle.update(Gdx.graphics.getDeltaTime(), worldController.getPlayerWorldX(), worldController.getPlayerWorldY());

            float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
            float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;
            tentacle.draw(offsetX, offsetY);
        }
        for (EyeBat bat : eyeBats) {
            bat.update(Gdx.graphics.getDeltaTime(), worldController.getPlayerWorldX(), worldController.getPlayerWorldY());
            float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
            float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;
            bat.draw(offsetX, offsetY);
            bat.shootTimer += Gdx.graphics.getDeltaTime();
            if(bat.shootTimer >= 5f){
                enemyBullets.add(new EnemyBullet(eyeBatWeapon, (int) bat.getSprite().getX(), (int) bat.getSprite().getY()));
                bat.shootTimer = 0f;
            }
        }
        if(elder != null) {
            elder.update(Gdx.graphics.getDeltaTime(), worldController.getPlayerWorldX(), worldController.getPlayerWorldY());
            float offsetX = worldController.getPlayerWorldX() - Gdx.graphics.getWidth() / 2f;
            float offsetY = worldController.getPlayerWorldY() - Gdx.graphics.getHeight() / 2f;
            elder.draw(offsetX, offsetY);
        }
        checkHit();

    }

    private void spawnEyeBatAwayFromPlayer(float playerX, float playerY) {
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

        eyeBats.add(new EyeBat(x, y));
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
            if (tree.getHP() <= 0) {
                dropXP(tree.getX(), tree.getY());
                return true;
            }

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
            if (tentacle.getHP() <= 0) {
                dropXP(tentacle.getX(), tentacle.getY());
                return true;
            }

            bulletController.bullets.removeIf(bullet -> {
                if (tentacle.getRect().collidesWith(bullet.getRect())) {
                    tentacle.setHP(tentacle.getHP() - bullet.getDamage());
                    handleKnockBack(tentacle, bullet);
                    return true;
                }
                return false;
            });

            return false;
        });
        eyeBats.removeIf(bat -> {
            if (bat.getHP() <= 0) {
                dropXP(bat.getX(), bat.getY());
                return true;
            }

            bulletController.bullets.removeIf(bullet -> {
                if (bat.getRect().collidesWith(bullet.getRect())) {
                    bat.setHP(bat.getHP() - bullet.getDamage());
                    handleKnockBack(bat, bullet);
                    return true;
                }
                return false;
            });

            return false;
        });
        if(elder != null){
            if (elder.getHP() <= 0) {
                dropXP(elder.getX(), elder.getY());
                elderSlayed = true;
                elder = null;
            }
            if(elder != null) {
                bulletController.bullets.removeIf(bullet -> {
                    if (elder.getRect().collidesWith(bullet.getRect())) {
                        elder.setHP(elder.getHP() - bullet.getDamage());
                        handleKnockBack(elder, bullet);
                        return true;
                    }
                    return false;
                });
            }
        }
    }

    private void handleKnockBack(Enemy enemy, Bullet bullet) {
        enemy.handleKnockBack(bullet);
    }

    private void dropXP(float x, float y) {
        worldController.getPlayer().setKillCount(worldController.getPlayer().getKillCount() + 1);
        worldController.getXps().add(new XP(x,y));
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

    public void updateBullets() {
        for(Bullet b : enemyBullets) {
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                -Gdx.graphics.getWidth()/2f + b.getX(),
                -Gdx.graphics.getHeight()/2f + b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 6);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 6);
            b.getRect().move(b.getSprite().getX() - direction.x * 6,
                b.getSprite().getY() + direction.y * 6);
        }
    }
}
