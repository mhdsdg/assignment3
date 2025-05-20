package untildawn.practice.Controller;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import untildawn.practice.Main;
import untildawn.practice.Model.Enum.Regexes;
import untildawn.practice.Model.Enum.dialogue.Dialogues;
import untildawn.practice.Model.GameAssetManager;
import untildawn.practice.View.ForgotPasswordMenu;
import untildawn.practice.View.LoginMenu;
import untildawn.practice.View.MainMenu;

public class ForgotPasswordMenuController {
    ForgotPasswordMenu view ;

    public void setView(ForgotPasswordMenu view) {
        this.view = view;
    }

    public void handleForgetPassword() {
        if(view != null && view.getAdvanceButton().isChecked()){
            if(!view.getAnswerTextField().getText().equalsIgnoreCase(view.getUser().getSecurityAnswer())){
                showErrorDialog(Dialogues.ErrorSecurityAnswerIncorrect.getTitle(), Dialogues.ErrorSecurityAnswerIncorrect.getText());
                resetFormFields();
            }
            else if(Regexes.Password.getMatcher(view.getNewPasswordTextField().getText()) == null){
                showErrorDialog(Dialogues.ErrorPasswordWeak.getTitle(), Dialogues.ErrorPasswordWeak.getText());
                resetFormFields();
            }
            else {
                view.getUser().setPassword(view.getNewPasswordTextField().getText());
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getManager().getSkin()));
            }
        }
    }

    public void handleGoBack() {
        if(view != null && view.getBackButton().isChecked()){
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getManager().getSkin()));
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
        view.getNewPasswordTextField().setMessageText(view.getNewPasswordTextField().getMessageText());
        view.getAdvanceButton().setChecked(false);
    }
}
