package View;

import Controller.LoginMenuController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class LoginMenu extends Menu {
	private final List<Command> commands = new ArrayList<>();

	LoginMenu() {
		commands.add(new Command("Show Current Menu", "^\\s*show\\s+current\\s+menu\\s*$"));
		commands.add(new Command("login", "^\\s*login\\s+-username\\s+(\\S+)\\s+-password\\s+(.+?)\\s*$"));
		commands.add(new Command("forget password", "^\\s*forget-password\\s+-username\\s+(\\S+)\\s+-email\\s+(\\S+)\\s*$"));
		commands.add(new Command("back", "^\\s*back\\s*$"));
		commands.add(new Command("exit", "^\\s*exit\\s*$"));
	}

	@Override
	protected CommandResult executeCommands(String input) {
		for (Command command : commands) {
			Matcher matcher = command.match(input);
			if (matcher.matches()) {
				switch (command.name) {
					case "Show Current Menu" -> System.out.println("login menu");
					case "login" -> {
						String username = matcher.group(1);
						String password = matcher.group(2);
						String result = LoginMenuController.login(username, password);
						System.out.println(result);
						if (result.equals(LoginMenuController.validLoginMessage)) {
							return CommandResult.GoToMain;
						}
					}
					case "forget password" -> {
						String username = matcher.group(1);
						String email = matcher.group(2);
						System.out.println(LoginMenuController.forgotPassword(username, email));
					}
					case "back" -> {
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