package me.taur.arenagames.perk;

import org.bukkit.Material;

public enum EffectPerkUtil {
	BLOOM("Spring Bloom", 0, Material.RED_ROSE, 0, 290),
	MUSIC("Symphony", 1, Material.RECORD_8, 0, 290),
	SOIL("Tormented Soil", 2, Material.COAL, 0, 290),
	ENDER("Mystic Magic", 3, Material.EYE_OF_ENDER, 0, 490),
	FIRESCARF("Scarf of the Lava Deity", 4, Material.CHAINMAIL_HELMET, 0, 490),
	HEARTTRAIL("Road of Love", 5, Material.REDSTONE, 0, 490),
	FIRETRAIL("Lava Spirits", 6, Material.BLAZE_POWDER, 0, 640),
	KNOWLEDGE("Knowledge Is Power", 7, Material.BOOK, 0, 640),
	SUPERCHICKEN("Super Chicken", 8, Material.FEATHER, 0, 640),
	BLOOD("Decay", 9, Material.SPIDER_EYE, 0, 720),
	CLOUD("Up On The Clouds", 10, Material.SNOW_BALL, 0, 720),
	STORM("Mother Nature's Curse", 11, Material.GHAST_TEAR, 0, 720);

	private final String name;
	private final int id;
	private final Material mat;
	private final int curr;
	private final int cash;
	
	EffectPerkUtil(String name, int id, Material mat, int curr, int cash) {
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
