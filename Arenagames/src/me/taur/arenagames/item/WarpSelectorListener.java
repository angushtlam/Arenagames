package me.taur.arenagames.item;

import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WarpSelectorListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void kitSelector(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}
		
		ItemStack i = evt.getPlayer().getItemInHand();
		if (i.getType() == Material.AIR) {
			return;
		}
		
		if (!i.hasItemMeta()) {
			return;
		}
		
		String im = i.getItemMeta().getDisplayName();
		String warpsel = InvUtil.getWarpItem().getItemMeta().getDisplayName();
		
		if (im == null || warpsel == null) {
			return;
		}
		
		if (!im.equals(warpsel)) { // Make sure the item they're holding is the kit item.
			return;
		}
		
		Player p = evt.getPlayer();
		if (Room.PLAYERS.containsKey(p)) { // If the player is in a game and has the perk selector.
			if (Room.ROOMS.get(Room.PLAYERS.get(p)).isGameInProgress()) {
				p.getInventory().removeItem(i); // Remove it
			} else {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You cannot warp while in queue.");
			}
			
			return;
			
		}
		
		Warp.menu.open(p);
		p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.3F);

	}
}