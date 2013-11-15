package me.taur.arenagames.ffa;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.InvUtil;
import me.taur.arenagames.util.ParticleEffect;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class FfaSignListener implements Listener {	
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
		if (sign.getLine(0).contains("[FFA]")) {
			Player p = evt.getPlayer();

			if (!p.hasPermission("arenagames.join")) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
				return;

			}

			String l1 = ChatColor.stripColor(sign.getLine(1)); // Remove the fancyness
			Room r = Room.ROOMS.get(l1.toLowerCase());

			if (r == null) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This sign points to a Free For All queue that doesn\'t exist.");
				return;

			}

			String pq = Room.PLAYERS.get(p);
			if (pq != null) {
				if (pq != r.getRoomId()) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You're already in a different queue: " + pq);
					p.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + "To leave a queue type /queue leave");
					return;
					
				}
			}

			if (r.getRoomType() != RoomType.FFA) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This sign points to an invalid Free For All queue.");
				return;
				
			}

			if (r.getRoomId().contains("-p")) {
				if (!p.hasPermission("arenagames.premium")) {
					p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This queue is for Premium only.");
					return;
					
				}
			}

			if (r.isPlayerInRoom(p)) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You're already in this queue.");
				return;
				
			}

			if (r.isGameInProgress()) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This arena has already started.");
				return;
				
			}

			if (r.getPlayersInRoom() + 1 > Config.getPlayerLimit(r.getRoomType())) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This queue is full.");
				return;
				
			}

			FfaRoom room = (FfaRoom) r;
			String roomId = room.getRoomId();

			Player[] other = room.getPlayers(); // Get players that are in the room before the player is added.
			room.addPlayer(p);
			Room.PLAYERS.put(p, roomId);
			
			room.updateSigns(); // Update signs.
			room.updateScoreboard();
			room.setPlayerScoreboard(p); // Add scoreboards to players
			
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You joined Free For All queue " + roomId + ". ");

			if (other != null) { // Make sure it wasn't empty before the player joined.
				for (Player pl : other) {
					if (pl != null) { // Null check
						pl.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " joined your queue. Queue " + room.getPlayers().length + "/" + Config.getPlayerLimit(RoomType.FFA));
					}
				}
			}

			int needed = room.getPlayersInRoom();
			if (needed > Config.getMinPlayersInWait(RoomType.FFA) - 1) {
				if (!room.isGameInWaiting()) {
					room.waitStartMessage(RoomType.FFA);
					room.setGameInWaiting(true);
					room.setWaitTimer(Config.getWaitTimer(RoomType.FFA));

				} else {
					room.waitStartMessage(p, RoomType.FFA);
				}
			} else {
				room.waitCancelledMessage(RoomType.FFA);
				room.setGameInWaiting(false);

			}
			
			// Reminder for players to choose their kits.
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Remember to pick your kit by right clicking on the Kit Selector (Nether Star) item!");
			
			InvUtil.setLobbyInventory(p);
			
			PlayerInventory inv = p.getInventory();
			inv.setItem(8, InvUtil.getKitSelector());
			InvUtil.updatePlayerInv(p);
			
			Location[] blocs = FfaConfig.getSignsStored(room.getRoomId());
			if (blocs != null) {
				for (Location bloc : blocs) {
					ParticleEffect.HAPPY_VILLAGER.display(bloc.add(0.5, 1.0, 0.5), 0.1F, 0.1F, 0.1F, 10, 3);
				}
			}
		}
	}
}