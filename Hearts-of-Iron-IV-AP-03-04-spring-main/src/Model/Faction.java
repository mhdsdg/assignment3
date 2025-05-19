package Model;

import java.util.*;
import java.util.concurrent.*;

public class Faction {
	private static final Map<String, Faction> factions = new ConcurrentHashMap<>();
	private final Set<String> members = ConcurrentHashMap.newKeySet();
	private final String name;

	public Faction(String name) {
		this.name = name;
		factions.put(name, this);
		this.members.add(Game.currentGame.getCurrentPlayer().getCountry().getName());
		Game.currentGame.getCurrentPlayer().getCountry().addToFactions(this);
	}

	public static boolean factionExists(String name) {
		return factions.containsKey(name);
	}

	public static Faction getFaction(String name) {
		return factions.get(name);
	}

	public void join(String country) {
		members.add(country);
	}

	public void leave(String country) {
		members.remove(country);
	}

	public Set<String> getMembers() {
		return Collections.unmodifiableSet(members);
	}

	public String getName() {
		return name;
	}
}