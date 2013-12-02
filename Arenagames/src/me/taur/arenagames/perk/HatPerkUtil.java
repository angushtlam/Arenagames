package me.taur.arenagames.perk;

import org.bukkit.Material;

public enum HatPerkUtil {
	GLASS("Alpha Astronaut", Material.GLASS, 0, 0),
	SPONGE("Sponge Bob", Material.SPONGE, 1700, 0),
	PUMPKIN("Spooky Halloween", Material.JACK_O_LANTERN, 1700, 0),
	BRICK("Brickhead", Material.BRICK, 1700, 0),
	BOOKSHELF("Librarian", Material.BOOKSHELF, 1700, 0),
	MELON("Melon Muncher", Material.MELON_BLOCK, 2250, 0),
	SOULSAND("Tormented Mind", Material.SOUL_SAND, 2250, 0),
	ICE("Chilled", Material.PACKED_ICE, 2250, 0),
	NOTE("Midiphile", Material.NOTE_BLOCK, 2250, 0),
	CHEST("Steve Storage & Co.", Material.CHEST, 4600, 0),
	TNT("Playing With Fire", Material.TNT, 4600, 0),
	COAL("Coalface", Material.COAL_BLOCK, 4600, 0),
	IRON("Metallic Mind", Material.IRON_BLOCK, 4600, 0),
	LAPIS("Feeling Blue", Material.LAPIS_BLOCK, 8100, 0),
	REDSTONE("Eccentric Red", Material.REDSTONE_BLOCK, 8100, 0),
	GOLD("King Midas\'s Touch", Material.GOLD_BLOCK, 8100, 0),
	EMERALD("Metallic Mind", Material.EMERALD_BLOCK, 8100, 0),
	DIAMOND("First Class", Material.DIAMOND_BLOCK, 0, 50),
	ENDER_CHEST("Galaxy Storage & Co.", Material.ENDER_CHEST, 0, 50),
	BEDROCK("Bedrock Solid", Material.BEDROCK, 0, 50);

	private final String name;
	private final Material mat;
	private final int curr;
	private final int cash;
	
	HatPerkUtil(String name, Material mat, int curr, int cash) {
		this.name = name;
		this.mat = mat;
		this.curr = curr;
		this.cash = cash;
	}
	
	public String getName() {
		return this.name;
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
