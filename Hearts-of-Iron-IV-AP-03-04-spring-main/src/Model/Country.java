package Model;

import java.util.*;

public class Country {
	private final String name;
	private Leader leader;
	private int stability;
	private int manPower;
	private int fuel;
	private int steel;
	private int sulfur;
	private final List<Country> puppetCountries;
	private final List<Faction> factions;

	private static Map<String, Country> countries = new HashMap<>();

	public static void initialize() {
		countries = new HashMap<>();
		new Country("German Reich");
		new Country("Soviet Union");
		new Country("United States");
		new Country("United Kingdom");
		new Country("Japan");
		Tile.initialize();
	}

	private Country(String name) {
		this.name = name;
		this.stability = 100;
		this.puppetCountries = new ArrayList<>();
		this.factions = new ArrayList<>();
		this.leader = Leader.getDefaultLeader(name);

		switch (name) {
			case "German Reich" -> {
				this.manPower = 60000000;
				this.fuel = 100000;
				this.sulfur = 200000;
				this.steel = 300000;
			}
			case "Soviet Union" -> {
				this.manPower = 160000000;
				this.fuel = 300000;
				this.sulfur = 50000;
				this.steel = 100000;
			}
			case "United States" -> {
				this.manPower = 120000000;
				this.fuel = 200000;
				this.sulfur = 100000;
				this.steel = 200000;
			}
			case "United Kingdom" -> {
				this.manPower = 30000000;
				this.fuel = 0;
				this.sulfur = 1;
				this.steel = 10;
			}
			case "Japan" -> {
				this.manPower = 70000000;
				this.fuel = 50000;
				this.sulfur = 50000;
				this.steel = 50000;
			}
			default -> throw new IllegalArgumentException("Country '" + name + "' is not recognized!");
		}

		countries.put(name, this);
	}


	public static Country getCountryByName(String name) {
		return countries.get(name);
	}

	public String getName() {
		return name;
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = Math.min(stability, 100);
	}

	public int getManPower() {
		return manPower;
	}

	public void setManPower(int manPower) {
		this.manPower = manPower;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getSteel() {
		return steel;
	}

	public void setSteel(int steel) {
		this.steel = steel;
	}

	public int getSulfur() {
		return sulfur;
	}

	public void setSulfur(int sulfur) {
		this.sulfur = sulfur;
	}

	public List<Country> getPuppetCountries() {
		return puppetCountries;
	}

	public void addToPuppetCountries(Country puppetCountry) {
		this.puppetCountries.add(puppetCountry);
	}

	public List<Faction> getFactions() {
		return factions;
	}

	public void addToFactions(Faction faction) {
		this.factions.add(faction);
	}

	public void removeFromFactions(Faction faction) {
		this.factions.remove(faction);
	}
}

