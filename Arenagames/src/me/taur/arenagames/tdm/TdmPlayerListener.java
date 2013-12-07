package me.taur.arenagames.tdm;

import me.taur.arenagames.Config;
import me.taur.arenagames.item.InvUtil;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TdmPlayerListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();

		if (Room.PLAYERS.containsKey(p)) {
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

			if (room.getRoomType() == RoomType.TDM) {
				room.removePlayer(p); // Only remove the gamemode's own players
				Room.PLAYERS.remove(p);
				
				TdmRoom r = (TdmRoom) room;
				if (room.isGameInProgress()) {
					if (room.getPlayers()[0] != null) {
						for (Player other : room.getPlayers()) {
							other.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + p.getName() + " has left this game.");
						}
					}

					if (room.getPlayersInRoom() == 0) {
						Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + room.getRoomId() + " queue has reopened.");
						r.resetRoom(true);
						
					}

				} else { // Left the queue
					if (room.getPlayers() != null) {
						for (Player other : room.getPlayers()) {
							if (other != null) {
								other.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " has left this queue.");
							}
						}
					}
					
					// Check if there are enough people in the room.
					int needed = room.getPlayersInRoom();
					if (needed > Config.getMinPlayersInWait(RoomType.TDM) - 1) {
						if (!room.isGameInWaiting()) {
							room.waitStartMessage(RoomType.TDM);
							room.setGameInWaiting(true);
							room.setWaitTimer(Config.getWaitTimer(RoomType.TDM));

						} else {
							room.waitStartMessage(p, RoomType.TDM);
						}
						
					} else {
						room.waitCancelledMessage(RoomType.TDM);
						room.setGameInWaiting(false);

					}
				}
				
				r.updateSigns();
				r.updateScoreboard(); // Update scoreboard
				r.getTeamtrackboard().remove(p);
				
				r.removePlayerScoreboard(p);

			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDropItem(PlayerDropItemEvent evt) {
		Player p = evt.getPlayer();
		
		if (!Room.PLAYERS.containsKey(p)) {
			return;
		}
		
		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
		if (room != null) {
			if (room.getRoomType() == RoomType.TDM) {
				PlayerInventory inv = p.getInventory();
				ItemStack is = evt.getItemDrop().getItemStack();
				int amt = is.getAmount();
				
				ItemStack[] isc = inv.getContents().clone();
				int slot = inv.getHeldItemSlot();
				
				if (amt > 1) {
					evt.setCancelled(true);
				} else {
					evt.getItemDrop().remove();
					inv.setItem(slot, is);
					
					for (int i = 0; i < inv.getContents().length; i++) {
						if (i > slot) {
							break;
						}
						
						if (isc[i].getAmount() > 0) {
							continue;
						}
						
						inv.setItem(i, new ItemStack(Material.AIR, 1));
						InvUtil.updatePlayerInv(p);

					}
				}
			}
		}
	}
}
