package me.taur.arenagames.ffa;

import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class FfaPlayerListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDropItem(PlayerDropItemEvent evt) {
		Player p = evt.getPlayer();
		
		if (!Room.PLAYERS.containsKey(p)) {
			return;
			
		}
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
		if (room != null) {
			if (room.getRoomType() == RoomType.FFA) {
				evt.setCancelled(true);
				
			}
			
		}
	}
}
