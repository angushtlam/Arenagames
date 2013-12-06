package me.taur.arenagames.item;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemUtil {
	public static HashMap<String, ItemStack> STORE = new HashMap<String, ItemStack>();
	
	public static void enable() {
		CustomPotion.loadPotionOfDegeneration();
		CustomPotion.loadPotionOfRevitalization();
		CustomPotion.loadElixirOfBerserkers();
		CustomPotion.loadElixirOfShadows();
		CustomPotion.loadElixirOfOnslaught();
		CustomPotion.loadElixirOfSacrifice();
		
		CustomItem.loadCommandTremble();
		CustomItem.loadCommandLockdown();
		CustomItem.loadTheStrangler();
		CustomItem.loadWondershot();
		CustomItem.loadTasteOfIsolation();
		CustomItem.loadBladeNoir();
		CustomItem.loadMachete();
		CustomItem.loadSickScalpel();
		
	}
	
	public static ItemStack getSplashPotion() {
		return new ItemStack(Material.POTION, 1, (short) 16384);
	}
	
	public static ItemStack getDrinkPotion() {
		return new ItemStack(Material.POTION, 1);
	}
	
}
