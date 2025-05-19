package Model;

import java.util.*;

public class Battalion {
	public enum BattalionType {
		INFANTRY, PANZER, AIR_FORCE, NAVY
	}

	private final String name;
	private final BattalionType type;
	private int level;
	private int power;
	private final int captureRatio;

	public Battalion(String name, BattalionType type, String country) {
		this.name = name;
		this.type = type;
		this.level = 0;
		this.power = getInitialPower(country, type);
		this.captureRatio = getInitialCaptureRatio(country, type);
	}

	public static Map<String, Integer> getCreationCost(BattalionType type) {
		Map<String, Integer> cost = new HashMap<>();

		switch (type) {
			case INFANTRY -> {
				cost.put("Fuel", 0);
				cost.put("Steel", 10000);
				cost.put("Sulfur", 10000);
				cost.put("Man Power", 15000);
			}
			case PANZER -> {
				cost.put("Fuel", 10000);
				cost.put("Steel", 20000);
				cost.put("Sulfur", 10000);
				cost.put("Man Power", 5000);
			}
			case AIR_FORCE -> {
				cost.put("Fuel", 50000);
				cost.put("Steel", 35000);
				cost.put("Sulfur", 10000);
				cost.put("Man Power", 1000);
			}
			case NAVY -> {
				cost.put("Fuel", 30000);
				cost.put("Steel", 50000);
				cost.put("Sulfur", 10000);
				cost.put("Man Power", 5000);
			}
		}
		return cost;
	}

	public static int getInitialCaptureRatio(String country, BattalionType battalionType) {
		return switch (country) {
			case "German Reich" -> switch (battalionType) {
				case INFANTRY -> 50;
				case NAVY -> 20;
				case PANZER -> 30;
				case AIR_FORCE -> 40;
			};
			case "Soviet Union" -> switch (battalionType) {
				case INFANTRY -> 20;
				case NAVY -> 30;
				case PANZER -> 60;
				case AIR_FORCE -> 40;
			};
			case "United States" -> switch (battalionType) {
				case INFANTRY -> 30;
				case NAVY -> 60;
				case PANZER -> 40;
				case AIR_FORCE -> 50;
			};
			case "United Kingdom" -> switch (battalionType) {
				case INFANTRY -> 60;
				case NAVY -> 40;
				case PANZER -> 50;
				case AIR_FORCE -> 20;
			};
			case "Japan" -> switch (battalionType) {
				case INFANTRY -> 40;
				case NAVY -> 50;
				case PANZER -> 20;
				case AIR_FORCE -> 30;
			};
			default -> throw new IllegalArgumentException("Country '" + country + "' is not exists");
		};
	}

	public static int getInitialPower(String country, BattalionType battalionType) {
		return switch (country) {
			case "German Reich" -> switch (battalionType) {
				case INFANTRY -> 15;
				case NAVY -> 5;
				case PANZER -> 20;
				case AIR_FORCE -> 10;
			};
			case "Soviet Union" -> switch (battalionType) {
				case INFANTRY -> 20;
				case NAVY -> 10;
				case PANZER -> 15;
				case AIR_FORCE -> 5;
			};
			case "United States" -> switch (battalionType) {
				case INFANTRY -> 5;
				case NAVY -> 15;
				case PANZER -> 10;
				case AIR_FORCE -> 20;
			};
			case "United Kingdom" -> switch (battalionType) {
				case INFANTRY -> 10;
				case NAVY -> 20;
				case PANZER -> 5;
				case AIR_FORCE -> 15;
			};
			case "Japan" -> switch (battalionType) {
				case INFANTRY, NAVY, PANZER, AIR_FORCE -> 10;
			};
			default -> throw new IllegalArgumentException("Country '" + country + "' is not exists");
		};
	}

	public String getName() {
		return name;
	}

	public BattalionType getType() {
		return type;
	}

	public int getLevel() {
		return level;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getCaptureRatio() {
		return captureRatio;
	}


	public void upgrade() {
		int powerIncrease = switch (this.level) {
			case 0 -> 5;
			case 1 -> 7;
			case 2 -> 10;
			default -> throw new RuntimeException("Upgrade btl to more than 3?");
		};
		this.power += powerIncrease;
		this.level += 1;
	}

	public static BattalionType getBattalionTypeFromString(String inp) {
		return switch (inp) {
			case "infantry" -> BattalionType.INFANTRY;
			case "navy" -> BattalionType.NAVY;
			case "panzer" -> BattalionType.PANZER;
			case "airforce" -> BattalionType.AIR_FORCE;
			default -> null;
		};
	}

	public static int getScoreMultiplier(BattalionType type) {
		return switch (type) {
			case INFANTRY -> 5;
			case PANZER -> 7;
			case NAVY -> 10;
			case AIR_FORCE -> 15;
		};
	}
}