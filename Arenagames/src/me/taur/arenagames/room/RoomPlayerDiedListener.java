package me.taur.arenagames.room;

import me.taur.arenagames.Config;
import me.taur.arenagames.ffa.FfaRoom;
import me.taur.arenagames.util.Players;
import me.taur.arenagames.util.RoomType;

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

public class RoomPlayerDiedListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDiedInArena(EntityDamageEvent evt) {
		if (!(evt.getEntity() instanceof Player)) { // Players are only affected by this listener.
			return;
			
		}
		
		Player p = (Player) evt.getEntity();
		if ((p.getHealth() - evt.getDamage()) > 0) { // Check if player was killed.
			return;
			
		}
		
		if (!Room.PLAYERS.containsKey(p)) { // Only apply when the player who died is killed by playing in an arena.
			return;
			
		}
		
		// Make sure the event is cancelled and the player won't really die.
		evt.setCancelled(true);
		
		// Set effects
		Players.respawnEffects(p);
		
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
						    		if (Room.PLAYERS.containsKey(d)) { // If the player who killed the player is playing
						    			((FfaRoom) room).playerDied(p, d); // Tell the room that the player has been slain by another player.
							    		
							    	} else {
							    		((FfaRoom) room).playerDied(p); // The player died by themselves.
							    		
							    	}
						    		
						    		
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
		} else { // If the player is not in a game room and died.
			p.teleport(Config.getGlobalLobby());
			
		}
	}
	
}
