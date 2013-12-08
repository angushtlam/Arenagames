package me.taur.arenagames.chat;

import me.taur.arenagames.player.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("mute")) {
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
			
			if (arg.length < 1 || arg.length > 2) {
				s.sendMessage(ChatUtil.cmdUsageMsg("/mute [player] (minutes)"));
				return true;
				
			}
			
			if (ChatUtil.MUTE.containsKey(arg[0])) {
				ChatUtil.MUTE.remove(arg[0]);
				s.sendMessage(ChatUtil.basicSuccessMsg("You have unmuted " + arg[0] + "."));
				
				if (Bukkit.getPlayerExact(arg[0]).isOnline()) {
					Bukkit.getPlayerExact(arg[0]).sendMessage(ChatUtil.basicInfoMsg("You have been unmuted."));
				}
				
				return true;
				
			}
			
			Player pl = Bukkit.getPlayer(arg[0]);
			if (ChatUtil.MUTE.containsKey(pl.getName())) {
				ChatUtil.MUTE.remove(pl.getName());
				s.sendMessage(ChatUtil.basicSuccessMsg("You have unmuted " + pl.getName() + "."));
				
				if (Bukkit.getPlayerExact(pl.getName()).isOnline()) {
					Bukkit.getPlayerExact(pl.getName()).sendMessage(ChatUtil.basicInfoMsg("You have been unmuted."));
				}
				
				return true;
				
			}
			
			if (!pl.isOnline()) {
				s.sendMessage(ChatUtil.basicErrorMsg("You cannot mute an offline player."));
				return true;
				
			}
			
			if (arg.length == 2) {
				int duration = 1;
				
				try {
					Integer.parseInt(arg[1]);
				} catch (NumberFormatException e) { 
					s.sendMessage(ChatUtil.basicErrorMsg("You have entered an invalid duration."));
					return true;

				}
				
				if (Integer.valueOf(arg[1]) < duration) {
					s.sendMessage(ChatUtil.basicSuccessMsg("You have entered an duration less than 1. Muting " + pl.getName() + " for 1 minute."));
					ChatUtil.MUTE.put(pl.getName(), 60 * duration);
					return true;
					
				} else {
					duration = Integer.valueOf(arg[1]);
					s.sendMessage(ChatUtil.basicSuccessMsg("You have muted " + pl.getName() + " for " + duration + " " + (duration == 1 ? "minute" : "minutes") + "."));
					ChatUtil.MUTE.put(pl.getName(), 60 * duration);
					return true;
					
				}
				
			} else {
				s.sendMessage(ChatUtil.basicSuccessMsg("You have muted " + pl.getName() + " for 2 minutes."));
				ChatUtil.MUTE.put(pl.getName(), 60 * 2);
				return true;
				
			}
		}
		
		return false;
	}
}