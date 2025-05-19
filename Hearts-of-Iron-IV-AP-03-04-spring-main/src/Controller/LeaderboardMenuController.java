package Controller;

import Model.GamesHistory;
import Model.User;

import java.util.*;

public class LeaderboardMenuController {
	public static String showRanking() {
		StringBuilder builder = new StringBuilder();
		builder.append("Leaderboard:\n");
		String[] usernames = User.getAllUserNames();
		List<User> userList = new ArrayList<>();
		for (String username : usernames) {
			User user = User.getUserByName(username);
			userList.add(user);
		}
		userList.sort((u1, u2) -> {
			int pointsComparison = Integer.compare(u2.getPoints(), u1.getPoints());
			if (pointsComparison == 0) {
				return u1.getName().compareTo(u2.getName());
			}
			return pointsComparison;
		});

		for (User user : userList)
			builder.append(user.getName()).append(" ").append(user.getPoints()).append("\n");
		if (builder.length() > 0 && builder.charAt(builder.length() - 1) == '\n')
			builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	public static String showHistory() {
		StringBuilder builder = new StringBuilder();
		List<String> gameIds = GamesHistory.getInstance().listAllGames();
		gameIds.sort(Comparator.reverseOrder());
		List<String> topGameIds = gameIds.subList(0, Math.min(5, gameIds.size()));
		builder.append("History:\n");
		for (String gameId : topGameIds) {
			GamesHistory.GameRecord game = GamesHistory.getInstance().getGame(gameId);
			List<GamesHistory.PlayerRecord> players = new ArrayList<>(game.getPlayers());
			for (GamesHistory.PlayerRecord player : players)
				builder.append(player.username()).append(" ").append(player.controllingCountry()).append(" ").append(player.earnedPoints()).append("\n");
			builder.append("----\n");
		}
		if (builder.length() >= 6) builder.delete(builder.length() - 6, builder.length());
		return builder.toString();
	}
}
