package models.Battalion;

import models.Cost;
import models.Countries.Countries;
import models.Tile;
import models.enums.BattalionCost;

public abstract class Battalion {
    public String name;
    public String type ;
    public int power ;
    public int captureRatio ;
    public int level = 0;
    public int point;
    public Tile tile;
    public Cost cost;

    public abstract int getCapturePower();
    public abstract int getPower();
    public void democracyCheck(Countries country) {
        if(country.leader.ideology.equals("democracy")) {
            multiplyCost(2);
        }
    }
    public void multiplyCost(double x) {
        cost.fuel *= x;
        cost.manPower *= x;
        cost.steel *= x;
        cost.sulfur *= x;
    }

}
