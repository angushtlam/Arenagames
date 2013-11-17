package me.taur.arenagames.item;

import java.util.List;

import me.taur.arenagames.util.ParticleEffect;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpellUtil {
	public static void forceKnockbackZone(Player p, int range, float force, float vertForce, float highestY, float power) {
		Vector pv = p.getLocation().toVector();
		List<Entity> entities = p.getNearbyEntities(range, range, range);
		Vector e, v;
		
		for (Entity entity : entities) {
			if (entity instanceof LivingEntity) {
				if (entity instanceof Player) { // Play sound first.
					Player pl = (Player) entity;
					pl.playSound(pl.getLocation(), Sound.SKELETON_HURT, 0.5F, 0.5F);
					
				}
				
				e = entity.getLocation().toVector();
				v = e.subtract(pv).normalize().multiply(force / 10.0 * power);
				
				if (force != 0) {
					v.setY(v.getY() + (vertForce / 10.0 * power));
				} else {
					v.setY(vertForce / 10.0 * power);
				}
				
				if (v.getY() > (highestY / 10.0)) {
					v.setY(highestY / 10.0);
				}
				
				entity.setVelocity(v);
				ParticleEffect.ANGRY_VILLAGER.display(p.getLocation(), 1.0F, 2.0F, 1.0F, 8, 8);
				
			}
		}
		
		ParticleEffect.EXPLODE.display(p.getLocation(), force, force, force, 5, range * 15);
		p.playSound(p.getLocation(), Sound.SKELETON_HURT, 0.5F, 0.5F);
		
	}
	
}
