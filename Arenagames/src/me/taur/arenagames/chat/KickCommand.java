package me.taur.arenagames.chat;

import me.taur.arenagames.player.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("kick")) {
			if (!(s instanceof Player)) {
				if (!s.hasPermission("arenagames.mod")) {
					s.sendMessage(ChatUtil.basicErrorMsg("You have no permission."));
					return true;

				}
			} else {
				Player p = (Player) s;
				if (!Permission.isModerator(p)) {
					s.sendMessage(ChatUtil.basicErrorMsg("You have no permission."));
					return true;
					
				}
			} 
			
			if (arg.length < 1) {
				s.sendMessage(ChatUtil.cmdUsageMsg("/kick [player] <message>"));
				return true;
				
			}
			
			OfflinePlayer op = Bukkit.getOfflinePlayer(arg[0]);
			if (!op.isOnline()) {
				s.sendMessage(ChatUtil.basicErrorMsg("You cannot kick an offline player, or this isn\'t the full username."));
				return true;
				
			}
			
			Player pl = Bukkit.getPlayerExact(arg[0]);
			String builder = "";
			
			for (int i = 1; i < arg.length; i++) {
				builder = builder + " " + arg[i];
			}
			
			pl.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "#Kicked#\n" + ChatColor.RESET + builder);
			return true;

		}
		
		return false;
	}
}