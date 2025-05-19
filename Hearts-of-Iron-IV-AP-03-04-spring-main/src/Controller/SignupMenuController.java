package Controller;

import Model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupMenuController {
	public static String userRegistered = "user registered successfully";

	public static String register(String username, String password, String email) {
		if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) return "invalid username";
		if (User.getUserByName(username) != null) return "username is already taken";

		if (password.length() < 8 || password.length() > 20) return "invalid length";
		if (password.contains(" ")) return "don't use whitespace in password";

		if (!password.matches("^[a-zA-Z].*")) return "password must start with English letters";

		if (!password.matches(".*[%@#$^&!].*")) return "password doesn't have special characters";

		String emailRegex = "^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?@[a-zA-Z0-9]+\\.com$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.matches()) return "invalid email format";

		new User(username, password, email);
		return userRegistered;
	}
}
