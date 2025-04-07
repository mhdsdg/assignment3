package models.Battalion;

import models.Cost;
import models.Tile;
import models.enums.BattalionCost;


public class Navy extends Battalion {
    public Navy(String name , int power , int captureRation , Tile tile) {
        super.type = "navy";
        super.power = power;
        super.captureRatio = captureRation;
        super.name = name;
        super.tile = tile;
        point = 10;
        cost = new Cost(BattalionCost.Navy.cost);
    }

    @Override
    public int getCapturePower() {
        return power * (captureRatio / 100);
    }

    @Override
    public int getPower() {
        return power;
    }
}
