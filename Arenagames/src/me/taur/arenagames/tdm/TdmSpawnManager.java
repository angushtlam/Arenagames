package me.taur.arenagames.tdm;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TdmSpawnManager {
	public static void spawn(final Player p, Location tp) {
		p.teleport(tp);
		
		purgeEffects(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 400));  // Prevent player from seeing the teleport.
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 3, 1)); // Stop spawncamping and prevent protected players from attacking.
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 3, 1));
		
	}
	
	public static void kill(Player p, Location tp) {
		Location loc = p.getLocation();
		ParticleUtil.RED_SMOKE.sendToLocation(loc, 1.5F, 1.5F, 80);
		ParticleUtil.EXPLODE.sendToLocation(loc, 2.0F, 4.0F, 125);
		ParticleUtil.LAVA_DRIP.sendToLocation(loc.add(0.0, 1.0, 0.0), 0.6F, 1.0F, 70);
		
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(19);
		
		purgeEffects(p);
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 700)); // Turn the screen completely dark
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60, 1));
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 60, 1)); // Hide the player
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60, 5)); // Prevent the player from dealing damage
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 60, 100));
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60, 128));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 128));
		
		InvUtil.clearPlayerInv(p);
		
		p.teleport(tp); // Teleport player
		p.setFallDistance(0F);
		
	}
	
	public static void endDeath(final Player p) {
		purgeEffects(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 3, 1));
		
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
		    public void run() {
		    	if (Room.PLAYERS.get(p) != null) {
		    		purgeEffects(p);
		    	}
		    }
		}, 60L); // Remove effects in 3 seconds.
	}
	
	public static void purgeEffects(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		p.setFireTicks(0); // Remove fire
		
	}
	
	public static void respawn(final Player p, final Location tp) {
		kill(p, tp);
		
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
		    public void run() {
		    	if (Room.PLAYERS.get(p) != null) {
		    		endDeath(p);
		    	}
		    }
		}, 60L); // Spawn in 3 seconds.
		
	}
}