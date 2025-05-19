package Model;

import java.util.*;

public class Leader {
	private static final Map<String, Map<Ideology, Leader>> countryIdeologyLeaders = new HashMap<>();
	private static final Map<String, Leader> defaultLeaders = new HashMap<>();

	private final Ideology ideology;
	private final String name;

	static {
		initializeLeaders();
	}

	private Leader(Ideology ideology, String name) {
		this.ideology = ideology;
		this.name = name;
	}

	private static void initializeLeaders() {
		// German Reich
		addLeader("German Reich", Ideology.DEMOCRACY, "Adenauer", false);
		addLeader("German Reich", Ideology.COMMUNISM, "Pieck", false);
		addLeader("German Reich", Ideology.FASCISM, "Hitler", true);

		// Soviet Union
		addLeader("Soviet Union", Ideology.DEMOCRACY, "Zombie-Lenin", false);
		addLeader("Soviet Union", Ideology.COMMUNISM, "Stalin", true);
		addLeader("Soviet Union", Ideology.FASCISM, "Trotsky", false);

		// United States
		addLeader("United States", Ideology.DEMOCRACY, "Roosevelt", true);
		addLeader("United States", Ideology.COMMUNISM, "Browder", false);
		addLeader("United States", Ideology.FASCISM, "Pelley", false);

		// United Kingdom
		addLeader("United Kingdom", Ideology.DEMOCRACY, "Churchill", true);
		addLeader("United Kingdom", Ideology.FASCISM, "Mosley", false);

		// Japan
		addLeader("Japan", Ideology.FASCISM, "Hirohito", true);
	}

	private static void addLeader(String country, Ideology ideology, String name, boolean isDefault) {
		Leader leader = new Leader(ideology, name);
		countryIdeologyLeaders.computeIfAbsent(country, k -> new EnumMap<>(Ideology.class)).put(ideology, leader);
		if (isDefault) {
			defaultLeaders.put(country, leader);
		}
	}

	public static Leader getLeader(String country, Ideology ideology) {
		Map<Ideology, Leader> ideologyLeaders = countryIdeologyLeaders.getOrDefault(country, Collections.emptyMap());
		return ideologyLeaders.getOrDefault(ideology, null);
	}

	public static Leader getDefaultLeader(String country) {
		return defaultLeaders.get(country);
	}

	public static List<Leader> getLeadersForCountry(String country) {
		return new ArrayList<>(countryIdeologyLeaders.getOrDefault(country, Collections.emptyMap()).values());
	}

	public Ideology getIdeology() {
		return ideology;
	}

	public String getName() {
		return name;
	}
}