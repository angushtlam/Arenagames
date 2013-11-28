package me.taur.arenagames.util;

import org.bukkit.ChatColor;

public enum RoomType {
	FFA(ChatColor.DARK_RED, "Free For All", "FFA"),
	TDM(ChatColor.DARK_PURPLE, "Team Deathmatch", "TDM"),
	CRK(ChatColor.YELLOW, "Cranked", "Cranked");
	
	private ChatColor color;
	private String name;
	private String sign;
	
	RoomType(ChatColor color, String name, String sign) {
		this.color = color;
		this.name = name;
		this.sign = sign;
	}
	
	public ChatColor getColor() {
		return color;	
	}
	
	public String getName() {
		return name;
	}
	
	public String getSign() {
		return sign;
	}	
}
