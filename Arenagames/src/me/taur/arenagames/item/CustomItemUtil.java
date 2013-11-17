package me.taur.arenagames.item;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemUtil {
	public static HashMap<String, ItemStack> STORE = new HashMap<String, ItemStack>();
	
	public static void enable() {
		CustomPotion.loadPotionOfDegeneration();
		CustomPotion.loadPotionOfGrimace();
		CustomPotion.loadPotionOfMercy();
		CustomPotion.loadPotionOfRevitalization();
		CustomPotion.loadElixirOfVictory();
		CustomPotion.loadElixirOfShadows();
		CustomPotion.loadElixirOfOnslaught();
		CustomPotion.loadElixirOfFocus();
		CustomPotion.loadElixirOfAdrenaline();
		
		CustomItem.loadCommandVacuum();
		CustomItem.loadCommandEndure();
		
	}
	
	public static ItemStack getSplashPotion() {
		return new ItemStack(Material.POTION, 1, (short) 16384);
	}
	
	public static ItemStack getDrinkPotion() {
		return new ItemStack(Material.POTION, 1);
	}
	
}
