package View;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("StaticInitializerReferencesSubClass")
abstract class Menu {
	private static final MainMenu mainMenu = new MainMenu();
	private static final LoginMenu loginMenu = new LoginMenu();
	private static final SignupMenu signupMenu = new SignupMenu();
	private static final GameMenu gameMenu = new GameMenu();
	private static final LeaderboardMenu leaderboardMenu = new LeaderboardMenu();
	protected static Scanner mainScanner;

	public void getCommands(Scanner scanner) {
		if (mainScanner == null) mainScanner = scanner;
		CommandResult result;
		do {
			String command = scanner.nextLine();
			result = executeCommands(command.trim());
		} while (result == CommandResult.GiveMeNextCommand);
		switch (result) {
			case Exit -> System.exit(0);
			case GoToSignup -> signupMenu.getCommands(scanner);
			case GoToGame -> gameMenu.getCommands(scanner);
			case GoToLeaderboard -> leaderboardMenu.getCommands(scanner);
			case GoToLogin -> loginMenu.getCommands(scanner);
			case GoToMain -> mainMenu.getCommands(scanner);
		}
	}

	abstract CommandResult executeCommands(String command);

	static class Command {
		String name;
		String regex;
		Pattern pattern;

		// Constructor
		public Command(String name, String regex) {
			this.name = name;
			this.regex = regex;
			this.pattern = Pattern.compile(regex);
		}

		public Matcher match(String input) {
			return pattern.matcher(input);
		}
	}
}





