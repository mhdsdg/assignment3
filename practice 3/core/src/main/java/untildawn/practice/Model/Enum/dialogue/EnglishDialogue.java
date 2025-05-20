package untildawn.practice.Model.Enum.dialogue;

public enum EnglishDialogue implements Dialogue {
    ErrorUsernameExists("this username exists", "Please try a different username" ),
    ErrorPasswordWeak("password invalid","Please enter a valid password"),
    ErrorUsernameDoesNotExist("username doesn't exist","Please enter a valid username" ),
    ErrorPasswordIncorrect("password incorrect","Please enter your password correctly" ),
    ErrorNoSecurityQuestion("no security question","you haven't set a security question at signup" ),
    ErrorSecurityAnswerIncorrect("wrong answer", "Please enter your answer correctly" ),;

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
