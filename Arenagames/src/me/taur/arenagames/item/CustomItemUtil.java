package me.taur.arenagames.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemUtil {
	public static void enable() {
		CustomPotion.loadSplashPotionOfDegeneration();
		CustomPotion.loadSplashPotionOfGrimace();
		
	}
	
	public static ItemStack getSplashPotion() {
		return new ItemStack(Material.POTION, 1, (short) 16384);
	}
	
	public static ItemStack getDrinkingPotion() {
		return new ItemStack(Material.POTION, 1);
	}
	
}
