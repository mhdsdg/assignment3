package untildawn.practice.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import untildawn.practice.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("until dawn");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(1920, 1080);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        // 👇 Add this to handle desktop drag-and-drop
        configuration.setWindowListener(new com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter() {
            @Override
            public void filesDropped(String[] files) {
                for (String path : files) {
                    System.out.println("File dropped: " + path);

                    com.badlogic.gdx.Gdx.app.postRunnable(() -> {
                        com.badlogic.gdx.files.FileHandle fileHandle = com.badlogic.gdx.Gdx.files.absolute(path);
                        try {
                            com.badlogic.gdx.graphics.Texture texture = new com.badlogic.gdx.graphics.Texture(fileHandle);
                            untildawn.practice.Model.App.getLoggedInUser().setAvatar(texture);

                            // Optional: update the ProfileMenu view if needed
                            if (untildawn.practice.Main.getProfileMenu() != null) {
                                untildawn.practice.Main.getProfileMenu().getCurrentAvatar().setDrawable(
                                    new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(texture)
                                );
                            }
                        } catch (Exception e) {
                            com.badlogic.gdx.Gdx.app.error("DragDrop", "Failed to load image from file drop", e);
                        }
                    });
                }
            }
        });

        return configuration;
    }

}
