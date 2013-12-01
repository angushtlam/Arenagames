package me.taur.arenagames.item;

import java.util.List;
import java.util.Random;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SpellUtil {
	public static List<Entity> getNearbyEntities(Player p, int range) {
		return p.getNearbyEntities(range, range, range);
	}
	
	public static void forceKnockZone(Player p, Sound sound, int range, float force, float vertForce, float highestY, float power) {
		Vector pv = p.getLocation().toVector();
		List<Entity> entities = getNearbyEntities(p, range);
		Vector e, v;

		for (Entity ent : entities) {
			if (ent instanceof LivingEntity) {
				if (ent instanceof Player) { // Play sound first.
					Player pl = (Player) ent;
					pl.playSound(pl.getLocation(), sound, 0.5F, 1.0F);

				}

				e = ent.getLocation().toVector();
				v = e.subtract(pv).normalize().multiply(force / 10.0 * power);

				if (force != 0) {
					v.setY(v.getY() + (vertForce / 10.0 * power));
				} else {
					v.setY(vertForce / 10.0 * power);
				}

				if (v.getY() > (highestY / 10.0)) {
					v.setY(highestY / 10.0);
				}

				ent.setVelocity(v);
				ParticleUtil.ANGRY_VILLAGER.sendToLocation(ent.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
				drawLine(ParticleUtil.MAGIC_CRITIAL, p.getLocation(), ent.getLocation(), 0.0, 0.0F, 0.0F, 5);

			}
		}
	}
	
	public static void forceKnockEntity(Location toLoc, LivingEntity ent, float force, float vertForce, float highestY, float power) {
		if (!ent.getWorld().equals(toLoc.getWorld())) {
			return;
		}
		
		Vector pv = ent.getLocation().toVector();
		Vector e = toLoc.toVector();
		Vector v = pv.subtract(e).normalize().multiply(force / 10.0 * power);
		
		if (force != 0) {
			v.setY(v.getY() + (vertForce / 10.0 * power));
		} else {
			v.setY(vertForce / 10.0 * power);
		}

		if (v.getY() > (highestY / 10.0)) {
			v.setY(highestY / 10.0);
		}

		ent.setVelocity(v);
		ParticleUtil.ANGRY_VILLAGER.sendToLocation(ent.getLocation().add(0.0, 2.5, 0.0), 0.0F, 0.0F, 1);
		drawLine(ParticleUtil.MAGIC_CRITIAL, ent.getLocation(), toLoc, 0.0, 0.0F, 0.0F, 5);
		
	}

	public static void drawLine(ParticleUtil fx, Location loc1, Location loc2, double moveVert, float offset, float offsetVert, int amt) {
		double distanceBetween = 1;

		int c = (int)Math.ceil(loc1.distance(loc2) / distanceBetween) - 1;
		if (c <= 0) return;
		Vector v = loc2.toVector().subtract(loc1.toVector()).normalize().multiply(distanceBetween);
		Location l = loc1.clone();

		for (int i = 0; i < c; i++) {
			l.add(v);
			fx.sendToLocation(l.add(0.0, moveVert, 0.0), offset, offsetVert, amt);
			
		}
		
	}
	
	public static void itemSpray(Location loc, ItemStack item, int count, float force, int duration) {
		if (count > 0) {
			Random rand = new Random();
			loc = loc.clone().add(0.0, 1.0, 0.0);
			final Item[] items = new Item[count];
			
			for (int i = 0; i < count; i++) {
				items[i] = loc.getWorld().dropItem(loc, item);
				items[i].setVelocity(new Vector((rand.nextDouble()-.5) * force, (rand.nextDouble()-.5) * force, (rand.nextDouble()-.5) * force)); // Add some randomness
				items[i].setPickupDelay(duration * 3); // Prevent it from being picked up.
				
			}
			
			Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
				public void run() {
					for (int i = 0; i < items.length; i++) {
						items[i].remove();
					}
				}
			}, duration);
			
		}
	}
	
}