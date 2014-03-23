package me.taur.arenagames.mastery;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MasterySelectorListener implements Listener {
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
		String sel = InvUtil.getMasteryItem().getItemMeta().getDisplayName();
		
		if (im == null || sel == null) {
			return;
		}
		
		if (!im.equals(sel)) { // Make sure the item they're holding is the kit item.
			return;
		}
		
		Player p = evt.getPlayer();
		if (Room.PLAYERS.containsKey(p)) { // If the player is in a game and has the perk selector.
			if (Room.ROOMS.get(Room.PLAYERS.get(p)).isGameInProgress()) {
				p.getInventory().removeItem(i); // Remove it
			} else {
				p.sendMessage(ChatUtil.basicErrorMsg("You cannot change masteries while in queue."));
			}
			
			return;
			
		}
		
		MasteryMenu.menu.open(p);
		p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.3F);

	}
}