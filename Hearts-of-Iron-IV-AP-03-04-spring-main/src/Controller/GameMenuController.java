package Controller;

import Model.*;
import View.GameMenu;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class GameMenuController {
	private static final HashSet<String> availableCountries = new HashSet<>() {{
		add("German Reich");
		add("United States");
		add("Soviet Union");
		add("Japan");
		add("United Kingdom");
	}};

	private static void correctUsernamesList(ArrayList<String> usernames) {
		usernames.add(0, User.getLoggedInUser().getName());
		int guestNumber = 1;
		while (usernames.size() < 5) {
			String guestName = "guest" + guestNumber;
			if (!usernames.contains(guestName)) {
				usernames.add(guestName);
				new User(guestName, "temp!", "temp!");
			}
			guestNumber++;
		}
	}

	public static void selectCountries(ArrayList<String> usernames) {
		correctUsernamesList(usernames);
		final HashMap<String, String> playerCountries = new HashMap<>();
		int totalPlayers = 5;
		for (int i = 0; i < totalPlayers; i++) {
			GameMenu.println("choosing country for " + usernames.get(i) + ":");
			while (true) {
				String selectedCountry = GameMenu.scan();
				if (!availableCountries.contains(selectedCountry)) GameMenu.println("wrong country name");
				else if (playerCountries.containsValue(selectedCountry)) GameMenu.println("country already taken");
				else {
					playerCountries.put(usernames.get(i), selectedCountry);
					break;
				}
			}
		}
		Country.initialize();
		Game game = new Game();
		for (int i = 0; i < 5; i++)
			game.addPlayer(usernames.get(i), playerCountries.get(usernames.get(i)));
		game.setCurrentPlayer(usernames.get(0));
		game.setOrderedUsernames(usernames);
	}

	public static String switchPlayer(String username) {
		if (!Game.currentGame.getPlayers().containsKey(username)) {
			return "player doesn't exist";
		} else if (Game.currentGame.getCurrentPlayerUsername().equals(username)) {
			return "you can't switch to yourself";
		} else {
			Game.currentGame.setCurrentPlayer(username);
		}
		return "switched to " + username;
	}

	public static String showDetail(String countryName) {
		if (!availableCountries.contains(countryName)) return "country doesn't exist";
		Country country = Country.getCountryByName(countryName);
		StringBuilder details = new StringBuilder();
		details.append(country.getName()).append("\n");
		details.append("leader : ");
		Leader leader = country.getLeader();
		if (leader != null) details.append(leader.getName().toLowerCase());
		details.append("\n");
		details.append("stability : ").append(country.getStability()).append("\n");
		details.append("man power : ").append(country.getManPower()).append("\n");
		details.append("fuel : ").append(country.getFuel()).append("\n");
		details.append("sulfur : ").append(country.getSulfur()).append("\n");
		details.append("steel : ").append(country.getSteel()).append("\n");
		details.append("faction :");
		List<Faction> factions = country.getFactions();
		if (!factions.isEmpty()) details.append(" ");
		for (int i = 0; i < factions.size(); i++) {
			details.append(factions.get(i).getName().toLowerCase());
			if (i + 1 < factions.size()) details.append(",");
		}
		details.append("\n").append("puppet :");
		List<Country> puppets = country.getPuppetCountries();
		if (!puppets.isEmpty()) details.append(" ");
		for (int i = 0; i < puppets.size(); i++) {
			details.append(puppets.get(i).getName());
			if (i + 1 < puppets.size()) details.append(",");
		}
		if (details.length() > 0 && details.charAt(details.length() - 1) == '\n')
			details.deleteCharAt(details.length() - 1);
		return details.toString();
	}

	public static String tileOwner(int index) {
		Tile tile = Tile.getTileByIndex(index);
		if (tile == null) return "tile doesn't exist";
		return tile.getOwnerCountry();
	}

	public static String tileNeighbors(int index) {
		Tile tile = Tile.getTileByIndex(index);
		if (tile == null) return "tile doesn't exist";
		List<Integer> neighbors = Objects.requireNonNull(Tile.getTileByIndex(index)).getNeighbors();
		return sortList(neighbors);
	}

	public static String tileSeaNeighbors(int index) {
		Tile tile = Tile.getTileByIndex(index);
		if (tile == null) return "tile doesn't exist";
		List<Integer> seaNeighbors = Objects.requireNonNull(Tile.getTileByIndex(index)).getSeaNeighbors();
		if (seaNeighbors.isEmpty()) return "no sea neighbors";
		return sortList(seaNeighbors);
	}

	private static String sortList(List<Integer> list) {
		Collections.sort(list);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			builder.append(list.get(i));
			if (i + 1 < list.size()) builder.append(" , ");
		}
		if (builder.length() > 0 && builder.charAt(builder.length() - 1) == '\n')
			builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	public static String showWeather(int tileIndex) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile doesn't exist";
		return tile.getWeather().name().toLowerCase();
	}

	public static String showTerrain(int tileIndex) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile doesn't exist";
		return tile.getTerrain().name().toLowerCase();
	}

	private static boolean checkNotHaveAccess(int tileIndex) {
		String tileCountry = Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getOwnerCountry();
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		if (tileCountry.equals(currentCountry.getName())) return false;
		for (Country country : currentCountry.getPuppetCountries())
			if (tileCountry.equals(country.getName())) return false;
		for (Faction faction : currentCountry.getFactions())
			for (String countryName : faction.getMembers())
				if (tileCountry.equals(countryName)) return false;
		return true;
	}

	private static class BattalionComparator implements Comparator<Battalion> {
		@Override
		public int compare(Battalion e1, Battalion e2) {
			return e1.getName().compareToIgnoreCase(e2.getName());
		}
	}

	private static void printBattalions(ArrayList<Battalion> battalions, StringBuilder details) {
		for (Battalion battalion : battalions) {
			details.append(battalion.getName()).append(" ");
			details.append(battalion.getLevel()).append(" ");
			details.append(battalion.getPower()).append(" ");
			details.append(battalion.getCaptureRatio()).append("\n");
		}
		if (details.length() > 0 && details.charAt(details.length() - 1) == '\n')
			details.deleteCharAt(details.length() - 1);
	}

	public static String showBattalions(int tileIndex) {
		if (Tile.getTileByIndex(tileIndex) == null) return "tile doesn't exist";
		if (checkNotHaveAccess(tileIndex)) return "can't show battalions";
		ArrayList<Battalion> battalions = Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getBattalions();
		ArrayList<Battalion> infantry = new ArrayList<>();
		ArrayList<Battalion> panzer = new ArrayList<>();
		ArrayList<Battalion> airForce = new ArrayList<>();
		ArrayList<Battalion> navy = new ArrayList<>();
		for (Battalion battalion : battalions) {
			if (battalion.getType() == Battalion.BattalionType.INFANTRY) infantry.add(battalion);
			else if (battalion.getType() == Battalion.BattalionType.PANZER) panzer.add(battalion);
			else if (battalion.getType() == Battalion.BattalionType.AIR_FORCE) airForce.add(battalion);
			else navy.add(battalion);
		}

		infantry.sort(new BattalionComparator());
		panzer.sort(new BattalionComparator());
		airForce.sort(new BattalionComparator());
		navy.sort(new BattalionComparator());

		StringBuilder details = new StringBuilder();
		details.append("infantry:\n");
		printBattalions(infantry, details);
		details.append("\n");
		details.append("\npanzer:\n");
		printBattalions(panzer, details);
		details.append("\n");
		details.append("\nairforce:\n");
		printBattalions(airForce, details);
		details.append("\n");
		details.append("\nnavy:\n");
		printBattalions(navy, details);
		details.append("\n");
		if (details.length() > 0 && details.charAt(details.length() - 1) == '\n')
			details.deleteCharAt(details.length() - 1);
		return details.toString();
	}

	public static String showFactories(int tileIndex) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile doesn't exist";
		if (checkNotHaveAccess(tileIndex)) return "can't show factories";
		List<Factory> factories = tile.getFactories();
		Map<FactoryType, List<Factory>> categorizedFactories = factories.stream().collect(Collectors.groupingBy(Factory::getType));
		FactoryType[] factoryOrder = {FactoryType.FUEL_REFINERY, FactoryType.STEEL_FACTORY, FactoryType.SULFUR_FACTORY};
		StringBuilder result = new StringBuilder();
		for (FactoryType type : factoryOrder) {
			List<Factory> factoriesOfType = categorizedFactories.getOrDefault(type, new ArrayList<>());
			factoriesOfType.sort(Comparator.comparing(Factory::getName));

			result.append(switch (type) {
				case STEEL_FACTORY -> "steel factory";
				case FUEL_REFINERY -> "fuel refinery";
				case SULFUR_FACTORY -> "sulfur factory";
			}).append(":\n");

			for (Factory factory : factoriesOfType) {
				result.append(factory.getName()).append(" ").append(factory.getProductionLeft(tileIndex)).append("\n");
			}
			result.append("\n");
		}
		if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') result.deleteCharAt(result.length() - 1);
		if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	public static String setTerrain(int tileIndex, String terrainName) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "you don't own this tile";
		if (tile.amINotTheOwner()) return "you don't own this tile";
		Terrain terrain = Terrain.getTerrain(terrainName);
		if (terrain == null) return "terrain doesn't exist";
		if (tile.isTerrainSet()) return "you can't change terrain twice";
		tile.setTerrain(terrain);
		return "terrain set successfully";
	}

	public static String setWeather(int tileIndex, String weatherName) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile doesn't exist";
		if (tile.amINotTheOwner()) return "you don't own this tile";
		Weather weather = Weather.getWeather(weatherName);
		if (weather == null) return "weather doesn't exist";
		tile.setWeather(weather);
		return "weather set successfully";
	}

	public static String addBattalion(int tileIndex, String battalionType, String name) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile is unavailable";
		if (checkNotHaveAccess(tileIndex)) return "tile is unavailable";
		Battalion.BattalionType type = Battalion.getBattalionTypeFromString(battalionType);
		if (type == null) return "you can't use imaginary battalions";
		for (Battalion btl : tile.getBattalions())
			if (btl.getName().equals(name)) return "battalion name already taken";
		Map<String, Integer> cost = Battalion.getCreationCost(type);
		Country country = Game.currentGame.getCurrentPlayer().getCountry();
		int mult = 1;
		if (country.getLeader().getIdeology().equals(Ideology.DEMOCRACY)) mult = 2;
		if (cost.get("Fuel") * mult > country.getFuel() || cost.get("Steel") * mult > country.getSteel() || cost.get("Sulfur") * mult > country.getSulfur() || cost.get("Man Power") * mult > country.getManPower())
			return "daddy USA plz help us";
		int count = 0;
		for (Battalion btl : tile.getBattalions()) {
			if (btl.getType().equals(type)) count += 1;
		}
		if (count >= 3) return "you can't add this type of battalion anymore";
		Battalion newBtl = new Battalion(name, type, tile.getOwnerCountry());
		country.setFuel(country.getFuel() - cost.get("Fuel") * mult);
		country.setSteel(country.getSteel() - cost.get("Steel") * mult);
		country.setSulfur(country.getSulfur() - cost.get("Sulfur") * mult);
		country.setManPower(country.getManPower() - cost.get("Man Power") * mult);
		tile.addBattalion(newBtl);
		return "battalion set successfully";
	}

	public static String moveBattalion(int tileIndex, String battalionName, int destinationIndex) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "tile is unavailable";
		if (checkNotHaveAccess(tileIndex)) return "tile is unavailable";
		Tile dest = Tile.getTileByIndex(destinationIndex);
		if (dest == null) return "tile is unavailable";
		if (checkNotHaveAccess(destinationIndex)) return "tile is unavailable";
		Battalion btl = null;
		for (Battalion meow : tile.getBattalions()) {
			if (meow.getName().equals(battalionName)) {
				btl = meow;
			}
		}
		if (btl == null) return "no battalion with the given name";
		int count = 0;
		for (Battalion meow : dest.getBattalions()) {
			if (meow.getType().equals(btl.getType())) count += 1;
		}
		if (count >= 3) return "maximum battalion of this type in destination exists";
		for (Battalion meow : dest.getBattalions()) {
			if (meow.getName().equals(btl.getName())) return "battalion name is already taken in this tile";
		}
		tile.getBattalions().remove(btl);
		dest.getBattalions().add(btl);
		return "battalion moved successfully";
	}

	public static String upgradeBattalion(int tileIndex, String battalionName) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) return "can't upgrade battalions on this tile";
		if (checkNotHaveAccess(tileIndex)) return "can't upgrade battalions on this tile";
		Battalion btl = null;
		for (Battalion meow : tile.getBattalions()) {
			if (meow.getName().equals(battalionName)) btl = meow;
		}
		if (btl == null) return "no battalion with the given name";
		if (btl.getLevel() >= 3) return "battalion is on highest level";
		Country country = Game.currentGame.getCurrentPlayer().getCountry();
		double mult = switch (btl.getLevel()) {
			case 0 -> 0.5;
			case 1 -> 1;
			case 2 -> 2;
			default -> throw new RuntimeException("Wtf btl > 3?");
		};
		if (country.getLeader().getIdeology().equals(Ideology.DEMOCRACY)) mult *= 2;
		Map<String, Integer> cost = Battalion.getCreationCost(btl.getType());

		if (((int) Math.floor(cost.get("Fuel") * mult)) > country.getFuel() || ((int) Math.floor(cost.get("Steel") * mult)) > country.getSteel() || ((int) Math.floor(cost.get("Sulfur") * mult)) > country.getSulfur() || ((int) Math.floor(cost.get("Man Power") * mult)) > country.getManPower())
			return "aww you can't upgrade your battalion";
		country.setFuel(country.getFuel() - ((int) Math.floor(cost.get("Fuel") * mult)));
		country.setSteel(country.getSteel() - ((int) Math.floor(cost.get("Steel") * mult)));
		country.setSulfur(country.getSulfur() - ((int) Math.floor(cost.get("Sulfur") * mult)));
		country.setManPower(country.getManPower() - ((int) Math.floor(cost.get("Man Power") * mult)));
		btl.upgrade();
		return btl.getName() + " upgraded to level " + btl.getLevel();
	}

	public static String createFaction(String name) {
		if (Faction.factionExists(name)) return "faction name already taken";
		new Faction(name);
		return "faction created successfully";
	}

	public static String joinFaction(String factionName) {
		if (!Faction.factionExists(factionName)) return "faction doesn't exist";
		Country country = Game.currentGame.getCurrentPlayer().getCountry();
		String countryName = country.getName();
		Faction faction = Faction.getFaction(factionName);
		faction.join(countryName);
		country.addToFactions(faction);
		return countryName + " joined " + factionName;
	}

	public static String leaveFaction(String factionName) {
		if (!Faction.factionExists(factionName)) return "faction doesn't exist";
		Country country = Game.currentGame.getCurrentPlayer().getCountry();
		String countryName = country.getName();
		Faction faction = Faction.getFaction(factionName);
		faction.leave(countryName);
		country.removeFromFactions(faction);
		return countryName + " left " + factionName;
	}

	public static String buildFactory(int tileIndex, String factoryType, String name) {
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null || checkNotHaveAccess(tileIndex)) return "invalid tile";
		FactoryType type;
		switch (factoryType) {
			case "steel factory":
				type = FactoryType.STEEL_FACTORY;
				break;
			case "sulfur factory":
				type = FactoryType.SULFUR_FACTORY;
				break;
			case "fuel refinery":
				type = FactoryType.FUEL_REFINERY;
				break;
			default:
				return "invalid factory type";
		}

		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		Map<String, Integer> costs = Factory.getAllInfo(type, tileIndex);
		int manpowerCost = costs.get("Manpower Cost");
		int steelCost = costs.get("Steel Cost");
		if (currentCountry.getManPower() < manpowerCost || currentCountry.getSteel() < steelCost) {
			return "not enough money to build factory";
		}

		int count = (int) tile.getFactories().stream().filter(factory -> factory.getType() == type).count();
		if (count >= 3) return "factory limit exceeded";

		currentCountry.setManPower(currentCountry.getManPower() - (costs.get("Manpower Cost")));
		currentCountry.setSteel(currentCountry.getSteel() - (costs.get("Steel Cost")));
		tile.addFactory(new Factory(name, type));

		return "factory built successfully";
	}


	public static String runFactory(int tileIndex, String name, int manPowerCount) {
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null || checkNotHaveAccess(tileIndex)) return "invalid tile";

		Factory factory = tile.getFactories().stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);

		if (factory == null) return "this tile doesn't contain this factory";
		if (currentCountry.getManPower() < manPowerCount) return "you are poor!";

		currentCountry.setManPower(currentCountry.getManPower() - manPowerCount);
		Map<String, Integer> info = Factory.getAllInfo(factory.getType(), tileIndex);
		int production = manPowerCount * info.get("Production Per Manpower");
		if (production >= factory.getProductionLeft(tileIndex)) {
			production = factory.getProductionLeft(tileIndex);
			tile.deleteFactoryByName(name);
		}
		String type = switch (factory.getType()) {
			case STEEL_FACTORY -> "steel";
			case FUEL_REFINERY -> "fuel";
			case SULFUR_FACTORY -> "sulfur";
		};
		ArrayList<Country> profitable = new ArrayList<>();
		profitable.add(currentCountry);
		for (Faction faction : currentCountry.getFactions())
			for (String countryName : faction.getMembers())
				if (Country.getCountryByName(countryName).getLeader().getIdeology() == Ideology.COMMUNISM)
					profitable.add(Country.getCountryByName(countryName));
		for (Country country : profitable) {
			try {
				String getterName = "get" + capitalize(type);
				String setterName = "set" + capitalize(type);
				Method getter = Country.class.getMethod(getterName);
				Method setter = Country.class.getMethod(setterName, int.class);
				int currentValue = (int) getter.invoke(country);
				setter.invoke(country, currentValue + (production / profitable.size()));
			} catch (Exception e) {
				throw new RuntimeException("Error updating country resource", e);
			}
		}
		factory.addToProduction(production);
		return "factory extracted " + production + " of " + type;
	}

	private static boolean checkMyOwnership(int tileIndex) {
		String tileCountry = Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getOwnerCountry();
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		return tileCountry.equals(currentCountry.getName());
	}

	private static boolean checkPuppet(int tileIndex) {
		String tileCountry = Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getOwnerCountry();
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		for (Country country : currentCountry.getPuppetCountries())
			if (tileCountry.equals(country.getName())) return true;
		return false;
	}

	private static boolean checkFaction(int tileIndex) {
		String tileCountry = Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getOwnerCountry();
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		for (Faction faction : currentCountry.getFactions())
			for (String countryName : faction.getMembers())
				if (tileCountry.equals(countryName)) return true;
		return false;
	}

	private static boolean checkPuppeter(int tileIndex) {
		Country tileCountry = Country.getCountryByName(Objects.requireNonNull(Tile.getTileByIndex(tileIndex)).getOwnerCountry());
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();
		for (Country cnt : tileCountry.getPuppetCountries()) {
			if (cnt.getName().equals(currentCountry.getName())) return true;
		}
		return false;
	}

	private static int calculatePower(int tileIndex, Battalion.BattalionType type) {
		int sum = 0;
		Tile tile = Tile.getTileByIndex(tileIndex);
		if (tile == null) throw new RuntimeException("calculate power boom");
		for (Battalion btl : tile.getBattalions()) {
			if (btl.getType().equals(type)) {
				sum += btl.getPower();
			}
		}
		if (type.equals(Battalion.BattalionType.PANZER) || type.equals(Battalion.BattalionType.INFANTRY)) {
			sum *= tile.getTerrain().attack;
			sum /= 100;
			sum *= tile.getWeather().attack;
			sum /= 100;
		} else if (type.equals(Battalion.BattalionType.AIR_FORCE)) {
			sum *= tile.getTerrain().airAttack;
			sum /= 100;
			sum *= tile.getWeather().airAttack;
			sum /= 100;
		}
		return sum;
	}

	private static ArrayList<Battalion> removeBattalions(Tile tile, Battalion.BattalionType type) {
		ArrayList<Battalion> toBeRemoved = new ArrayList<>();
		for (Battalion btl : tile.getBattalions()) {
			if (btl.getType().equals(type)) toBeRemoved.add(btl);
		}
		for (Battalion btl : toBeRemoved) {
			tile.getBattalions().remove(btl);
		}
		return toBeRemoved;
	}

	private static void giveCapturedAndRemoveBattalions(Tile myTile, Tile enemyTile, Battalion.BattalionType type) {
		ArrayList<Battalion> enemyBattalions = removeBattalions(enemyTile, type);
		int captured = 0;
		ArrayList<Battalion> myBattalions = new ArrayList<>();
		for (Battalion btl : myTile.getBattalions()) {
			if (btl.getType().equals(type)) myBattalions.add(btl);
		}
		if (!enemyBattalions.isEmpty()) {
			for (Battalion btl : enemyBattalions) {
				captured += btl.getPower() * btl.getCaptureRatio();
			}
			captured /= 100;
			captured /= myBattalions.size();
		}

		for (Battalion btl : myBattalions) {
			btl.setPower(btl.getPower() + captured);
		}
	}

	private static boolean theyAreNotNeighbor(Tile tile, int enemyTileIndex, Battalion.BattalionType type) {
		if (type.equals(Battalion.BattalionType.INFANTRY) || type.equals(Battalion.BattalionType.PANZER)) {
			return !tile.getNeighbors().contains(enemyTileIndex);
		} else return type.equals(Battalion.BattalionType.NAVY) && !tile.getSeaNeighbors().contains(enemyTileIndex);
	}

	public static String attack(int ourTileIndex, int enemyTileIndex, String battalionType) {
		Tile tile = Tile.getTileByIndex(ourTileIndex);
		if (tile == null) return "attacker tile unavailable";
		if (!(checkMyOwnership(ourTileIndex) || checkPuppet(ourTileIndex))) return "attacker tile unavailable";
		Battalion.BattalionType type = Battalion.getBattalionTypeFromString(battalionType);
		if (type == null) return "selected tile doesn't have this type of battalion";
		boolean containsType = false;
		for (Battalion meow : tile.getBattalions()) {
			if (meow.getType().equals(type)) {
				containsType = true;
				break;
			}
		}
		if (!containsType) return "selected tile doesn't have this type of battalion";
		Tile enemyTile = Tile.getTileByIndex(enemyTileIndex);
		if (enemyTile == null) return "enemy tile unavailable for attacking";
		if (checkMyOwnership(enemyTileIndex) || checkPuppeter(enemyTileIndex) || checkFaction(enemyTileIndex))
			return "enemy tile unavailable for attacking";
		if (theyAreNotNeighbor(tile, enemyTileIndex, type)) return "enemy tile unavailable for attacking";
		Country myCountry = Country.getCountryByName(tile.getOwnerCountry());
		Country enemyCountry = Country.getCountryByName(enemyTile.getOwnerCountry());
		Ideology myIdeology = myCountry.getLeader().getIdeology();
		Ideology enemyIdeology = enemyCountry.getLeader().getIdeology();
		if (myIdeology.equals(Ideology.FASCISM) && enemyIdeology.equals(Ideology.FASCISM))
			return "we are rivals , not enemies";

		int myPower = calculatePower(ourTileIndex, type);
		int enemyPower = calculatePower(enemyTileIndex, type);
		int difference = myPower - enemyPower;
		if (difference == 0) {
			removeBattalions(tile, type);
			removeBattalions(enemyTile, type);
			return "draw";
		} else if (difference > 0) {
			Game.currentGame.getCountryOwner(myCountry).addPoints(Math.abs(difference) * 10 * Battalion.getScoreMultiplier(type));
			Game.currentGame.getCountryOwner(enemyCountry).losePoints(Math.abs(difference) * 5 * Battalion.getScoreMultiplier(type));
			myCountry.setStability(myCountry.getStability() * 150 / 100);
			enemyCountry.setStability(enemyCountry.getStability() * 50 / 100);
			enemyTile.setOwnerCountry(tile.getOwnerCountry());
			giveCapturedAndRemoveBattalions(tile, enemyTile, type);
			return "war is over\nwinner : " + myCountry.getName() + "\nloser : " + enemyCountry.getName();
		} else {
			Game.currentGame.getCountryOwner(enemyCountry).addPoints(Math.abs(difference) * 10 * Battalion.getScoreMultiplier(type));
			Game.currentGame.getCountryOwner(myCountry).losePoints(Math.abs(difference) * 5 * Battalion.getScoreMultiplier(type));
			myCountry.setStability(myCountry.getStability() * 50 / 100);
			enemyCountry.setStability(enemyCountry.getStability() * 150 / 100);
			enemyCountry.setFuel(enemyCountry.getFuel() + (difference * 100));
			enemyCountry.setSulfur(enemyCountry.getSulfur() + (difference * 100));
			enemyCountry.setManPower(enemyCountry.getManPower() + (difference * 1000));
			giveCapturedAndRemoveBattalions(enemyTile, tile, type);
			return "war is over\nwinner : " + enemyCountry.getName() + "\nloser : " + myCountry.getName();
		}
	}

	public static String startCivilWar(int tile1Index, int tile2Index, String battalionType) {
		if (Game.currentGame.getCurrentPlayer().getCountry().getLeader().getIdeology().equals(Ideology.DEMOCRACY))
			return "no civil war for you";
		Tile tile1 = Tile.getTileByIndex(tile1Index);
		if (tile1 == null) return "invalid tiles for civil war";
		if (!tile1.getOwnerPlayer().equals(Game.currentGame.getCurrentPlayer())) return "invalid tiles for civil war";
		Tile tile2 = Tile.getTileByIndex(tile2Index);
		if (tile2 == null) return "invalid tiles for civil war";
		if (!tile2.getOwnerPlayer().equals(Game.currentGame.getCurrentPlayer())) return "invalid tiles for civil war";
		Battalion.BattalionType type = Battalion.getBattalionTypeFromString(battalionType);
		if (type == null) return "invalid battalion type";
		boolean hasType = false;
		for (Battalion btl : tile1.getBattalions()) {
			if (btl.getType().equals(type)) {
				hasType = true;
				break;
			}
		}
		if (theyAreNotNeighbor(tile1, tile2Index, type)) return "can't start civil war between these tiles";
		if (!hasType) return "invalid battalion type";
		Country country = Game.currentGame.getCurrentPlayer().getCountry();
		country.setStability((int) Math.floor(country.getStability() * 0.1));
		int power1 = calculatePower(tile1Index, type);
		int power2 = calculatePower(tile2Index, type);
		int difference = power1 - power2;
		if (difference == 0) {
			removeBattalions(tile1, type);
			removeBattalions(tile2, type);
			return "man dige harfi nadaram.";
		} else if (difference > 0) {
			giveCapturedAndRemoveBattalions(tile1, tile2, type);
			return "civil war ended. " + tile1Index + " won.";
		} else {
			giveCapturedAndRemoveBattalions(tile2, tile1, type);
			return "civil war ended. " + tile2Index + " won.";
		}
	}

	public static String puppet(String countryName) {
		if (Country.getCountryByName(countryName) == null) return "country doesn't exist";

		Country destinationCountry = Country.getCountryByName(countryName);
		Country currentCountry = Game.currentGame.getCurrentPlayer().getCountry();

		boolean condition1 = currentCountry.getManPower() > destinationCountry.getManPower();
		boolean condition2 = true;
		for (int i = 1; i <= 56; i++) {
			Tile tile = Tile.getTileByIndex(i);
			assert tile != null;
			if (tile.getInitialOwner() == currentCountry && tile.getOwnerCountry().equals(destinationCountry.getName()))
				condition2 = false;
		}
		boolean condition3 = currentCountry.getLeader().getIdeology() == Ideology.COMMUNISM || currentCountry.getLeader().getIdeology() == Ideology.FASCISM;
		boolean condition4 = true;
		for (Faction faction : currentCountry.getFactions())
			for (String country : faction.getMembers())
				if (country.equals(countryName)) {
					condition4 = false;
					break;
				}

		if (!(condition1 && condition2 && condition3 && condition4))
			return "you are not allowed to puppet this country";

		currentCountry.addToPuppetCountries(destinationCountry);
		return "now " + countryName + " is my puppet yo ho ha ha ha";
	}

	public static void startElection() {
		StringBuilder result = new StringBuilder();
		result.append("Available leaders:\n");
		for (Ideology ideology : List.of(Ideology.DEMOCRACY, Ideology.COMMUNISM, Ideology.FASCISM)) {
			Leader leader = Leader.getLeader(Game.currentGame.getCurrentPlayer().getCountry().getName(), ideology);
			if (leader != null) result.append(leader.getName().toLowerCase()).append("\n");
		}
		if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') result.deleteCharAt(result.length() - 1);
		GameMenu.println(result.toString());
		while (true) {
			Leader foundedLeader = null;
			String name = GameMenu.scan().toLowerCase();
			List<Leader> leaders = Leader.getLeadersForCountry(Game.currentGame.getCurrentPlayer().getCountry().getName());
			boolean found = false;
			for (Leader leader : leaders)
				if (leader.getName().toLowerCase().equals(name)) {
					foundedLeader = leader;
					found = true;
					break;
				}
			if (found) {
				Country country = Country.getCountryByName(Game.currentGame.getCurrentPlayer().getCountry().getName());
				country.setStability(100);
				country.setLeader(foundedLeader);
				return;
			} else GameMenu.println("leader doesn't exist");
		}
	}

	public static void endGame() {
		User.deleteGuests();
		GamesHistory gamesHistory = GamesHistory.getInstance();
		String id = gamesHistory.getAndIncreaseID();
		gamesHistory.addNewGame(id);
		for (String username : Game.currentGame.getOrderedUsernames()) {
			Player player = Game.currentGame.getPlayers().get(username);
			int mult = 1;
			if (player.getCountry().getLeader().getIdeology().equals(Ideology.FASCISM)) mult = 2;
			gamesHistory.addPlayerToGame(id, username, player.getCountry().getName(), player.getPoints() * mult);
			player.getUser().addToPoints(player.getPoints() * mult);
		}
	}

	private static String capitalize(String str) {
		if (str == null || str.isEmpty()) return str;
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}


	public static boolean amILock() {
		int stability = Game.currentGame.getCurrentPlayer().getCountry().getStability();
		Ideology ideology = Game.currentGame.getCurrentPlayer().getCountry().getLeader().getIdeology();
		int acceptValue = switch (ideology) {
			case COMMUNISM -> 60;
			case DEMOCRACY -> 50;
			case FASCISM -> 30;
		};
		return stability < acceptValue;
	}
}
