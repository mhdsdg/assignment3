package models.Factory;

import models.Countries.Countries;
import models.Countries.Faction;
import models.enums.FactoryCost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FuelRefinery extends Factory{
    public FuelRefinery(String name) {
        type = "fuel refinery";
        resourceType = "fuel";
        super.name = name;
        super.maxProduction = FactoryCost.Fuel.maxProduction;
        super.productionPerMP = FactoryCost.Fuel.PPMP;
        super.cost = FactoryCost.Fuel.cost;
    }

    @Override
    public int runFactory(Countries country, int produce, int amount) {
        Set<Countries> commies = new HashSet<>();
        commies.add(country);
        if(country.leader.ideology.equals("communism")) {
            for (Faction faction : country.factions) {
                for (Countries count : faction.countries) {
                    if (count.leader.ideology.equals("communism")) commies.add(count);
                }
            }
        }
        int each = produce/(commies.size());
        for (Countries commy : commies) {
            commy.fuel += each;
        }
        country.manPower -= amount;
        production += produce;
        return each;
    }
}
