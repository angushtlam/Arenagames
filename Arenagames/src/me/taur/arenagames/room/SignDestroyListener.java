package me.taur.arenagames.room;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.ffa.FfaRoom;
import me.taur.arenagames.tdm.TdmRoom;
import me.taur.arenagames.util.RoomType;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SignDestroyListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void adminDestroySign(BlockBreakEvent evt) {
		Block b = evt.getBlock();
		Player p = evt.getPlayer();
		
		if (!b.getType().name().contains("SIGN")) {
			return;
		}
		
		Sign sign = (Sign) b.getState();
		String l0 = ChatColor.stripColor(sign.getLine(0));
		
		if (l0.equals("[" + RoomType.FFA.getSign() + "]")) {
			if (!p.hasPermission("arenagames.admin")) {
				p.sendMessage(ChatUtil.basicErrorMsg("You have no permission."));
				evt.setCancelled(true);
				return;
				
			}
			
			String l1 = ChatColor.stripColor(sign.getLine(1).toLowerCase());
			if (!Room.ROOMS.containsKey(l1)) {
				return;
			}
			
			Room r = Room.ROOMS.get(l1);
			if (r.getRoomType() == RoomType.FFA) {
				FfaRoom room = (FfaRoom) r;
				room.updateSigns();
				
				p.sendMessage(ChatUtil.basicSuccessMsg("You have removed a queue sign for " + r.getRoomId() + "."));
			}
			
			return;
			
		}
		
		if (l0.equals("[" + RoomType.TDM.getSign() + "]")) {
			if (!p.hasPermission("arenagames.admin")) {
				p.sendMessage(ChatUtil.basicErrorMsg("You have no permission."));
				evt.setCancelled(true);
				return;
				
			}
			
			String l1 = ChatColor.stripColor(sign.getLine(1).toLowerCase());
			if (!Room.ROOMS.containsKey(l1)) {
				return;
			}
			
			Room r = Room.ROOMS.get(l1);
			if (r.getRoomType() == RoomType.TDM) {
				TdmRoom room = (TdmRoom) r;
				room.updateSigns();
				
				p.sendMessage(ChatUtil.basicSuccessMsg("You have removed a queue sign for " + r.getRoomId() + "."));
			}
			
			return;
			
		}
		
	}
}