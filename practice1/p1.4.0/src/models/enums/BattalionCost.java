package models.enums;

import models.Cost;

public enum BattalionCost {
    Infantry(new Cost(0 , 10000 , 10000 , 15000)),
    Panzer(new Cost(10000 , 20000 , 10000 , 5000)),
    Navy(new Cost(30000 , 50000 , 10000 , 5000)),
    AirForce(new Cost(50000 , 35000 , 10000 , 1000));
    public Cost cost;
    BattalionCost(Cost cost) {
        this.cost = cost;
    }
}
