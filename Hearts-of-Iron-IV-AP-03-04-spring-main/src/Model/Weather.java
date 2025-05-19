package Model;

public enum Weather {
	SUNNY(100, 100), RAINY(80, 10), BLIZZARD(60, 30), SANDSTORM(30, 60), FOG(20, 70);

	public final int attack;
	public final int airAttack;

	Weather(int attack, int airAttack) {
		this.attack = attack;
		this.airAttack = airAttack;
	}

	public static Weather getWeather(String name) {
		return switch (name) {
			case "sunny" -> Weather.SUNNY;
			case "rainy" -> Weather.RAINY;
			case "blizzard" -> Weather.BLIZZARD;
			case "sandstorm" -> Weather.SANDSTORM;
			case "fog" -> Weather.FOG;
			default -> null;
		};
	}
}
