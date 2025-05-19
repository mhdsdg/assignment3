package Model;

public enum Terrain {
	MOUNTAIN(50, 100, 10000, 0), PLAIN(120, 125, 100, 100), FOREST(90, 100, 500, 0), URBAN(110, 100, 10, 20), DESERT(100, 140, 100, 100);

	public final int attack;
	public final int airAttack;
	public final int factoryCost;
	public final int fuel;

	Terrain(int attack, int airAttack, int factoryCost, int fuel) {
		this.attack = attack;
		this.airAttack = airAttack;
		this.factoryCost = factoryCost;
		this.fuel = fuel;
	}

	public static Terrain getTerrain(String name) {
		return switch (name) {
			case "mountain" -> Terrain.MOUNTAIN;
			case "forest" -> Terrain.FOREST;
			case "desert" -> Terrain.DESERT;
			case "urban" -> Terrain.URBAN;
			case "plain" -> Terrain.PLAIN;
			default -> null;
		};
	}
}