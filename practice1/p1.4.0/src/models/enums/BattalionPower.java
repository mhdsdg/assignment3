package models.enums;

public enum BattalionPower {
    GermanReich(15 ,5 ,20 , 10),
    SovietUnion(20 , 10 , 15 , 5),
    UnitedStates(5 , 15 , 10 , 20),
    UnitedKingdom(10 , 20 , 5 , 15),
    Japan(10 , 10 , 10 , 10);

    public int infantry , navy , panzer , airforce;

    BattalionPower(int infantry, int navy, int panzer, int airforce) {
        this.infantry = infantry;
        this.navy = navy;
        this.panzer = panzer;
        this.airforce = airforce;
    }
}
