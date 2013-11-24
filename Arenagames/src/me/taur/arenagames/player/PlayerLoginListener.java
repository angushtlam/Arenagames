package me.taur.arenagames.player;

import me.taur.arenagames.Config;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.perk.PerkEffect;
import me.taur.arenagames.room.Room;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerLoginListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedIn(PlayerJoinEvent evt) {
		Player p = evt.getPlayer();
		evt.setJoinMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + p.getName() + " has signed onto the server.");
		
		InvUtil.setLobbyInventory(p);
		
		for (PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		p.setLevel(0);
		p.setExp((float) 0.0);
		p.teleport(Config.getGlobalLobby()); // Teleport people to lobby when they join
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();
		evt.setQuitMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + p.getName() + " has signed off the server.");
		
		// Remove stored player.
		PerkEffect.MENU_STORE.remove(p);
		PerkEffect.ACTIVE_EFFECT_PERK.remove(p);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDropItemInSpawn(PlayerDropItemEvent evt) {
		Player p = evt.getPlayer();
		
		if (Room.PLAYERS.containsKey(p)) {
			return;
		}
		
		String name = evt.getItemDrop().getItemStack().getItemMeta().getDisplayName();
		
		if (name.equalsIgnoreCase(InvUtil.getProfileItem().getItemMeta().getDisplayName())) {
			evt.setCancelled(true);
		}
		
		if (name.equalsIgnoreCase(InvUtil.getPerkItem().getItemMeta().getDisplayName())) {
			evt.setCancelled(true);
		}
		
	}
}
