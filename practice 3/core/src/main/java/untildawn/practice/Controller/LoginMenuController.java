package untildawn.practice.Controller;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Main;
import untildawn.practice.Model.App;
import untildawn.practice.Model.Enum.dialogue.Dialogues;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.Model.User;
import untildawn.practice.View.*;

public class LoginMenuController {
    public LoginMenu view;
    public void setView(LoginMenu loginMenu) {
        this.view = loginMenu;
    }

    public void handleLogin() {
        if(view != null && view.getAdvanceButton().isChecked()) {
            User user = SignupMenuController.getUserByName(view.getUsernameField().getText());
            if(user == null) {
                showErrorDialog(Dialogues.ErrorUsernameDoesNotExist.getTitle(), Dialogues.ErrorUsernameDoesNotExist.getText());
                resetFormFields();
            }
            else if(!user.getPassword().equals(view.getPasswordField().getText())) {
                showErrorDialog(Dialogues.ErrorPasswordIncorrect.getTitle(), Dialogues.ErrorPasswordIncorrect.getText());
                resetFormFields();
            }
            else {
                App.setLoggedInUser(user);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getManager().getSkin()));
            }
        }
    }
    public void handleForgotPassword() {
        if(view != null && view.getForgotPasswordButton().isChecked()) {
            User user = SignupMenuController.getUserByName(view.getUsernameField().getText());
            if(user == null) {
                showErrorDialog(Dialogues.ErrorUsernameDoesNotExist.getTitle(), Dialogues.ErrorUsernameDoesNotExist.getText());
                resetFormFields();
            }
            else if(user.getSecurityQuestion() == null) {
                showErrorDialog(Dialogues.ErrorNoSecurityQuestion.getTitle(), Dialogues.ErrorNoSecurityQuestion.getText());
                resetFormFields();
            }
            else{
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ForgotPasswordMenu(new ForgotPasswordMenuController(),GameAssetManager.getManager().getSkin(), user));
            }
        }
    }
    public void handleGoBack(){
        if(view != null && view.getGoToSignUpButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new SignupMenu(new SignupMenuController(), GameAssetManager.getManager().getSkin()));
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
        view.getAdvanceButton().setChecked(false);
        view.getForgotPasswordButton().setChecked(false);
    }
}
