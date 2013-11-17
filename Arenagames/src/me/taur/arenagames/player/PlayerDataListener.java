package me.taur.arenagames.player;

import me.taur.arenagames.util.TimeUtil;

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
			new PlayerData(p);
		}
		
		PlayerData data = PlayerData.get(p);
		data.setFirstJoined(p.getFirstPlayed());
		data.setLastLogin(TimeUtil.currentMilliseconds());
		data.save(p);
		
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
			
			if (PlayerData.get(p) == null) {
				new PlayerData(p);
				
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();
		
		if (PlayerData.isLoaded(p)) {
			PlayerData data = PlayerData.get(p);
			data.save(p);
			
		}
		
		PlayerData.remove(p);
		
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
				PlayerData data = PlayerData.get(p);
				data.save(p);
				
			}
			
			PlayerData.remove(p);
			
		}
	}
}
