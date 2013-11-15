package me.taur.arenagames.player;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("player") || c.getName().equalsIgnoreCase("self")) {
			if (arg.length < 1) {
				String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/player <save>";
				
				if (!(s instanceof Player)) {
					s.sendMessage(ChatColor.stripColor(msg));
				} else {
					s.sendMessage(msg);
				}
				
				return true;
				
			}
			
			if (arg[0].equalsIgnoreCase("save")) {
				if (!(s instanceof Player)) {
					s.sendMessage("This command can only be run by a player.");
					return true;
					
				} else {
					Player p = (Player) s;
					
					if (PlayerData.isLoaded(p)) {
						PlayerData data = PlayerData.STORE.get(p);
						data.save(p);
						p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Your player data has been saved.");
						return true;
						
					}
				
					// Need to load the player's PlayerData.
					PlayerData data = new PlayerData(p);
					PlayerData.STORE.put(p, data);
					
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Your Player Data has been created.");
					return true;
					
				}
			}
			
			String msg = ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + "/player <save>";
			
			if (!(s instanceof Player)) {
				s.sendMessage(ChatColor.stripColor(msg));
			} else {
				s.sendMessage(msg);
			}
			
			return true;
		}
		
		return false;
	}
}