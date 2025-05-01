package models.Battalion;

import models.Cost;
import models.Tile;
import models.enums.BattalionCost;

public class Infantry extends Battalion {
    public Infantry(String name , int power , int captureRation , Tile tile) {
        super.type = "infantry";
        super.power = power;
        super.captureRatio = captureRation;
        super.name = name;
        super.tile = tile;
        point = 5;
        cost = new Cost(BattalionCost.Infantry.cost);
    }

    @Override
    public int getCapturePower() {
        return getPower() * captureRatio/100 ;
    }

    @Override
    public int getPower() {
        int p = power;
        p = p * tile.terrain.attack;
        p = p * tile.weather.attackConst;
        return p ;
    }
}
