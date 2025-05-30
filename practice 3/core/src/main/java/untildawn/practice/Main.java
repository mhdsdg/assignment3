package untildawn.practice;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import untildawn.practice.Controller.SignupMenuController;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.View.ProfileMenu;
import untildawn.practice.View.SignupMenu;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private static ProfileMenu profileMenu;
    private static OrthographicCamera camera;

    public static ProfileMenu getProfileMenu() {
        return profileMenu;
    }

    public static void setProfileMenu(ProfileMenu menu) {
        profileMenu = menu;
    }


    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        getMain().setScreen(new SignupMenu(new SignupMenuController(), GameAssetManager.getManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
