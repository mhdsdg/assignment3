package untildawn.practice.Model.Enum.dialogue;

public enum EnglishDialogue implements Dialogue {
    ErrorUsernameExists("this username exists", "Please try a different username" ),
    ErrorPasswordWeak("password invalid","Please enter a valid password"),






    ;

    public final String inEnglish, inEnglishMessage ;
    EnglishDialogue(String inEnglish, String inEnglishMessage) {
        this.inEnglish = inEnglish;
        this.inEnglishMessage = inEnglishMessage;
    }

    @Override
    public String getTitle() {
        return inEnglish;
    }

    @Override
    public String getText() {
        return inEnglishMessage;
    }
}
