package me.taur.arenagames.tdm;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

public class TdmSignListener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void arenaSigns(PlayerInteractEvent evt) {
		Block b = evt.getClickedBlock();
		
		if (b.getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) b.getState();
			
			if (!sign.getLine(0).equals("[TDM]")) {
				return;
				
			}
			
			if (!sign.getLine(1).equals("Join Game")) {
				return;
				
			}
			
			Player p = evt.getPlayer();
			String l2 = sign.getLine(2);
			
			if (!TdmModeration.TDM_ROOM_TIMER.containsKey(l2)) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This sign points to a Team Deathmatch Arena with an invalid name.");
				return;
				
			}
			
			int count = 0;
			
			for (Player room : TdmModeration.TDM_PLAYERS.keySet()) {
				if (TdmModeration.TDM_PLAYERS.get(room) == l2) {
					count++;
					
				}
				
			}
			
			if (count > TdmConfig.maxPlayers()) {
				p.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "This Team Deathmatch Arena is already full.");
				
			}
			
			TdmModeration.TDM_PLAYERS.put(p, l2);
			//TODO: Teleporting players
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "You joined Team Deathmatch room " + l2 + ".");
			
		}
		
	}

}
