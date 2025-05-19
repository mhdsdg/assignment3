package Model;

import java.util.*;

public class Tile {
	private static Map<Integer, Tile> tileMap = new HashMap<>();
	private static Set<Tile> terrainSet = new HashSet<>();
	private String ownerCountry;
	private final String initialOwnerCountry;

	private final List<Integer> neighbors;
	private final List<Integer> seaNeighbors;
	private final List<Factory> factories;
	private Terrain terrain;
	private Weather weather;
	private final ArrayList<Battalion> battalions;

	public static void initialize() {
		tileMap = new HashMap<>();
		terrainSet = new HashSet<>();
		for (int i = 1; i <= 56; i++) {
			String country;
			if (i <= 16) country = "Soviet Union";
			else if (i <= 34) country = "United States";
			else if (i <= 50) country = "German Reich";
			else if (i <= 53) country = "Japan";
			else country = "United Kingdom";

			ArrayList<Integer> neighbor = new ArrayList<>();
			ArrayList<Integer> seaNeighbor = new ArrayList<>();

			switch (i) {
				case 1 -> neighbor.addAll(Arrays.asList(2, 5));
				case 2 -> neighbor.addAll(Arrays.asList(1, 3, 6));
				case 3 -> neighbor.addAll(Arrays.asList(2, 7, 4));
				case 4 -> neighbor.addAll(Arrays.asList(3, 8));
				case 5 -> neighbor.addAll(Arrays.asList(1, 6, 9));
				case 6 -> neighbor.addAll(Arrays.asList(2, 5, 10, 7));
				case 7 -> neighbor.addAll(Arrays.asList(3, 6, 11, 8));
				case 8 -> {
					neighbor.addAll(Arrays.asList(4, 7, 12));
					seaNeighbor.addAll(Arrays.asList(35, 36, 54));
				}
				case 9 -> neighbor.addAll(Arrays.asList(5, 10, 13));
				case 10 -> neighbor.addAll(Arrays.asList(6, 9, 14, 11));
				case 11 -> neighbor.addAll(Arrays.asList(7, 10, 15, 12));
				case 12 -> neighbor.addAll(Arrays.asList(8, 11, 16, 35));
				case 13 -> neighbor.addAll(Arrays.asList(9, 14));
				case 14 -> neighbor.addAll(Arrays.asList(13, 10, 15, 17, 18));
				case 15 -> neighbor.addAll(Arrays.asList(14, 11, 16, 19, 20));
				case 16 -> neighbor.addAll(Arrays.asList(12, 15, 39, 21, 22));
				case 17 -> neighbor.addAll(Arrays.asList(14, 18, 23));
				case 18 -> neighbor.addAll(Arrays.asList(17, 14, 19, 24));
				case 19 -> neighbor.addAll(Arrays.asList(18, 15, 20, 25));
				case 20 -> neighbor.addAll(Arrays.asList(19, 15, 21, 26));
				case 21 -> neighbor.addAll(Arrays.asList(20, 16, 22, 27));
				case 22 -> neighbor.addAll(Arrays.asList(21, 16, 43, 28));
				case 23 -> neighbor.addAll(Arrays.asList(17, 24, 29));
				case 24 -> neighbor.addAll(Arrays.asList(23, 18, 25, 30));
				case 25 -> neighbor.addAll(Arrays.asList(24, 19, 26, 31));
				case 26 -> neighbor.addAll(Arrays.asList(25, 20, 27, 32));
				case 27 -> neighbor.addAll(Arrays.asList(26, 28, 21, 33));
				case 28 -> neighbor.addAll(Arrays.asList(27, 22, 47, 34));
				case 29 -> neighbor.addAll(Arrays.asList(23, 30));
				case 30 -> neighbor.addAll(Arrays.asList(29, 24, 31));
				case 31 -> neighbor.addAll(Arrays.asList(30, 25, 32));
				case 32 -> neighbor.addAll(Arrays.asList(31, 26, 33));
				case 33 -> neighbor.addAll(Arrays.asList(32, 27, 34));
				case 34 -> {
					neighbor.addAll(Arrays.asList(33, 28));
					seaNeighbor.addAll(Arrays.asList(47, 48, 49, 52));
				}
				case 35 -> {
					neighbor.addAll(Arrays.asList(12, 39, 36));
					seaNeighbor.addAll(Arrays.asList(8, 36, 54));
				}
				case 36 -> {
					neighbor.addAll(Arrays.asList(35, 40, 37));
					seaNeighbor.addAll(Arrays.asList(8, 35, 54));
				}
				case 37 -> neighbor.addAll(Arrays.asList(36, 41, 38, 54));
				case 38 -> neighbor.addAll(Arrays.asList(37, 42, 55));
				case 39 -> neighbor.addAll(Arrays.asList(16, 35, 40, 43));
				case 40 -> neighbor.addAll(Arrays.asList(39, 36, 41, 44));
				case 41 -> neighbor.addAll(Arrays.asList(40, 37, 42, 45));
				case 42 -> neighbor.addAll(Arrays.asList(38, 41, 46));
				case 43 -> neighbor.addAll(Arrays.asList(22, 39, 44, 47));
				case 44 -> neighbor.addAll(Arrays.asList(40, 43, 48, 45));
				case 45 -> neighbor.addAll(Arrays.asList(41, 44, 49, 46));
				case 46 -> neighbor.addAll(Arrays.asList(42, 45, 50));
				case 47 -> {
					neighbor.addAll(Arrays.asList(28, 43, 48));
					seaNeighbor.addAll(Arrays.asList(34, 48, 49, 52));
				}
				case 48 -> {
					neighbor.addAll(Arrays.asList(47, 44, 49));
					seaNeighbor.addAll(Arrays.asList(34, 47, 49, 52));
				}
				case 49 -> {
					neighbor.addAll(Arrays.asList(48, 45, 50));
					seaNeighbor.addAll(Arrays.asList(34, 47, 48, 52));
				}
				case 50 -> neighbor.addAll(Arrays.asList(49, 46, 51, 52));
				case 51 -> neighbor.addAll(Arrays.asList(50, 53));
				case 52 -> {
					neighbor.addAll(Arrays.asList(50, 53));
					seaNeighbor.addAll(Arrays.asList(34, 47, 48, 49));
				}
				case 53 -> neighbor.addAll(Arrays.asList(51, 52));
				case 54 -> {
					neighbor.addAll(Arrays.asList(37, 55));
					seaNeighbor.addAll(Arrays.asList(8, 35, 36));
				}
				case 55 -> neighbor.addAll(Arrays.asList(54, 38, 56));
				case 56 -> neighbor.add(55);
			}
			new Tile(i, country, neighbor, seaNeighbor);
		}
	}

	private Tile(int index, String ownerCountry, ArrayList<Integer> neighbors, ArrayList<Integer> seaNeighbors) {
		this.ownerCountry = ownerCountry;
		this.initialOwnerCountry = ownerCountry;
		this.factories = new ArrayList<>();
		this.terrain = Terrain.PLAIN;
		this.weather = Weather.SUNNY;
		this.neighbors = neighbors;
		this.seaNeighbors = seaNeighbors;
		this.battalions = new ArrayList<>();
		tileMap.put(index, this);
	}

	public static Tile getTileByIndex(int index) {
		if (index <= 56 && index >= 1) return tileMap.get(index);
		else return null;
	}

	public Player getOwnerPlayer() {
		return Game.currentGame.getCountryOwner(this.getOwnerCountry());
	}

	public boolean amINotTheOwner() {
		Player owner = this.getOwnerPlayer();
		return !(owner != null && owner.equals(Game.currentGame.getCurrentPlayer()));
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public List<Factory> getFactories() {
		return factories;
	}

	public void addFactory(Factory factory) {
		this.factories.add(factory);
	}

	public void deleteFactoryByName(String name) {
		factories.removeIf(factory -> factory.getName().equals(name));
	}

	public List<Integer> getNeighbors() {
		return neighbors;
	}

	public List<Integer> getSeaNeighbors() {
		return seaNeighbors;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		Tile.terrainSet.add(this);
	}

	public boolean isTerrainSet() {
		return Tile.terrainSet.contains(this);
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public ArrayList<Battalion> getBattalions() {
		return battalions;
	}

	public void addBattalion(Battalion battalion) {
		this.battalions.add(battalion);
	}

	public Country getInitialOwner() {
		return Country.getCountryByName(initialOwnerCountry);
	}
}

