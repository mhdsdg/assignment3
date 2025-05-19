package Model;

public class Player {
	private final User user;
	private final Country country;
	private int points;

	public Player(User user, Country country) {
		this.user = user;
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public Country getCountry() {
		return country;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public void losePoints(int points) {
		this.points -= points;
	}
}