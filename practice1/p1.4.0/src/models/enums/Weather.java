package models.enums;

public enum Weather {
    Sunny(100 , 100),
    Rainy(80 , 10),
    Blizzard(60 , 30),
    SandStorm(30 , 60),
    Fog(20 , 70);

    public int attackConst , airAttackConst;

    Weather(int attackConst , int airAttackConst) {
        this.attackConst = attackConst;
        this.airAttackConst = airAttackConst;
    }
}
