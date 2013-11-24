package me.taur.arenagames.shop;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreateListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void adminPlaceSign(SignChangeEvent evt) {
		Block b = evt.getBlock();
		Player p = evt.getPlayer();
		String l0 = evt.getLine(0);
		
		if (l0.equals("[Perk Shop]")) {
			if (!p.hasPermission("arenagames.admin")) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
				b.breakNaturally();
				return;
				
			}
			
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have created a queue sign for.");
			
		}
		
	}
}
