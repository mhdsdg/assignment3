package models.Battalion;

import models.Cost;
import models.Tile;
import models.enums.BattalionCost;

public class AirForce extends Battalion {
    public AirForce(String name , int power , int captureRation , Tile tile) {
        super.type = "airforce";
        super.power = power;
        super.captureRatio = captureRation;
        super.name = name;
        super.tile = tile;
        point = 15;
        cost = new Cost(BattalionCost.AirForce.cost);
    }

    @Override
    public int getCapturePower() {
        return getPower() * (captureRatio / 100);
    }

    @Override
    public int getPower() {
        int p = power;
        p = p * tile.terrain.airAttack;
        p = p * tile.weather.airAttackConst;
        return p ;
    }


}
