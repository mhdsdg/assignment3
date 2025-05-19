package untildawn.practice.Model.Enum.dialogue;

import untildawn.practice.Model.App;

public enum Dialogues {
    ErrorUsernameExists(EnglishDialogue.ErrorUsernameExists, MedievalEnglishDialogue.ErrorUsernameExists),
    ErrorPasswordWeak(EnglishDialogue.ErrorPasswordWeak, MedievalEnglishDialogue.ErrorPasswordWeak),

    ;
    Dialogue english, medieval;

    Dialogues(Dialogue english, Dialogue medieval) {
        this.english = english;
        this.medieval = medieval;
    }

    public String getTitle() {
        switch (App.getLanguage()){
            case "English":
                return english.getTitle();
                case "Medieval":
                    return medieval.getTitle();
                    default:
                        return english.getTitle();
        }
    }

    public String getText(){
        switch (App.getLanguage()){
            case "English":
                return english.getText();
                case "Medieval":
                    return medieval.getText();
                    default:
                        return english.getText();
        }
    }



}
