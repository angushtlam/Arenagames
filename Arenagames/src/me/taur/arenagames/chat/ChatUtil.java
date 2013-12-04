package me.taur.arenagames.chat;

import java.util.HashMap;

import org.bukkit.ChatColor;

public class ChatUtil {
	public static HashMap<String, Integer> STORE = new HashMap<String, Integer>();
	
	public static String basicInfoMsg(String msg) {
		return ChatColor.YELLOW + "" + ChatColor.ITALIC + msg;
	}
	
	public static String basicSuccessMsg(String msg) {
		return ChatColor.GREEN + "" + ChatColor.ITALIC + msg;
	}
	
	public static String basicImportantMsg(String msg) {
		return ChatColor.AQUA + "" + ChatColor.ITALIC + msg;
	}
	
	public static String basicErrorMsg(String msg) {
		return ChatColor.RED + "" + ChatColor.ITALIC + msg;
	}
	
	public static String gameInfoMsg(String msg) {
		return ChatColor.GOLD + "" + ChatColor.ITALIC + msg;
	}

	public static String gameSuccessMsg(String msg) {
		return ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + msg;
	}
	
	public static String gameErrorMsg(String msg) {
		return ChatColor.DARK_RED + "" + ChatColor.ITALIC + msg;
	}
	
	public static String importantBroadcast(String msg) {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + msg;
	}
	
	public static String cmdUsageMsg(String msg) {
		return ChatColor.YELLOW + "Usage: " + ChatColor.ITALIC + msg;
	}
	
	public static void sendToLog(String part, String msg) {
		System.out.println("[AG-" + ChatColor.stripColor(part) + "]" +ChatColor.stripColor(msg));		
	}
	
}
