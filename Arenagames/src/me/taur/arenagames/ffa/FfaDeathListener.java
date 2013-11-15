package me.taur.arenagames.ffa;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FfaDeathListener implements Listener {
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
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

		if (room != null) {
			if (room.getRoomType() == RoomType.FFA) {
				// Make sure the event is cancelled and the player won't really die.
				evt.setCancelled(true);
				
				FfaRoom r = (FfaRoom) room;
				if (room.isGameInProgress()) { // Check if the game is in progress.
					DamageCause c = evt.getCause();

					if (c.equals(DamageCause.ENTITY_ATTACK)) {
						if (evt instanceof EntityDamageByEntityEvent) {
							EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
							Entity damager = edbeEvent.getDamager();

							if (damager instanceof Player) {
								Player d = (Player) damager;
								if (Room.PLAYERS.containsKey(d)) { // If the player who killed the player is playing
									r.playerDied(p, d); // Tell the room that the player has been slain by another player.
								} else {
									r.playerDied(p); // The player died by themselves.
								}

							} else if (damager instanceof Monster) {
								Monster d = (Monster) damager;
								r.playerDied(p, d.getType()); // Tell the room that the player has been slain by an monster.
								
							}

						} else {
							r.playerDied(p); // Tell the room that the player has been slain mysteriously.
						}
						
					} else if (c.equals(DamageCause.PROJECTILE)) {
						if (evt instanceof EntityDamageByEntityEvent) {
							EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
							Entity damager = edbeEvent.getDamager();
							
							if (damager instanceof Arrow) {
								Arrow a = (Arrow) damager;
								if (a.getShooter() instanceof Player) {
									Player d = (Player) a.getShooter();

									if (Room.PLAYERS.containsKey(d)) { // If the player who killed the player is playing
										if (p.getName() == d.getName()) {
											r.playerDied(p); // The player died by themselves.
										} else {
											r.playerDied(p, d); // Tell the room that the player has been slain by another player.
										}
										
									} else {
										r.playerDied(p); // The player died by themselves.
									}

								} else {
									r.playerDied(p, a.getShooter().getType()); // Tell the room that the player has been slain by an monster.
								}
							}
						}
						
					} else if (c.equals(DamageCause.MAGIC)) {
						if (evt instanceof EntityDamageByEntityEvent) {
							EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent) evt;
							Entity damager = edbeEvent.getDamager();
							
							if (damager instanceof ThrownPotion) {
								ThrownPotion pot = (ThrownPotion) damager;
								if (pot.getShooter() instanceof Player) {
									Player d = (Player) pot.getShooter();

									if (Room.PLAYERS.containsKey(d)) { // If the player who killed the player is playing
										if (p.getName() == d.getName()) {
											r.playerDied(p); // The player died by themselves.
										} else {
											r.playerDied(p, d); // Tell the room that the player has been slain by another player.
										}
										
									} else {
										r.playerDied(p); // The player died by themselves.
									}

								} else {
									r.playerDied(p, c); // Tell the room that the player has been slain by an monster.
								}
							}
						}
						
					} else if (c.equals(DamageCause.ENTITY_EXPLOSION)) {
						r.playerDied(p, EntityType.CREEPER); // Tell the room that the player has been slain by a Creeper.
					} else {
						r.playerDied(p, c); // Tell the room that the player has been slain by something else.
					}
					
				}
			}
		} else { // If the player is not in a game room and died.
			p.teleport(Config.getGlobalLobby());
		}
	}
}