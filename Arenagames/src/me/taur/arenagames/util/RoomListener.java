package me.taur.arenagames.util;

import me.taur.arenagames.Config;
import me.taur.arenagames.ffa.FfaRoom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RoomListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedIn(PlayerJoinEvent evt) {
		evt.setJoinMessage(""); // I hate this.
		evt.getPlayer().teleport(Config.getGlobalLobby()); // Teleport people to lobby when they join
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		evt.setQuitMessage(""); // I hate this.
		
		Player p = evt.getPlayer();
		
		if (Room.PLAYERS.containsKey(p)) {
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

			room.removePlayer(p);
			Room.PLAYERS.remove(p);

			if (room.isGameInProgress()) {
				if (room.getPlayers()[0] != null) {
					for (Player other : room.getPlayers()) {
						other.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + p.getName() + " has left this game.");
					}
				}
				
				if (room.getPlayersInRoom() == 0) {
					Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + room.getRoomId() + " queue has reopened.");
					
					if (room.getRoomType() == RoomType.FFA) {
						((FfaRoom) room).resetRoom(true);
						
					}
				}
				
			} else { // Left the queue
				if (room.getPlayers() != null) {
					for (Player other : room.getPlayers()) {
						other.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " has left this queue.");
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDiedInArena(EntityDamageEvent evt) {
		if (!(evt.getEntity() instanceof Player)) { // Players are only affected by this listener.
			return;
			
		}
		
		Player p = (Player) evt.getEntity();
		if ((p.getHealth() - evt.getDamage()) > 0) { // Check if player was killed.
			return;
			
		}
		
		// Make sure the event is cancelled and the player won't really die.
		evt.setCancelled(true);
		
		// Set effects
		p.setFireTicks(0);
		p.getActivePotionEffects().clear();
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 800));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 100));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2));
		
		
		if (Room.PLAYERS.containsKey(p)) { // If player is in a room
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
			
			if (room != null) {
				if (room.isGameInProgress()) { // Check if the game is in progress.
					DamageCause c = evt.getCause();
					
					if (c.equals(DamageCause.ENTITY_ATTACK)) {
						if (evt instanceof EntityDamageByEntityEvent) {
							EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
						    Entity damager = edbeEvent.getDamager();

						    if (damager instanceof Player) {
						    	Player d = (Player) damager;
						    	
						    	if (room.getRoomType() == RoomType.FFA) {
						    		((FfaRoom) room).playerDied(p, d); // Tell the room that the player has been slain by another player.
						    		
						    	}
						        
							} else if (damager instanceof Monster) {
								Monster d = (Monster) damager;
						    	
								if (room.getRoomType() == RoomType.FFA) {
						    		((FfaRoom) room).playerDied(p, d.getType()); // Tell the room that the player has been slain by an monster.
						    		
						    	}
						        
						    }
						    
						} else {
							if (room.getRoomType() == RoomType.FFA) {
								((FfaRoom) room).playerDied(p); // Tell the room that the player has been slain mysteriously.
					    		
					    	}	
							
						}
					} else if (c.equals(DamageCause.ENTITY_EXPLOSION)) {
						if (room.getRoomType() == RoomType.FFA) {
							((FfaRoom) room).playerDied(p, EntityType.CREEPER); // Tell the room that the player has been slain by a Creeper.
				    		
				    	}	
						
					} else {
						if (room.getRoomType() == RoomType.FFA) {
							((FfaRoom) room).playerDied(p, c); // Tell the room that the player has been slain mysteriously.
				    		
				    	}
						
					}
					
				}
			}
		}
	}
	
}
