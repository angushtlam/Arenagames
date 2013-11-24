package me.taur.arenagames.item;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomPotion {
	public static void loadPotionOfDegeneration() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.GREEN.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Potion of Degeneration");
		PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 10 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_DEGEN", i);
		
	}
	
	public static void loadPotionOfGrimace() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.DARK_GRAY.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Potion of Grimace");
		PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, 6 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_GRIM", i);
		
	}
	
	public static void loadPotionOfRevitalization() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.PINK.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Potion of Revitalization");
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_REVIT", i);
		
	}
	
	public static void loadPotionOfMercy() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.ORANGE.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Potion of Mercy");
		PotionEffect effect = new PotionEffect(PotionEffectType.HARM, 1, 0, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_MERCY", i);
		
	}
	
	public static void loadElixirOfVictory() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Victory");
		PotionEffect effect = new PotionEffect(PotionEffectType.HEAL, 1, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_VICTORY", i);
		
	}
	
	public static void loadElixirOfFocus() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.PURPLE.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Focus");
		PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4 * 20, 0, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SLOW, 4 * 20, 2, true);
		pm.addCustomEffect(effect2, true);
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_FOCUS", i);
		
	}
	
	public static void loadElixirOfOnslaught() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Onslaught");
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 7 * 20, 2, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_ONSLAUGHT", i);
		
	}
	
	public static void loadElixirOfAdrenaline() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.BLUE.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Adrenaline");
		PotionEffect effect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 8 * 20, 0, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 8 * 20, 1, true);
		pm.addCustomEffect(effect2, true);
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_ADREN", i);
		
	}
	
	public static void loadElixirOfShadows() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Shadows");
		PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, 10 * 20, 2, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_SHADOWS", i);
		
	}
	
}