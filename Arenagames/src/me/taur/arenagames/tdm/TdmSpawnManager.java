package me.taur.arenagames.tdm;

import java.util.HashMap;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.ParticleUtil;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TdmSpawnManager {
	public static HashMap<Player, Integer> RESPAWN_TIMER = new HashMap<Player, Integer>();
	
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
	
	public static void purgeEffects(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		p.setFireTicks(0); // Remove fire
		
	}

	public static void respawnTimer(Player p) {
		killedEffect(p.getLocation());
		RESPAWN_TIMER.put(p, 5);
		
	}
	
	public static void killedEffect(Location loc) {
		ParticleUtil.RED_SMOKE.sendToLocation(loc, 1.5F, 1.5F, 80);
		ParticleUtil.EXPLODE.sendToLocation(loc, 2.0F, 4.0F, 125);
		ParticleUtil.LAVA_DRIP.sendToLocation(loc.add(0.0, 1.0, 0.0), 0.6F, 1.0F, 70);
		
	}
	
	public static void respawnPrecheck(final Player p) {
		if (Room.PLAYERS.containsKey(p)) { // Only apply when the player who died is killed by playing in an arena.
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
			
			if (room.getRoomType() == RoomType.TDM) {
				if (!RESPAWN_TIMER.containsKey(p)) {
					respawnToArena(p);
					
				}
				
				int timeleft = RESPAWN_TIMER.get(p).intValue();
				
				p.setHealth(p.getMaxHealth());
				p.setFoodLevel(19);
				
				purgeEffects(p);
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * timeleft, 700)); // Turn the screen completely dark
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * timeleft, 1));
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * timeleft, 1)); // Hide the player
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * timeleft, 5)); // Prevent the player from dealing damage
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * timeleft, 5));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * timeleft, 100));
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * timeleft, 128));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * timeleft, 128));
				
				Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
				    public void run() {
				    	respawnToArena(p);
				    }
				}, 20L * timeleft);
				
			}
			
		} else {
			Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			    public void run() {
			    	purgeEffects(p);
					p.teleport(Config.getGlobalLobby()); // Teleport player to main spawn
					p.setFallDistance(0F);
					
			    }
			}, 2L);
		}
		
	}
	
	public static void respawnToArena(final Player p) {
		if (Room.PLAYERS.containsKey(p)) { // Only apply when the player who died is killed by playing in an arena.
			final Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
			
			if (room.getRoomType() == RoomType.TDM) {
				purgeEffects(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 2, 4));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 2, 4));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 2, 4));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 2, 0));
				
				((TdmRoom) room).resetKit(p);
				
				Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
				    public void run() {
				    	if (Room.PLAYERS.get(p) != null) {
				    		purgeEffects(p);
				    	}
				    }
				}, 40L); // Remove invincibility in 2 seconds.
			}
			
		} else {
			purgeEffects(p);
			p.teleport(Config.getGlobalLobby()); // Teleport player to main spawn
			p.setFallDistance(0F);

		}		
	}
}