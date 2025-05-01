package models.enums;

public enum Terrain {
    Mountain(50 , 100 , 1000 , 0),
    Plain(120 , 125 , 100 , 100),
    Forest(90 , 100 , 500 , 0),
    Urban(110 , 100 ,10 , 20),
    Desert(100 , 140 , 100 , 100);

    public int attack , airAttack , factoryCost , fuelProduction;
    Terrain(int attack , int airAttack , int factoryCost , int fuelProduction) {
        this.attack = attack;
        this.airAttack = airAttack;
        this.factoryCost = factoryCost;
        this.fuelProduction = fuelProduction;
    }
}
