package me.taur.arenagames.crk;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleEffect;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrkSpawnManager {
	public static void kill(Player p) {
		Location loc = p.getLocation();
		ParticleEffect.RED_DUST.display(loc, 1.5F, 1.0F, 1.5F, 0, 80);
		ParticleEffect.EXPLODE.display(loc, 2.0F, 4.0F, 2.0F, 0, 125);
		ParticleEffect.DRIP_LAVA.display(loc, 0.6F, 1.0F, 0.6F, 0, 70);
		
		p.getWorld().createExplosion(p.getLocation(), 0.0F, false);
		
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(19);
		
		purgeEffects(p);
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 700)); // Turn the screen completely dark
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60, 1));
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 60, 1)); // Hide the player
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60, 5)); // Prevent the player from dealing damage
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 60, 5));
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60, 128));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60, 128));
		
		InvUtil.clearPlayerInv(p);
		nowDeadPlayer(p);
		
	}
	
	public static void spawn(Player p, Location tp) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 400));  // Prevent player from seeing the teleport.
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 3, 1)); // Stop spawncamping and prevent protected players from attacking.
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 3, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 3, 1));

		p.teleport(tp); // Teleport player
		p.setFallDistance(0);
		purgeEffects(p);
		
	}
	
	public static void purgeEffects(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		p.setFireTicks(0); // Remove fire
		
	}
	
	public static void nowDeadPlayer(final Player p) {
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
		    public void run() {
		    	if (Room.PLAYERS.get(p) != null) {
		    		purgeEffects(p);
		    		
		    		int time = Config.getCountdown(RoomType.CRK);
		    		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * time, 1)); // Entire duration of the game. Cleared later.
		    		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * time, 5));
		    		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * time, 5));
		    		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * time, 5));
		    		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * time, 1));
		    		
		    	}
		    }
		}, 40L);
	}
}