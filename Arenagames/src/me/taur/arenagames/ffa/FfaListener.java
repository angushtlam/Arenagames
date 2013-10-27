package me.taur.arenagames.ffa;

import me.taur.arenagames.Config;
import me.taur.arenagames.util.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FfaListener implements Listener {	
	@EventHandler(priority = EventPriority.NORMAL)
	public void joinRoomSign(PlayerInteractEvent evt) {
		Block b = evt.getClickedBlock();
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		if (b.getType() != Material.WALL_SIGN) {
			return;
		}
		
		Sign sign = (Sign) b.getState();
		
		if (!sign.getLine(0).contains("[FFA]")) {
			return;
			
		}
		
		Player p = evt.getPlayer();
		
		if (!p.hasPermission("arenagames.join")) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission.");
			return;
			
		}
		
		String l1 = sign.getLine(1);
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
		
		p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You joined Free For All queue " + roomId + ". ");
		
		if (other != null) { // Make sure it wasn't empty before the player joined.
			for (Player pl : other) {
				pl.sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC + p.getName() + " joined your queue. Queue " + room.getPlayers().length + "/"
						+ Config.getPlayerLimit(RoomType.FFA));
				
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
		
	}
	
}
