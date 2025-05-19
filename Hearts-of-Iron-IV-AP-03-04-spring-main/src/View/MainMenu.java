package View;

import Controller.GameMenuController;
import Controller.MainMenuController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class MainMenu extends Menu {
	private final List<Command> commands = new ArrayList<>();

	MainMenu() {
		commands.add(new Command("Show Current Menu", "^\\s*show\\s+current\\s+menu\\s*$"));
		commands.add(new Command("leaderboard", "^\\s*leaderboard\\s*$"));
		commands.add(new Command("play", "^\\s*play(?:\\s+(\\S+))?(?:\\s+(\\S+))?(?:\\s+(\\S+))?(?:\\s+(\\S+))?\\s*$"));
		commands.add(new Command("logout", "^\\s*logout\\s*$"));
		commands.add(new Command("exit", "^\\s*exit\\s*$"));
	}

	@Override
	protected CommandResult executeCommands(String input) {
		for (Command command : commands) {
			Matcher matcher = command.match(input);
			if (matcher.matches()) {
				switch (command.name) {
					case "Show Current Menu" -> System.out.println("main menu");
					case "leaderboard" -> {
						return CommandResult.GoToLeaderboard;
					}
					case "play" -> {
						ArrayList<String> users = new ArrayList<>();
						for (int i = 1; i <= 4; i++)
							if (matcher.group(i) != null) users.add(matcher.group(i));
						String result = MainMenuController.play(users);
						System.out.println(result);
						if (result.equals(MainMenuController.validPlayMessage)) {
							GameMenuController.selectCountries(users);
							return CommandResult.GoToGame;
						}
					}
					case "logout" -> {
						return CommandResult.GoToSignup;
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
