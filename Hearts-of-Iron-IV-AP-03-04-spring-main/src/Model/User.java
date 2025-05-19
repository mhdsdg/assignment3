package Model;

import java.util.*;

public class User {
	private final String name;
	private final String password;
	private final String email;
	private int points;

	private static final HashMap<String, User> userRegistry = new HashMap<>();
	private static User loggedInUser;

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(String username) {
		User.loggedInUser = getUserByName(username);
	}

	public User(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.points = 0;

		userRegistry.put(name, this);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getPoints() {
		return this.points;
	}

	public void addToPoints(int points) {
		this.points += points;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	public String getPassword() {
		return password;
	}

	public static User getUserByName(String name) {
		return userRegistry.getOrDefault(name, null);
	}

	public static String[] getAllUserNames() {
		return userRegistry.keySet().toArray(new String[0]);
	}

	public static void deleteGuests() {
		userRegistry.entrySet().removeIf(entry -> entry.getKey().matches("guest\\d+"));
	}
}