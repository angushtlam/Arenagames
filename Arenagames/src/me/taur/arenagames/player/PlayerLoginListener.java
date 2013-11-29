package me.taur.arenagames.player;

import me.taur.arenagames.Config;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.perk.PerkEffect;
import me.taur.arenagames.room.Room;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
		
		if (!p.hasPlayedBefore()) {
			evt.setJoinMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + p.getName() + " has signed onto the server for the first time! Welcome!");
			PlayerEconomy.changeCash(p, 9000); // Give the player 9000 cash.
			
		} else {
			evt.setJoinMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + p.getName() + " has signed onto the server.");
		}
		
		if (p.getGameMode() != GameMode.SURVIVAL) {
			p.setGameMode(GameMode.SURVIVAL);
		}
		
		p.sendMessage(ChatColor.YELLOW + " --- SERVER NOTICE ---");
		p.sendMessage(ChatColor.YELLOW + "The server is conducting an Open Beta test. All player data will be deleted at the end of the test." +
				" You may find many incomplete and broken features on the server. We are trying to fix and adjust these issues before the official" +
				" launch of the server. Sorry for the inconvienence.");
		p.sendMessage(ChatColor.YELLOW + " --- ------------- ---");
		
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
		
		if (evt.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) {
			return;
		}
		
		String name = evt.getItemDrop().getItemStack().getItemMeta().getDisplayName();
		
		if (name.equalsIgnoreCase(InvUtil.getProfileItem().getItemMeta().getDisplayName())) {
			evt.setCancelled(true);
			return;
			
		}
		
		if (name.equalsIgnoreCase(InvUtil.getPerkItem().getItemMeta().getDisplayName())) {
			evt.setCancelled(true);
			return;
			
		}
		
	}
}
