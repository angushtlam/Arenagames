package me.taur.arenagames.tdm;

import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TdmKitSelectorListener implements Listener {
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
		String kitsel = InvUtil.getKitSelector().getItemMeta().getDisplayName();
		
		if (im == null || kitsel == null) {
			return;
		}
		
		if (!im.equals(kitsel)) { // Make sure the item they're holding is the kit item.
			return;
		}
		
		Player p = evt.getPlayer();
		if (!Room.PLAYERS.containsKey(p)) { // If the player is not in a game and has the kit selector.
			p.getInventory().removeItem(i); // Remove it
			return;
			
		}
		
		Room r = Room.ROOMS.get(Room.PLAYERS.get(p));
		
		if (r == null) { // If the room doesn't exist
			p.getInventory().removeItem(i); // Remove it
			return;
			
		}
		
		if (r.getRoomType() == RoomType.TDM) { // If the player is in a TDM room.
			TdmUtil.kitMenu.open(p);
			p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.3F);
			return;
			
		}
		
	}
}