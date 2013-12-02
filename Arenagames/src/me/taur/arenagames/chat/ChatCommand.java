package me.taur.arenagames.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
	public boolean onCommand(CommandSender s, Command c, String l, String[] arg) {
		if (c.getName().equalsIgnoreCase("chat") || c.getName().equalsIgnoreCase("ch") || c.getName().equalsIgnoreCase("c")) {
			if (arg.length < 1) {
				s.sendMessage(ChatUtil.cmdUsageMsg("/chat <global/queue/mod/off>"));
				return true;
				
			}

			if (!(s instanceof Player)) {
				s.sendMessage("This command can only be run by a player.");
				return true;
				
			}
			
			for (ChatChannels ch : ChatChannels.values()) {
				if (arg[0].toUpperCase().startsWith(ch.toString().substring(0, 1))) {
					s.sendMessage(ChatUtil.basicInfoMsg("Your chat is now " + ch.getName() + "."));
					ChatUtil.STORE.put(s.getName(), ch.getId());
					return true;
					
				}
			}
			
			s.sendMessage(ChatUtil.cmdUsageMsg("/chat <global/queue/mod/off>"));
			return true;
			
		}
		
		return false;
	}
}