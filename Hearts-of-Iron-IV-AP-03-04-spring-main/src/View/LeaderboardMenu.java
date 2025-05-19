package View;

import Controller.LeaderboardMenuController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class LeaderboardMenu extends Menu {
	private final List<Command> commands = new ArrayList<>();

	LeaderboardMenu() {
		commands.add(new Command("Show Current Menu", "^\\s*show\\s+current\\s+menu\\s*$"));
		commands.add(new Command("show ranking", "^\\s*show\\s+ranking\\s*$"));
		commands.add(new Command("show history", "^\\s*show\\s+history\\s*$"));
		commands.add(new Command("back", "^\\s*back\\s*$"));
		commands.add(new Command("exit", "^\\s*exit\\s*$"));
	}

	@Override
	protected CommandResult executeCommands(String input) {
		for (Command command : commands) {
			Matcher matcher = command.match(input);
			if (matcher.matches()) {
				switch (command.name) {
					case "Show Current Menu" -> System.out.println("leaderboard menu");
					case "show ranking" -> System.out.println(LeaderboardMenuController.showRanking());
					case "show history" -> System.out.println(LeaderboardMenuController.showHistory());
					case "back" -> {
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
}