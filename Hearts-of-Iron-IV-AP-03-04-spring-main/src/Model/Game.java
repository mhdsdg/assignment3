package Model;

import java.util.*;

public class Game {
	private final Map<String, Player> players;
	private ArrayList<String> orderedUsernames = new ArrayList<>();
	private String currentPlayerUsername;
	public static Game currentGame;

	public Game() {
		players = new HashMap<>();
		currentGame = this;
	}

	public void addPlayer(String username, String country) {
		Player player = new Player(User.getUserByName(username), Country.getCountryByName(country));
		players.put(username, player);
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setCurrentPlayer(String username) {
		currentPlayerUsername = username;
	}

	public String getCurrentPlayerUsername() {
		return currentPlayerUsername;
	}

	public Player getCurrentPlayer() {
		return this.players.get(this.currentPlayerUsername);
	}

	public Player getCountryOwner(Country country) {
		for (Player player : this.players.values()) {
			if (player.getCountry().equals(country)) return player;
		}
		return null;
	}

	public Player getCountryOwner(String countryName) {
		for (Player player : this.players.values()) {
			if (player.getCountry().getName().equals(countryName)) return player;
		}
		return null;
	}

	public void setOrderedUsernames(ArrayList<String> orderedUsernames) {
		this.orderedUsernames = orderedUsernames;
	}

	public ArrayList<String> getOrderedUsernames() {
		return orderedUsernames;
	}
}
