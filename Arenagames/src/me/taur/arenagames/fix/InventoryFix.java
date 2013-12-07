package me.taur.arenagames.fix;

import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;

import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class InventoryFix implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void preventPlayersFromMovingInventory(InventoryClickEvent evt) {
		HumanEntity he = evt.getWhoClicked();
		
		if (he instanceof Player) {
			Player p = (Player) he;
			
			if (p.getGameMode() == GameMode.ADVENTURE) {
				if (Room.PLAYERS.containsKey(p)) {
					if (evt.getSlotType().equals(SlotType.ARMOR)) {
						evt.setCancelled(true);
						InvUtil.updatePlayerInv(p);
						return;
						
					}
				}
				
				evt.setCancelled(true); // Cancel the player from moving items in the lobby.
				InvUtil.updatePlayerInv(p);
				
			}
		}
	}
}
