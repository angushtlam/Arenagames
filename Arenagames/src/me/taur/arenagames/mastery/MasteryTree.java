package me.taur.arenagames.mastery;

import java.util.HashMap;

import me.taur.arenagames.shop.Cost;

public class MasteryTree {
	public static HashMap<Integer, Cost> BLOODTHIRSTY = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> SPEED_SIPHON = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> FOCUS = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> TENACIOUS = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> FIREFIGHTER = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> FEATHER_FEET = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> HEART_OF_GOLD = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> REPOSE = new HashMap<Integer, Cost>();
	public static HashMap<Integer, Cost> LAST_RESORT = new HashMap<Integer, Cost>();
	
	public static void loadValues() {
		BLOODTHIRSTY.put(1, new Cost(200, -1));
		BLOODTHIRSTY.put(2, new Cost(800, -1));
		BLOODTHIRSTY.put(4, new Cost(2000, -1));
		BLOODTHIRSTY.put(8, new Cost(5200, -1));
		
		SPEED_SIPHON.put(1, new Cost(400, -1));
		SPEED_SIPHON.put(2, new Cost(1000, -1));
		SPEED_SIPHON.put(3, new Cost(4800, -1));
		
		FOCUS.put(2, new Cost(1600, -1));
		FOCUS.put(4, new Cost(4000, -1));
		
		TENACIOUS.put(1, new Cost(800, -1));
		TENACIOUS.put(6, new Cost(6200, -1));
		
		FIREFIGHTER.put(1, new Cost(200, -1));
		FIREFIGHTER.put(3, new Cost(1400, -1));
		FIREFIGHTER.put(6, new Cost(3800, -1));
		
		FEATHER_FEET.put(1, new Cost(800, -1));
		FEATHER_FEET.put(4, new Cost(2200, -1));
		FEATHER_FEET.put(8, new Cost(5800, -1));
		
		HEART_OF_GOLD.put(1, new Cost(700, -1));
		HEART_OF_GOLD.put(3, new Cost(1600, -1));
		HEART_OF_GOLD.put(5, new Cost(3400, -1));
		HEART_OF_GOLD.put(7, new Cost(6200, -1));
		
		REPOSE.put(1, new Cost(200, -1));
		REPOSE.put(3, new Cost(2800, -1));
		REPOSE.put(6, new Cost(5200, -1));
		
		LAST_RESORT.put(4, new Cost(3600, -1));
		LAST_RESORT.put(8, new Cost(7800, -1));
		
	}
}
