package models.enums;

import models.Cost;

public enum FactoryCost {
    Steel(new Cost(0 , 0 , 0 , 10000) ,30000 , 10),
    Sulfur(new Cost(0 , 0 , 0 , 20000) ,20000 , 20),
    Fuel(new Cost(0 , 5000 , 0 , 50000) , 100000 , 50);

    public Cost cost;
    public int maxProduction;
    public int PPMP;
    FactoryCost(Cost cost, int maxProduction , int PPMP) {
        this.cost = cost;
        this.maxProduction = maxProduction;
        this.PPMP = PPMP;
    }

}
