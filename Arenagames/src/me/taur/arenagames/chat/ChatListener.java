package me.taur.arenagames.chat;

import java.util.HashSet;
import java.util.Set;

import me.taur.arenagames.player.Permission;
import me.taur.arenagames.room.Room;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class ChatListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
    public void formatChat(AsyncPlayerChatEvent evt) {
		evt.setCancelled(true); // Take over the chat printing.
		
		Player p = evt.getPlayer();
		String name = p.getDisplayName();
		String msg = evt.getMessage();
		String sep = ChatColor.GRAY + "" + ChatColor.BOLD + ":" + ChatColor.RESET;
		
		if (Permission.isAdmin(p)) {
			name = ChatColor.DARK_RED + "\u265a" + ChatColor.BOLD + name + ChatColor.RESET;
		} else if (Permission.isModerator(p)) {
			name = ChatColor.DARK_RED + "\u2654" + ChatColor.BOLD + name + ChatColor.RESET;
		} else if (Permission.isPermaPremium(p)) {
			name = ChatColor.YELLOW + "\u2667" + ChatColor.UNDERLINE + name + ChatColor.RESET;
		} else if (Permission.isPremium(p)) {
			name = ChatColor.GOLD + "\u2667" + ChatColor.UNDERLINE + name + ChatColor.RESET;
		} else {
			name = ChatColor.GRAY + name + ChatColor.RESET;
		}

		ChatChannels ch = ChatChannels.GLOBAL;
		
		for (ChatChannels cch : ChatChannels.values()) {
			if (ChatUtil.STORE.get(p.getName()) != null) {
				if (cch.getId() == ChatUtil.STORE.get(p.getName()) ) {
					ch = cch;
				}
			}
		}
		
		if (ch.equals(ChatChannels.OFF)) {
			p.sendMessage(ChatUtil.basicErrorMsg("You disabled your chat. Please change your channel to talk."));
			p.sendMessage(ChatUtil.cmdUsageMsg("/chat <global/queue/admin/off>"));
			return;
			
		}
		
		if (Permission.isModerator(p)) {
			msg = ch.getColor() + "" + ChatColor.ITALIC + msg; // Add colors to the player's message if the channel is not overridden.
		} else {
			msg = ch.getColor() + msg;
		}
		
		if (ch.equals(ChatChannels.GLOBAL)) {
			Set<Player> ls = new HashSet<Player>();
			ls.add(p); // Send the message to the player.
			
			if (Bukkit.getOnlinePlayers() != null) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (ChatUtil.STORE.get(pl.getName()) != null) {
						if (ChatUtil.STORE.get(pl.getName()).intValue() == ChatChannels.GLOBAL.getId()) {
							if (pl != null) {
								ls.add(pl);
							}
						}
					}
				}
			}
			
			String format = ch.getColor() + ch.getPrefix() + ChatColor.RESET + name + sep + " " + msg;
			for (Player pl : ls) {
				if (pl != null) {
					pl.sendMessage(format);
					
				}			
			}
			
			ChatUtil.sendToLog("Chat", format);
			return;
			
		} else if (ch.equals(ChatChannels.QUEUE)) {
			if (!Room.PLAYERS.containsKey(p)) {
				p.sendMessage(ChatUtil.basicErrorMsg("You are not in queue. Please change your channel to talk."));
				p.sendMessage(ChatUtil.cmdUsageMsg("/chat <global/queue/admin/off>"));
				return;
				
			}
			
			Room room = Room.ROOMS.get(Room.PLAYERS.get(p));
			Set<Player> ls = new HashSet<Player>();
			ls.add(p); // Send the message to the player.
			
			if (room.getPlayers() != null) {
				for (Player pl : room.getPlayers()) {
					if (ChatUtil.STORE.get(pl.getName()) != null) {
						if (!(ChatUtil.STORE.get(pl.getName()).intValue() == ChatChannels.OFF.getId())) {
							if (pl != null) {
								ls.add(pl);
							}
						}
					}
				}
			}

			String format = ch.getColor() + ch.getPrefix() + ChatColor.RESET + name + sep + " " + msg;
			for (Player pl : ls) {
				if (pl != null) {
					pl.sendMessage(format);
				}			
			}
			
			ChatUtil.sendToLog("Chat", format);
			return;
			
		} else if (ch.equals(ChatChannels.MOD)) {
			Set<Player> ls = new HashSet<Player>();
			ls.add(p); // Send the message to the player.
			
			if (Bukkit.getOnlinePlayers() != null) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (Permission.isModerator(pl)) { // This channel will send all messages to moderators even if their channels are off.
						ls.add(pl);
						continue;
						
					}
					
					if (ChatUtil.STORE.get(pl.getName()) != null) {
						if (ChatUtil.STORE.get(pl.getName()).intValue() == ChatChannels.MOD.getId()) {
							if (pl != null) {
								ls.add(pl);
							}	
						}
					}
				}
			}
			

			String format = ch.getColor() + ch.getPrefix() + ChatColor.RESET + name + sep + " " + msg;
			for (Player pl : ls) {
				if (pl != null) {
					pl.sendMessage(format);
				}			
			}
			
			ChatUtil.sendToLog("Chat", format);
			return;
			
		} else {
			p.sendMessage(ChatUtil.basicErrorMsg("You are not in queue. Please change your channel to talk."));
			p.sendMessage(ChatUtil.cmdUsageMsg("/chat <global/queue/admin/off>"));
			return;
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void defaultChannelToGlobalOnJoin(PlayerJoinEvent evt) {
		Player p = evt.getPlayer();
		ChatUtil.STORE.put(p.getName(), ChatChannels.GLOBAL.getId());
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void serverOn(PluginEnableEvent evt) {
		if (Bukkit.getOnlinePlayers() == null) {
			return;
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == null) {
				return;
			}
			
			ChatUtil.STORE.put(p.getName(), ChatChannels.GLOBAL.getId());
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerLoggedOff(PlayerQuitEvent evt) {
		Player p = evt.getPlayer();
		ChatUtil.STORE.remove(p);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void serverOff(PluginDisableEvent evt) {
		if (Bukkit.getOnlinePlayers() == null) {
			return;
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p == null) {
				return;
			}
			
			ChatUtil.STORE.remove(p);
			
		}
	}
	
}
