package me.taur.arenagames.item;

import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;

public class CustomProjectileParticles {
	public static void tick2() {
		for (Projectile proj : CustomProjectileListener.PROJECTILES.keySet()) {
			if (proj.isDead() || !proj.isValid()) {
				CustomProjectileListener.PROJECTILES.remove(proj);
				continue;
				
			}
			
			if (CustomProjectileListener.PROJECTILES.get(proj).equalsIgnoreCase(ChatColor.stripColor("Taste of Isolation"))) {
				Location loc = proj.getLocation();
				ParticleUtil.SPELL.sendToLocation(loc, 0.1F, 0.1F, 2);
				continue;
				
			} else if (CustomProjectileListener.PROJECTILES.get(proj).equalsIgnoreCase(ChatColor.stripColor("Wondershot"))) {
				Location loc = proj.getLocation();
				ParticleUtil.SNOWBALL.sendToLocation(loc, 0.1F, 0.1F, 2);
				ParticleUtil.WITCH_MAGIC.sendToLocation(loc, 0.1F, 0.1F, 5);
				ParticleUtil.MAGIC_CRITIAL_SMALL.sendToLocation(loc, 0.1F, 0.1F, 5);
				continue;
				
			}
			
		}
	}
	
}
