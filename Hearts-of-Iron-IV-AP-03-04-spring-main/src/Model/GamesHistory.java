package Model;

import java.util.*;

public class GamesHistory {
	private static int currentGameID = 0;
	private final HashMap<String, GameRecord> games;
	private static final GamesHistory instance = new GamesHistory();

	private GamesHistory() {
		this.games = new HashMap<>();
	}

	public static GamesHistory getInstance() {
		return instance;
	}

	public void addNewGame(String gameId) {
		if (!games.containsKey(gameId)) games.put(gameId, new GameRecord());
	}

	public void addPlayerToGame(String gameId, String username, String controllingCountry, int earnedPoints) {
		GameRecord game = games.get(gameId);
		if (game != null) {
			PlayerRecord player = new PlayerRecord(username, controllingCountry, earnedPoints);
			game.addPlayer(player);
		}
	}

	public GameRecord getGame(String gameId) {
		return games.get(gameId);
	}

	public List<String> listAllGames() {
		return new ArrayList<>(games.keySet());
	}

	public String getAndIncreaseID() {
		currentGameID++;
		return String.valueOf(currentGameID - 1);
	}


	public record PlayerRecord(String username, String controllingCountry, int earnedPoints) {

	}

	public static class GameRecord {
		private final List<PlayerRecord> players;

		public GameRecord() {
			this.players = new ArrayList<>();
		}


		public List<PlayerRecord> getPlayers() {
			return players;
		}

		public void addPlayer(PlayerRecord player) {
			players.add(player);
		}
	}
}

