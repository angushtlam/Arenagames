package me.taur.arenagames.chat;

import org.bukkit.ChatColor;

public enum ChatChannels {
	OFF(0, "disabled", "", ChatColor.BLACK),
	GLOBAL(1, "Global", "G/ ", ChatColor.WHITE),
	QUEUE(2, "Queue-Only", "Q/ ", ChatColor.BLUE),
	ADMIN(3, "Moderator-Chat", "M/ ", ChatColor.YELLOW);
	
	private int id;
	private String name;
	private String prefix;
	private ChatColor color;
	
	ChatChannels(int id, String name, String prefix, ChatColor color) {
		this.id = id;
		this.name = name;
		this.prefix = prefix;
		this.color = color;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
}
