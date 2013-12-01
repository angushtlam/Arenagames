package me.taur.arenagames.item;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemUtil {
	public static HashMap<String, ItemStack> STORE = new HashMap<String, ItemStack>();
	
	public static void enable() {
		CustomPotion.loadPotionOfDegeneration();
		CustomPotion.loadPotionOfGrimace();
		CustomPotion.loadPotionOfRevitalization();
		CustomPotion.loadElixirOfBerserkers();
		CustomPotion.loadElixirOfShadows();
		CustomPotion.loadElixirOfOnslaught();
		CustomPotion.loadElixirOfAdrenaline();
		CustomPotion.loadElixirOfCranked();
		
		CustomItem.loadCommandTremble();
		CustomItem.loadCommandLockdown();
		CustomItem.loadTheStrangler();
		CustomItem.loadBaneOfTheForest();
		CustomItem.loadTasteOfIsolation();
		
	}
	
	public static ItemStack getSplashPotion() {
		return new ItemStack(Material.POTION, 1, (short) 16384);
	}
	
	public static ItemStack getDrinkPotion() {
		return new ItemStack(Material.POTION, 1);
	}
	
}
