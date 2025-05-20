package untildawn.practice.Controller;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import untildawn.practice.Model.Enum.dialogue.Dialogues;
import untildawn.practice.Model.Enum.dialogue.EnglishDialogue;
import untildawn.practice.Model.Enum.Regexes;
import untildawn.practice.Model.User;
import untildawn.practice.View.LoginMenu;
import untildawn.practice.View.MainMenu;
import untildawn.practice.View.PreGameMenu;
import untildawn.practice.View.SignupMenu;
import untildawn.practice.Model.GameAssetManager;

public class SignupMenuController {
    private SignupMenu view;

    public void setView(SignupMenu view) {
        this.view = view;
    }

    public void signup() {
        if (view != null && view.getAdvanceButton().isChecked()) {
            if(getUserByName(view.getUsernameField().getText()) != null) {//handle errors
                showErrorDialog(Dialogues.ErrorUsernameExists.getTitle(), Dialogues.ErrorUsernameExists.getText());
                resetFormFields();
            }
            else if(Regexes.Password.getMatcher(view.getPasswordField().getText()) == null) {
                showErrorDialog(Dialogues.ErrorPasswordWeak.getTitle(), Dialogues.ErrorPasswordWeak.getText());
                resetFormFields();
            }
            else{
                User user = new User(view.getUsernameField().getText(), view.getPasswordField().getText());
                App.getUsers().add(user);
                if(view.getSecurityQuestionField().getText() != null) {
                    user.setSecurityQuestion(view.getSecurityQuestionField().getText());
                }
                if(view.getSecurityAnswerField().getText() != null) {
                    user.setSecurityAnswer(view.getSecurityAnswerField().getText());
                }
                //TODO : random avatar
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getManager().getSkin()));
            }
        }
        if(view != null && view.getGuestButton().isChecked()) {//guest
            App.setIsGuest(true);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new PreGameMenu(new PreGameMenuController(), GameAssetManager.getManager().getSkin()));
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
        view.getUsernameField().setMessageText(view.getUsernameField().getMessageText());
        view.getPasswordField().setMessageText(view.getPasswordField().getMessageText());
        view.getSecurityQuestionField().setMessageText(view.getSecurityQuestionField().getMessageText());
        view.getSecurityAnswerField().setMessageText(view.getSecurityAnswerField().getMessageText());
        view.getAdvanceButton().setChecked(false);
        view.getGuestButton().setChecked(false);
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
