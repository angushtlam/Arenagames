package me.taur.arenagames.mastery;

import java.util.HashMap;

import me.taur.arenagames.shop.Cost;

public enum Masteries {
	BLOODTHIRSTY("Bloodthirsty", 0, MasteryTree.BLOODTHIRSTY),
	SPEED_SIPHON("Speed Siphon", 1, MasteryTree.SPEED_SIPHON),
	FOCUS("Focus", 2, MasteryTree.FOCUS),
	TENACIOUS("Tenacious", 3, MasteryTree.TENACIOUS),
	FIREFIGHTER("Firefighter", 4, MasteryTree.FIREFIGHTER),
	FEATHER_FEET("Feather Feet", 5, MasteryTree.FEATHER_FEET),
	HEART_OF_GOLD("Heart Of Gold", 6, MasteryTree.HEART_OF_GOLD),
	REPOSE("Repose", 7, MasteryTree.REPOSE),
	LAST_RESORT("Last Resort", 8, MasteryTree.LAST_RESORT);

	private final String name;
	private final int id;
	private final HashMap<Integer, Cost> tree;
	
	Masteries(String name, int id, HashMap<Integer, Cost> tree) {
		this.name = name;
		this.id = id;
		this.tree = tree;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}

	public HashMap<Integer, Cost> getTree() {
		return this.tree;
	}
	
	@SuppressWarnings("unused")
	private enum Categories {
		OFFENSE, DEFENSE, UTILITY;
	}
	
}
