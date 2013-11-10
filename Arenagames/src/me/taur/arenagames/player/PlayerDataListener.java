package me.taur.arenagames.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PlayerDataListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void playerLoggedIn(PlayerJoinEvent evt) {
		Player p = evt.getPlayer();
		
		if (!PlayerData.isLoaded(p)) {
			PlayerData data = new PlayerData(p);
			PlayerData.STORE.put(p, data);
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void serverOn(PluginEnableEvent evt) {
		if (Bukkit.getOnlinePlayers() == null) {
			return;
			
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == null) {
				return;
				
			}
			
			
			if (PlayerData.STORE.get(p.getName()) == null) {
				PlayerData data = new PlayerData(p);
				PlayerData.STORE.put(p, data);
				
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();
		
		if (PlayerData.isLoaded(p)) {
			PlayerData data = PlayerData.STORE.get(p);
			data.save(p);
			
		}
		
		PlayerData.STORE.remove(p);
		
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void serverOff(PluginDisableEvent evt) {
		if (Bukkit.getOnlinePlayers() == null) {
			return;
			
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == null) {
				return;
				
			}
			
			if (PlayerData.isLoaded(p)) {
				PlayerData data = PlayerData.STORE.get(p);
				data.save(p);
				
			}
			
			PlayerData.STORE.remove(p);
		}
	}
	
}
