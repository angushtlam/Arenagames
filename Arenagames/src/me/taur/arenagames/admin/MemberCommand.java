package me.taur.arenagames.admin;

import me.taur.arenagames.chat.ChatUtil;
import me.taur.arenagames.player.Permission;
import me.taur.arenagames.player.PlayerData;
import me.taur.arenagames.player.PlayerRank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MemberCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("member")) {
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

			if (arg.length != 1) { // Must have 1 argument only
				s.sendMessage(ChatUtil.cmdUsageMsg("/member [username]"));
				return true;

			}

			OfflinePlayer op = Bukkit.getOfflinePlayer(arg[0]);
			
			if (op.isOnline() || !op.hasPlayedBefore()) {
				s.sendMessage(ChatUtil.basicErrorMsg(op.getName() + " has never logged on the server and is not qualified for member."));
				return true;
				
			}

			PlayerData data = null;
			if (op.isOnline() && PlayerData.isLoaded((Player) op)) {
				data = PlayerData.get(op.getName());
			} else {
				data = new PlayerData(op.getName());
			}
			
			if (data.getPlayerRank() > PlayerRank.MEMBER.getRankNumber()) {
				s.sendMessage(ChatUtil.basicErrorMsg(op.getName() + " is already higher ranked and is not qualified for member."));
				return true;
				
			}
			
			if (data.getPlayerRank() == PlayerRank.MEMBER.getRankNumber()) {
				s.sendMessage(ChatUtil.basicErrorMsg(op.getName() + " is already a member."));
				return true;
				
			}
			
			if (data.getPlayerRank() < 0) {
				s.sendMessage(ChatUtil.basicErrorMsg(op.getName() + " is banned and is not qualified for member."));
				return true;
				
			}
			
			data.setPlayerRank(PlayerRank.MEMBER.getRankNumber());
			data.save(op.getName());
			
			s.sendMessage(ChatUtil.basicSuccessMsg(op.getName() + " is now a member."));
			return true;
			
		}
		
		return false;
		
	}
}
