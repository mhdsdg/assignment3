package models.Factory;

import models.Cost;
import models.Countries.Countries;

public abstract class Factory {
    public String name;
    public String type;
    public String resourceType;
    public int maxProduction;
    public int production;
    public int productionPerMP;
    public Cost cost;
    public int getProductionLeft(){
        return maxProduction - production;
    }
    public abstract int runFactory(Countries country , int produce , int amount) ;
}
