package me.taur.arenagames.perk;

import org.bukkit.Material;

public enum EffectPerkUtil {
	STORM("Mother Nature's Curse", Material.GHAST_TEAR, 0, 820),
	BLOOM("Spring Bloom", Material.RED_ROSE, 0, 290),
	MUSIC("Symphony", Material.RECORD_8, 0, 290),
	CLOUD("Up On The Clouds", Material.SNOW_BALL, 0, 670),
	SOIL("Tormented Soil", Material.COAL, 0, 290),
	ENDER("Mystic Magic", Material.EYE_OF_ENDER, 0, 440),
	FIRESCARF("Scarf of the Lava Deity", Material.CHAINMAIL_HELMET, 0, 440),
	FIRETRAIL("Lava Spirits", Material.BLAZE_POWDER, 0, 670),
	HEARTSCARF("Scarf of Romance", Material.LEATHER_HELMET, 0, 440),
	HEARTTRAIL("Road of Love", Material.REDSTONE, 0, 670),
	KNOWLEDGE("Knowledge Is Power", Material.BOOK, 0, 670),
	SUPERCHICKEN("Super Chicken", Material.FEATHER, 0, 820),
	BLOOD("Decay", Material.SPIDER_EYE, 0, 820);

	private final String name;
	private final Material mat;
	private final int curr;
	private final int cash;
	
	EffectPerkUtil(String name, Material mat, int curr, int cash) {
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
