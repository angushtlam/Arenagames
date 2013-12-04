package me.taur.arenagames.shop;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopSignListener implements Listener {	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void joinRoomSign(PlayerInteractEvent evt) {
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block b = evt.getClickedBlock();
		if (!b.getType().name().contains("SIGN")) {
			return;
		}

		Sign sign = (Sign) b.getState();
		if (sign.getLine(0).contains("[Perk Shop]")) {
			Player p = evt.getPlayer();
			Shop.shopMenu.open(p);
			
		}
	}
	
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
			
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have created a Perk Shop sign.");
			evt.setLine(0, ChatColor.AQUA + "" + ChatColor.BOLD + "[Perk Shop]");
			evt.setLine(1, "Right click on");
			evt.setLine(2, "this sign to");
			evt.setLine(3, "open shop.");
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void adminDestroySign(BlockBreakEvent evt) {
		Block b = evt.getBlock();
		Player p = evt.getPlayer();
		
		if (!b.getType().name().contains("SIGN")) {
			return;
		}
		
		Sign sign = (Sign) b.getState();
		String l0 = ChatColor.stripColor(sign.getLine(0));
		
		if (l0.equals("[Perk Shop]")) {
			if (!p.hasPermission("arenagames.admin")) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
				evt.setCancelled(true);
				return;
				
			}
			
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You have removed a Perk Shop sign.");
			
		}
	}
}