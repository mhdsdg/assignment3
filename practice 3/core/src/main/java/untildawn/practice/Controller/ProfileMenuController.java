package untildawn.practice.Controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import untildawn.practice.Main;
import untildawn.practice.Model.Adapters.SerializationUtility;
import untildawn.practice.Model.App;
import untildawn.practice.Model.Enum.Regexes;
import untildawn.practice.Model.Enum.dialogue.Dialogues;
import untildawn.practice.Model.FileChooser;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.User;
import untildawn.practice.View.LoginMenu;
import untildawn.practice.View.MainMenu;
import untildawn.practice.View.ProfileMenu;

public class ProfileMenuController {
    private ProfileMenu view;

    public void setView(ProfileMenu view) {
        this.view = view;
    }

    public ClickListener getChangeUsernameListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newUsername = view.getNewUsernameField().getText();
                if (!newUsername.isEmpty()) {
                    if(getUserByName(view.getNewUsernameField().getText()) != null) {//handle errors
                        showErrorDialog(Dialogues.ErrorUsernameExists.getTitle(), Dialogues.ErrorUsernameExists.getText());
                        resetFormFields();
                    }
                    else {
                        App.getLoggedInUser().setUsername(newUsername);
                        SerializationUtility.saveAppState("users.json");
                    }
                }
            }
        };
    }

    public ClickListener getChangePasswordListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newPassword = view.getNewPasswordField().getText();
                if(Regexes.Password.getMatcher(newPassword) == null) {
                    showErrorDialog(Dialogues.ErrorPasswordWeak.getTitle(), Dialogues.ErrorPasswordWeak.getText());
                    resetFormFields();
                }
                else {
                    App.getLoggedInUser().setPassword(newPassword);
                    SerializationUtility.saveAppState("users.json");
                }
            }
        };
    }

    public ChangeListener getAvatarSelectListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                Texture selectedAvatar = GameAssetManager.getAvatars().get(0);
                switch (view.getAvatarSelectBox().getSelectedIndex()) {
                    case 0: selectedAvatar = GameAssetManager.getAvatars().get(0); break;
                    case 1: selectedAvatar = GameAssetManager.getAvatars().get(1); break;
                    case 2: selectedAvatar = GameAssetManager.getAvatars().get(2); break;
                    case 3: selectedAvatar = GameAssetManager.getAvatars().get(3); break;
                }
                App.getLoggedInUser().setAvatar(selectedAvatar);
                SerializationUtility.saveAppState("users.json");
            }
        };
    }

    public ClickListener getDeleteAccountListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                App.getUsers().remove(App.getLoggedInUser());
                App.setLoggedInUser(null);
                SerializationUtility.saveAppState("users.json");
                Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }

    public ClickListener getBackListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getManager().getSkin()));
            }
        };
    }

    public ClickListener getChooseImageListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    // Desktop implementation using FileChooser
                    showDesktopFileChooser();
                }
            }
        };
    }

    private void showDesktopFileChooser() {
        try {
            // This requires the gdx-backend-lwjgl3 dependency
            FileHandle defaultDir = Gdx.files.external("");
            FileChooser fileChooser = new FileChooser(FileChooser.Mode.OPEN);
            fileChooser.setDirectory(defaultDir);
            fileChooser.setSelectionMode(FileChooser.SelectionMode.FILES);
            fileChooser.setListener(new FileChooser.Listener() {
                @Override
                public void selected(Array<FileHandle> files) {
                    if (files.size > 0) {
                        loadSelectedImage(files.first());
                    }
                }

                @Override
                public void canceled() {
                    // User canceled the selection
                }
            });
            fileChooser.show();
        } catch (Exception e) {
            showErrorDialog("Error", "File chooser not available");
            Gdx.app.error("FileChooser", "Error showing file chooser", e);
        }
    }

    private void loadSelectedImage(FileHandle fileHandle) {
        try {
            Texture newTexture = new Texture(fileHandle);
            view.getCurrentAvatar().setDrawable(new TextureRegionDrawable(newTexture));
            App.getLoggedInUser().setAvatar(newTexture);
        } catch (Exception e) {
            showErrorDialog("Error", "Failed to load image");
            Gdx.app.error("Image Upload", "Error loading selected image", e);
        }
    }

    private void showErrorDialog(String title, String message) {
        Skin skin = GameAssetManager.getManager().getSkin();
        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("OK");
        dialog.show(view.getStage());
    }

    private void resetFormFields() {
        // Reset username field
        view.getNewUsernameField().setMessageText(view.getNewUsernameField().getMessageText());
        view.getNewPasswordField().setMessageText(view.getNewPasswordField().getMessageText());
    }
    public static User getUserByName(String username) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
