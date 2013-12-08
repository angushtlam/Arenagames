package me.taur.arenagames.chat;

import org.bukkit.Bukkit;

public class MuteActive {
	public static void run() {
		for (String name : ChatUtil.MUTE.keySet()) {
			if (ChatUtil.MUTE.get(name) > 1) { // Subtract from mute timer.
				ChatUtil.MUTE.put(name, ChatUtil.MUTE.get(name) - 1);
				return;
				
			}
			
			if (Bukkit.getPlayerExact(name).isOnline()) {
				Bukkit.getPlayerExact(name).sendMessage(ChatUtil.basicInfoMsg("You have been unmuted."));
			}
			
			ChatUtil.MUTE.remove(name);
			
		}
	}
	
}
