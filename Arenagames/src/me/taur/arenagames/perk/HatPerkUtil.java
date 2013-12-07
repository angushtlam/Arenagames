package me.taur.arenagames.perk;

import org.bukkit.Material;

public enum HatPerkUtil {
	GLASS("Alpha Astronaut", 0, Material.GLASS, 0, 0),
	SPONGE("Sponge Bob", 1, Material.SPONGE, 1700, 0),
	PUMPKIN("Spooky Halloween", 2, Material.JACK_O_LANTERN, 1700, 0),
	BRICK("Brickhead", 3, Material.BRICK, 1700, 0),
	BOOKSHELF("Librarian", 4, Material.BOOKSHELF, 1700, 0),
	MELON("Melon Muncher", 5, Material.MELON_BLOCK, 2250, 0),
	SOULSAND("Tormented Mind", 6, Material.SOUL_SAND, 2250, 0),
	ICE("Chilled", 7, Material.PACKED_ICE, 2250, 0),
	NOTE("Midiphile", 8, Material.NOTE_BLOCK, 2250, 0),
	CHEST("Steve Storage & Co.", 9, Material.CHEST, 4600, 0),
	TNT("Playing With Fire", 10, Material.TNT, 4600, 0),
	COAL("Coalface", 11, Material.COAL_BLOCK, 4600, 0),
	IRON("Metallic Mind", 12, Material.IRON_BLOCK, 4600, 0),
	LAPIS("Feeling Blue", 13, Material.LAPIS_BLOCK, 8100, 0),
	REDSTONE("Eccentric Red", 14, Material.REDSTONE_BLOCK, 8100, 0),
	GOLD("King Midas\'s Touch", 15, Material.GOLD_BLOCK, 8100, 0),
	EMERALD("Metallic Mind", 16, Material.EMERALD_BLOCK, 8100, 0),
	DIAMOND("First Class", 17, Material.DIAMOND_BLOCK, 0, 50),
	ENDER_CHEST("Galaxy Storage & Co.", 18, Material.ENDER_CHEST, 0, 50),
	BEDROCK("Bedrock Solid", 19, Material.BEDROCK, 0, 50);

	private final String name;
	private final int id;
	private final Material mat;
	private final int curr;
	private final int cash;
	
	HatPerkUtil(String name, int id, Material mat, int curr, int cash) {
		this.name = name;
		this.id = id;
		this.mat = mat;
		this.curr = curr;
		this.cash = cash;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}

	public Material getMaterial() {
		return this.mat;
	}
	
	public int getCurrencyCost() {
		return this.curr;
	}
	
	public int getCashCost() {
		return this.cash;
	}
}
