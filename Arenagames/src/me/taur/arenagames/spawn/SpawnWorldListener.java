package me.taur.arenagames.spawn;

import me.taur.arenagames.Arenagames;
import me.taur.arenagames.Config;
import me.taur.arenagames.event.PerkHatChangeEvent;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnWorldListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void removeDeathMessageInMainSpawn(PlayerDeathEvent evt) {
		Player p = evt.getEntity();

		if (Room.PLAYERS.containsKey(p)) {
			if (Room.ROOMS.get(Room.PLAYERS.get(p)).isGameInProgress()) {
				return;
			}
		}

		if (!p.getLocation().getWorld().equals(Config.getGlobalLobby().getWorld())) {
			return;
		}

		evt.setDeathMessage("");
		evt.getDrops().clear();

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void spawnProperlyMainSpawn(PlayerRespawnEvent evt) {
		final Player p = evt.getPlayer();

		if (Room.PLAYERS.containsKey(p)) {
			if (Room.ROOMS.get(Room.PLAYERS.get(p)).isGameInProgress()) {
				return;
			}
		}

		if (!p.getLocation().getWorld().equals(Config.getGlobalLobby().getWorld())) {
			return;
		}
		
		evt.setRespawnLocation(Config.getGlobalLobby());
		
		Bukkit.getScheduler().runTaskLater(Arenagames.plugin, new Runnable() {
			public void run() {
				InvUtil.setLobbyInventory(p);
				
				PerkHatChangeEvent event = new PerkHatChangeEvent(p); // Call the event to update the player's hat.
				Bukkit.getPluginManager().callEvent(event);
				
			}
		}, 5L);

	}
}
