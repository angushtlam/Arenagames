package me.taur.arenagames.item;

import java.util.Arrays;

import me.taur.arenagames.util.Sym;

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
		PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 4 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_DEGEN", i);
		
	}
	
	public static void loadPotionOfRevitalization() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.PINK.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Potion of Revitalization");
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 12 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("POTION_OF_REVIT", i);
		
	}
	
	public static void loadElixirOfBerserkers() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Berserkers");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: Half " + Sym.HEART + ", 7 " + Sym.HUNGER,
				ChatColor.GOLD + "Requires 10 " + Sym.HUNGER + " To Use",
				ChatColor.YELLOW + "Applies Speed I and Strength II",
				ChatColor.YELLOW + "for 10 sec."));
		
		PotionEffect effect = new PotionEffect(PotionEffectType.SATURATION, 1, 0, false);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());

		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_BERSERKERS", i);
		
	}
	
	public static void loadElixirOfSacrifice() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.PURPLE.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Sacrifice");
		pm.setLore(Arrays.asList(ChatColor.GOLD + "Cost: Half " + Sym.HEART + ", 7 " + Sym.HUNGER,
				ChatColor.GOLD + "Requires 10 " + Sym.HUNGER + " To Use",
				ChatColor.YELLOW + "Randomly applies 1 of the following",
				ChatColor.YELLOW + Sym.TAB + "Slow I for 3 sec.",
				ChatColor.YELLOW + Sym.TAB + "Speed II for 6 sec.",
				ChatColor.YELLOW + Sym.TAB + "Damage Resistance for 12 sec.",
				ChatColor.YELLOW + Sym.TAB + "Lightning Strike"));
		
		PotionEffect effect = new PotionEffect(PotionEffectType.SATURATION, 1, 0, false);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());

		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_SACRIFICE", i);
		
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
	
	public static void loadElixirOfShadows() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Shadows");
		PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 20, 2, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_SHADOWS", i);
		
	}
	
	public static void loadElixirOfAntigravity() {
		ItemStack i = CustomItemUtil.getDrinkPotion().clone();
		i.setDurability(PotionColor.RED.getDmg());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + "Elixir Of Antigravity");
		PotionEffect effect = new PotionEffect(PotionEffectType.JUMP, 20 * 120, 2, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItemUtil.STORE.put("ELIXIR_OF_ANTIGRAVITY", i);
		
	}
	
}