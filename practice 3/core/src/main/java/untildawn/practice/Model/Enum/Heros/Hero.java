package untildawn.practice.Model.Enum.Heros;

public enum Hero {
    Shana(4,4, "Heros/ShanaSpriteSheet.png"),
    Diamond(7,1, "Heros/DiamondSpriteSheet.png"),
    Scarlett(3,5, "Heros/ScarlettSpriteSheet.png"),
    Lilith(5,3, "Heros/LilithSpriteSheet.png"),
    Dasher(2,10, "Heros/DasherSpriteSheet.png"),
    ;
    public final int HP,Speed;
    public final String spriteSheet;

    Hero(int HP, int Speed, String spriteSheet) {
        this.HP = HP;
        this.Speed = Speed;
        this.spriteSheet = spriteSheet;
    }
}
