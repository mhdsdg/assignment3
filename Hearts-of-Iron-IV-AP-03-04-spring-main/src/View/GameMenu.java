package View;

import Controller.GameMenuController;

import java.util.*;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
	private final List<Command> commands = new ArrayList<>();

	GameMenu() {
		commands.add(new Command("Show Current Menu", "^\\s*show\\s+current\\s+menu\\s*$"));
		commands.add(new Command("Switch Player", "^\\s*switch\\s+player\\s+(\\S+)\\s*$"));
		commands.add(new Command("Show Detail", "^\\s*show\\s+detail\\s+(.+?)\\s*$"));
		commands.add(new Command("Tile Owner", "^\\s*tile\\s+owner\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Tile Neighbors", "^\\s*tile\\s+neighbors\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Tile Sea Neighbors", "^\\s*tile\\s+sea\\s+neighbors\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Show Weather", "^\\s*show\\s+weather\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Show Terrain", "^\\s*show\\s+terrain\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Show Battalions", "^\\s*show\\s+battalions\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Show Factories", "^\\s*show\\s+factories\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Set Terrain", "^\\s*set\\s+terrain\\s+(-?\\d+)\\s+(.+?)\\s*$"));
		commands.add(new Command("Set Weather", "^\\s*set\\s+weather\\s+(-?\\d+)\\s+(.+?)\\s*$"));
		commands.add(new Command("Add Battalion", "^\\s*add\\s+battalion\\s+(-?\\d+)\\s+(.+?)\\s+(.+?)\\s*$"));
		commands.add(new Command("Move Battalion", "^\\s*move\\s+battalion\\s+(-?\\d+)\\s+(.+?)\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Upgrade Battalion", "^\\s*upgrade\\s+battalion\\s+(-?\\d+)\\s+(.+?)\\s*$"));
		commands.add(new Command("Create Faction", "^\\s*create\\s+faction\\s+(.+?)\\s*$"));
		commands.add(new Command("Join Faction", "^\\s*join\\s+faction\\s+(.+?)\\s*$"));
		commands.add(new Command("Leave Faction", "^\\s*leave\\s+faction\\s+(.+?)\\s*$"));
		commands.add(new Command("Build Factory", "^\\s*build\\s+factory\\s+(-?\\d+)\\s+(.+?)\\s+(\\S+)\\s*$"));
		commands.add(new Command("Run Factory", "^\\s*run\\s+factory\\s+(-?\\d+)\\s+(.+?)\\s+(-?\\d+)\\s*$"));
		commands.add(new Command("Attack", "^\\s*attack\\s+(-?\\d+)\\s+(-?\\d+)\\s+(.+?)\\s*$"));
		commands.add(new Command("Start Civil War", "^\\s*start\\s+civil\\s+war\\s+(-?\\d+)\\s+(-?\\d+)\\s+(.+?)\\s*$"));
		commands.add(new Command("Puppet", "^\\s*puppet\\s+(.+?)\\s*$"));
		commands.add(new Command("Start Election", "^\\s*start\\s+election\\s*$"));
		commands.add(new Command("back", "^\\s*sadagha\\s+allah\\s+ol\\s+aliol\\s+azim\\s*$"));
		commands.add(new Command("exit", "^\\s*exit\\s*$"));
	}

	@Override
	protected CommandResult executeCommands(String input) {
		for (Command command : commands) {
			Matcher matcher = command.match(input);
			if (matcher.matches()) {
				if (!command.name.equals("Switch Player") && !command.name.equals("Start Election") && !command.name.equals("back") && !command.name.equals("exit") && GameMenuController.amILock()) {
					System.out.println("game is locked");
					return CommandResult.GiveMeNextCommand;
				}
				switch (command.name) {
					case "Show Current Menu" -> System.out.println("game menu");
					case "Switch Player" -> {
						String username = matcher.group(1);
						System.out.println(GameMenuController.switchPlayer(username));
					}
					case "Show Detail" -> {
						String countryName = matcher.group(1);
						System.out.println(GameMenuController.showDetail(countryName));
					}
					case "Tile Owner" -> {
						int index = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.tileOwner(index));
					}
					case "Tile Neighbors" -> {
						int index = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.tileNeighbors(index));
					}
					case "Tile Sea Neighbors" -> {
						int index = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.tileSeaNeighbors(index));
					}
					case "Show Weather" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.showWeather(tileIndex));
					}
					case "Show Terrain" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.showTerrain(tileIndex));
					}
					case "Show Battalions" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.showBattalions(tileIndex));
					}
					case "Show Factories" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						System.out.println(GameMenuController.showFactories(tileIndex));
					}
					case "Set Terrain" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String terrainName = matcher.group(2);
						System.out.println(GameMenuController.setTerrain(tileIndex, terrainName));
					}
					case "Set Weather" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String weatherName = matcher.group(2);
						System.out.println(GameMenuController.setWeather(tileIndex, weatherName));
					}
					case "Add Battalion" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String battalionType = matcher.group(2);
						String name = matcher.group(3);
						System.out.println(GameMenuController.addBattalion(tileIndex, battalionType, name));
					}
					case "Move Battalion" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String battalionName = matcher.group(2);
						int destinationIndex = Integer.parseInt(matcher.group(3));
						System.out.println(GameMenuController.moveBattalion(tileIndex, battalionName, destinationIndex));
					}
					case "Upgrade Battalion" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String battalionName = matcher.group(2);
						System.out.println(GameMenuController.upgradeBattalion(tileIndex, battalionName));
					}
					case "Create Faction" -> {
						String name = matcher.group(1);
						System.out.println(GameMenuController.createFaction(name));
					}
					case "Join Faction" -> {
						String factionName = matcher.group(1);
						System.out.println(GameMenuController.joinFaction(factionName));
					}
					case "Leave Faction" -> {
						String factionName = matcher.group(1);
						System.out.println(GameMenuController.leaveFaction(factionName));
					}
					case "Build Factory" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String factoryType = matcher.group(2);
						String name = matcher.group(3);
						System.out.println(GameMenuController.buildFactory(tileIndex, factoryType, name));
					}
					case "Run Factory" -> {
						int tileIndex = Integer.parseInt(matcher.group(1));
						String name = matcher.group(2);
						int manPowerCount = Integer.parseInt(matcher.group(3));
						System.out.println(GameMenuController.runFactory(tileIndex, name, manPowerCount));
					}
					case "Attack" -> {
						int ourTileIndex = Integer.parseInt(matcher.group(1));
						int enemyTileIndex = Integer.parseInt(matcher.group(2));
						String battalionType = matcher.group(3);
						System.out.println(GameMenuController.attack(ourTileIndex, enemyTileIndex, battalionType));
					}
					case "Start Civil War" -> {
						int tile1 = Integer.parseInt(matcher.group(1));
						int tile2 = Integer.parseInt(matcher.group(2));
						String battalionType = matcher.group(3);
						System.out.println(GameMenuController.startCivilWar(tile1, tile2, battalionType));
					}
					case "Puppet" -> {
						String countryName = matcher.group(1);
						System.out.println(GameMenuController.puppet(countryName));
					}
					case "Start Election" -> GameMenuController.startElection();

					case "back" -> {
						GameMenuController.endGame();
						return CommandResult.GoToMain;
					}
					case "exit" -> {
						return CommandResult.Exit;
					}
				}
				return CommandResult.GiveMeNextCommand;
			}
		}
		System.out.println("invalid command");
		return CommandResult.GiveMeNextCommand;
	}

	public static String scan() {
		while (true) {
			String text = mainScanner.nextLine().trim();
			if (text.equals("exit")) System.exit(0);
			else if (text.matches("^\\s*sadagha\\s+allah\\s+ol\\s+aliol\\s+azim\\s*$")) {
				GameMenuController.endGame();
				new MainMenu().getCommands(MainMenu.mainScanner);
			} else return text;
		}
	}

	public static void println(String text) {
		System.out.println(text);
	}
}

