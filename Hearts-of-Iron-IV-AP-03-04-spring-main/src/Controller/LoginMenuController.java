package Controller;

import Model.User;

public class LoginMenuController {
	public static final String validLoginMessage = "user logged in successfully";

	public static String login(String username, String password) {
		if (User.getUserByName(username) == null) return "username doesn't exist!";

		if (!User.getUserByName(username).validatePassword(password)) return "password is incorrect!";

		User.setLoggedInUser(username);
		return validLoginMessage;
	}

	public static String forgotPassword(String username, String email) {
		if (User.getUserByName(username) == null) return "username doesn't exist!";
		User user = User.getUserByName(username);
		if (!user.getEmail().equals(email)) return "email doesn't match!";
		return "password: " + user.getPassword();
	}
}
