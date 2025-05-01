package models.Factory;

import models.Countries.Countries;
import models.Countries.Faction;
import models.enums.FactoryCost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SteelFactory extends Factory{
    public SteelFactory(String name) {
        type = "steel factory";
        resourceType = "steel";
        super.name = name;
        super.maxProduction = FactoryCost.Steel.maxProduction;
        super.cost = FactoryCost.Steel.cost;
        super.productionPerMP = FactoryCost.Steel.PPMP;
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
            commy.steel += each;
        }
        country.manPower -= amount;
        production += produce;
        return each;
    }
}
