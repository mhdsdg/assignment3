package untildawn.practice.Model.Enum.dialogue;

public enum MedievalEnglishDialogue implements Dialogue {
    ErrorUsernameExists("", "" ),
    ErrorPasswordWeak("",""),






    ;

    public final String inMedievalEnglish, inMedievalEnglishMessage ;
    MedievalEnglishDialogue(String inMedievalEnglish, String inMedievalEnglishMessage) {
        this.inMedievalEnglish = inMedievalEnglish;
        this.inMedievalEnglishMessage = inMedievalEnglishMessage;
    }

    @Override
    public String getTitle() {
        return inMedievalEnglish;
    }

    @Override
    public String getText() {
        return inMedievalEnglishMessage;
    }
}
