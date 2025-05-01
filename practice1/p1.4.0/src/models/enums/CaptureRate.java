package models.enums;

public enum CaptureRate {
    GermanReich(50 , 20 ,30 , 40),
    SovietUnion(20 , 30 ,60 ,40),
    UnitedStates(30 , 60 , 40 , 50),
    UnitedKingdom(60 , 40 , 50 , 20),
    Japan(40 , 50 , 20 , 30);

    public final int infantry , navy , panzer , airforce;
    CaptureRate(int infantry, int navy, int panzer, int airforce) {
        this.infantry = infantry;
        this.navy = navy;
        this.panzer = panzer;
        this.airforce = airforce;
    }
}
