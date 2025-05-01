package models.Battalion;

import models.Cost;
import models.Tile;
import models.enums.BattalionCost;

public class Panzer extends Battalion {
    public Panzer(String name , int power , int captureRate , Tile tile) {
        type = "panzer";
        super.power = power;
        super.captureRatio = captureRate;
        super.name = name;
        super.tile = tile;
        point = 7;
        cost = new Cost(BattalionCost.Panzer.cost);
    }

    @Override
    public int getCapturePower() {
        return getPower() * captureRatio/100 ;
    }

    @Override
    public int getPower() {
        double p = power;
        p = p * tile.terrain.attack/100;
        p = p * tile.weather.attackConst/100;
        return (int)p ;
    }
}
