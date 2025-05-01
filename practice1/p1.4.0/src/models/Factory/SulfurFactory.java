package models.Factory;

import models.Countries.Countries;
import models.Countries.Faction;
import models.enums.FactoryCost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SulfurFactory extends Factory{
    public SulfurFactory(String name) {
        type = "sulfur factory";
        resourceType = "sulfur";
        super.name = name;
        super.maxProduction = FactoryCost.Sulfur.maxProduction;
        super.cost = FactoryCost.Sulfur.cost;
        super.productionPerMP = FactoryCost.Sulfur.PPMP;
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
            commy.sulfur += each;
        }
        country.manPower -= amount;
        production += produce;
        return each;
    }
}
