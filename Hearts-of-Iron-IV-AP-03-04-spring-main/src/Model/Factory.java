package Model;

import java.util.*;

public class Factory {
	private final String name;
	private final FactoryType type;
	private int production = 0;

	public Factory(String name, FactoryType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public FactoryType getType() {
		return type;
	}


	public void addToProduction(int production) {
		this.production += production;
	}

	public static Map<String, Integer> getAllInfo(FactoryType type, int tileIndex) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		assert tile != null;
		Leader leader = Country.getCountryByName(Game.currentGame.getCurrentPlayer().getCountry().getName()).getLeader();
		Map<String, Integer> costs = new HashMap<>();
		costs.put("Manpower Cost", type.manPowerCost);
		costs.put("Steel Cost", type.steelCost);
		costs.put("Production Per Manpower", type.productionPerManPower);
		costs.put("Max Production", type.maxProduction);
		if (tile.getTerrain() == Terrain.MOUNTAIN) {
			costs.computeIfPresent("Manpower Cost", (key, value) -> value * 10);
			costs.computeIfPresent("Steel Cost", (key, value) -> value * 10);
			costs.computeIfPresent("Production Per Manpower", (key, value) -> 0);

		} else if (tile.getTerrain() == Terrain.FOREST) {
			costs.computeIfPresent("Manpower Cost", (key, value) -> value * 5);
			costs.computeIfPresent("Steel Cost", (key, value) -> value * 5);
			costs.computeIfPresent("Production Per Manpower", (key, value) -> 0);
		} else if (tile.getTerrain() == Terrain.URBAN) {
			costs.computeIfPresent("Manpower Cost", (key, value) -> (int) (value * 0.1));
			costs.computeIfPresent("Steel Cost", (key, value) -> (int) (value * 0.1));
			costs.computeIfPresent("Production Per Manpower", (key, value) -> (int) (value * 0.2));
		}
		if (leader.getIdeology().equals(Ideology.COMMUNISM)) {
			costs.computeIfPresent("Manpower Cost", (key, value) -> value / 2);
			costs.computeIfPresent("Steel Cost", (key, value) -> value / 2);
		}
		return costs;
	}

	public int getProductionLeft(int tileIndex) {
		return getAllInfo(type, tileIndex).get("Max Production") - production;
	}
}
