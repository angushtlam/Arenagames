package me.taur.arenagames.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Players {
	public static void respawnEffects(Player p) {
		p.setFireTicks(0);
		p.getActivePotionEffects().clear();
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 800));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3));
		
		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		
	}
}
