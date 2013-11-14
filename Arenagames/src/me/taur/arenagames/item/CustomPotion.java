package me.taur.arenagames.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomPotion {
	public static void loadSplashPotionOfDegeneration() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.GREEN.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName("Splash Potion of Degeneration");
		PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 10 * 20, 2, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItem.STORE.put("POTION_OF_DEGEN", i);
		
	}
	
	public static void loadSplashPotionOfGrimace() {
		ItemStack i = CustomItemUtil.getSplashPotion().clone();
		i.setDurability(PotionColor.DARK_GRAY.getSplash());
		
		PotionMeta pm = (PotionMeta) i.getItemMeta();
		pm.setDisplayName("Splash Potion of Grimace");
		PotionEffect effect = new PotionEffect(PotionEffectType.WITHER, 6 * 20, 1, true);
		pm.addCustomEffect(effect, true);
		pm.setMainEffect(effect.getType());
		
		i.setItemMeta(pm);
		CustomItem.STORE.put("POTION_OF_GRIM", i);
		
	}
	
}
