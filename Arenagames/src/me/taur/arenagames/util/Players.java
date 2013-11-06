package me.taur.arenagames.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Players {
	public static void respawnEffects(Player p) {
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(19);
		
		p.setFireTicks(0);
		
		for (PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 800));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 80, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3));
		
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		
	}
}
