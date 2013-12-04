package me.taur.arenagames.item;

import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;

public class CustomProjectileParticles {
	public static void tick2() {
		for (Projectile proj : CustomProjectileListener.PROJECTILES.keySet()) {
			if (CustomProjectileListener.PROJECTILES.get(proj).equalsIgnoreCase(ChatColor.stripColor("Taste of Isolation"))) {
				Location loc = proj.getLocation();
				ParticleUtil.RAINBOW_SMOKE.sendToLocation(loc, 0.1F, 0.1F, 3);
				
			}
		}
	}
	
}
