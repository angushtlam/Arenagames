package me.taur.arenagames.lfl;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LflPlayerListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();

		if (Room.PLAYERS.containsKey(p)) {
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

			if (room.getRoomType() == RoomType.LFL) {
				room.removePlayer(p); // Only remove the gamemode's own players
				Room.PLAYERS.remove(p);
				
				LflRoom r = (LflRoom) room;
				if (room.isGameInProgress()) {
					if (room.getPlayers()[0] != null) {
						for (Player other : room.getPlayers()) {
							other.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + p.getName() + " has left this game.");
						}
					}

					if (room.getPlayersInRoom() == 0) {
						Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + room.getRoomId() + " queue has reopened.");

						r.getPointboard().remove(p);
						r.resetRoom(true);
					}

				} else { // Left the queue
					if (room.getPlayers() != null) {
						for (Player other : room.getPlayers()) {
							if (other != null) {
								other.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " has left this queue.");
							}
						}
					}


					// Check if there are enough people in the room.
					int needed = room.getPlayersInRoom();
					if (needed > Config.getMinPlayersInWait(RoomType.LFL) - 1) {
						if (!room.isGameInWaiting()) {
							room.waitStartMessage(RoomType.LFL);
							room.setGameInWaiting(true);
							room.setWaitTimer(Config.getWaitTimer(RoomType.LFL));

						} else {
							room.waitStartMessage(p, RoomType.LFL);
						}
						
					} else {
						room.waitCancelledMessage(RoomType.LFL);
						room.setGameInWaiting(false);

					}
				}
				
				r.updateSigns();
				room.removePlayerScoreboard(p);

			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDropItem(PlayerDropItemEvent evt) {
		Player p = evt.getPlayer();
		
		if (!Room.PLAYERS.containsKey(p)) {
			return;
		}
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
		if (room != null) {
			if (room.getRoomType() == RoomType.LFL) {
				evt.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDisableInteractWhenDead(PlayerInteractEvent evt) {
		Player p = evt.getPlayer();
		
		if (!Room.PLAYERS.containsKey(p)) {
			return;
		}
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
		if (room != null) {
			if (room.getRoomType() == RoomType.LFL) {
				LflRoom r = (LflRoom) room;
				if (r != null) {
					if (!r.getTimer().containsKey(p)) {
						evt.setCancelled(true);
					} else {
						if (r.getTimer().get(p) < 1) {
							evt.setCancelled(true);
						}
					}
				}
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDisableDamageWhenDead(EntityDamageEvent evt) {
		if (!(evt.getEntity() instanceof Player)) { // Players are only affected by this listener.
			return;
		}
		
		Player p = (Player) evt.getEntity();
		
		if (!Room.PLAYERS.containsKey(p)) {
			return;
		}
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
		if (room != null) {
			if (room.getRoomType() == RoomType.LFL) {
				LflRoom r = (LflRoom) room;
				if (r != null) {
					if (!r.getTimer().containsKey(p)) {
						evt.setCancelled(true);
					} else {
						if (r.getTimer().get(p) < 1) {
							evt.setCancelled(true);
						}
					}
				}
			}
		}
		
	}
}
