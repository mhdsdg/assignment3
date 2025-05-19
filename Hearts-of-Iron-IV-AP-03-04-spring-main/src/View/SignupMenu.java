package View;

import Controller.SignupMenuController;

import java.util.*;
import java.util.regex.Matcher;

class SignupMenu extends Menu {
	private final List<Command> commands = new ArrayList<>();

	SignupMenu() {
		commands.add(new Command("Show Current Menu", "^\\s*show\\s+current\\s+menu\\s*$"));
		commands.add(new Command("Register", "^\\s*register\\s+-username\\s+(\\S+)\\s+-password\\s+(.+?)\\s+-email\\s+(\\S+)\\s*$"));
		commands.add(new Command("login", "^\\s*login\\s*$"));
		commands.add(new Command("exit", "^\\s*exit\\s*$"));
	}

	@Override
	protected CommandResult executeCommands(String input) {
		for (Command command : commands) {
			Matcher matcher = command.match(input);
			if (matcher.matches()) {
				switch (command.name) {
					case "Show Current Menu" -> System.out.println("signup menu");
					case "Register" -> {
						String username = matcher.group(1);
						String password = matcher.group(2);
						String email = matcher.group(3);
						String result = SignupMenuController.register(username, password, email);
						System.out.println(result);
						if (result.equals(SignupMenuController.userRegistered)) return CommandResult.GoToLogin;
						return CommandResult.GiveMeNextCommand;
					}
					case "login" -> {
						return CommandResult.GoToLogin;
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