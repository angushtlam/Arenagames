package me.taur.arenagames.tdm;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class TdmModeration {
	
	public static HashMap<String, Integer> TDM_ROOM_TIMER = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TDM_WAIT_TIMER = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TDM_ROOM_MAP = new HashMap<String, Integer>();
	public static HashMap<Player, String> TDM_PLAYERS = new HashMap<Player, String>();
	public static HashMap<String, Integer> TDM_RED_TEAM = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TDM_BLUE_TEAM = new HashMap<String, Integer>();

	@EventHandler(priority = EventPriority.NORMAL)
	public void adminSign(BlockPlaceEvent evt) {
		Block b = evt.getBlock();
		
		if (b.getType().toString().contains("SIGN")) {
			Player p = evt.getPlayer();
			
			if (!p.hasPermission("arenagames.admin.sign")) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You have no permission to place signs.");
				return;
				
			}
			
			Sign sign = (Sign) b.getState();
			
			if (sign.getLine(0).equals("[TDM]")) {
				tdmSign(p, b);
				return;
				
			}
			
		}
		
	}
	
	public static void modeLoad() {
		for (int i = 1; i < TdmConfig.normalRoomCount(); i++) {
			TDM_ROOM_TIMER.put("normal" + i, -1);
			
		}
		
		for (int i = 1; i < TdmConfig.vipRoomCount(); i++) {
			TDM_ROOM_TIMER.put("vip" + i, -1);
			
		}
		
	}
	
	
	private static void tdmSign(Player p, Block signblock) {
		if (!TdmConfig.modeEnabled()) {
			p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Team Deathmatch is disabled.");
			
		}
		
		
		
	}
	
}
